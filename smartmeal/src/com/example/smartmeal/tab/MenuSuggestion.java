package com.example.smartmeal.tab;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import vn.hust.smie.Dish;
import vn.hust.smie.History;
import vn.hust.smie.Meal;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.smartmeal.MainActivity;
import com.example.smartmeal.R;
import com.example.smartmeal.adapter.MenuAdapter;
import com.example.smartmeal.menu.MealMenu;
import com.example.smartmeal.save.Save;

public class MenuSuggestion extends Fragment {

	// more efficient than HashMap for mapping integers to objects
	static SparseArray<MealMenu>	groups		= new SparseArray<MealMenu>();

	// initialize
	static MealMenu					menu[]		= new MealMenu[] { new MealMenu("Bữa sáng"), new MealMenu("Bữa trưa"), new MealMenu("Bữa tối") };
	static Meal						meal[]		= new Meal[3];
	static boolean					detected[]	= new boolean[] { false, false, false };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// construct view
		View rootView = inflater.inflate(R.layout.list, container, false);

		// set up time
		TextView day = (TextView) rootView.findViewById(R.id.day);
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+07"));
		day.setText("Thực đơn ngày " + (new SimpleDateFormat("dd/MM/yyyy", Locale.US)).format(calendar.getTime()));

		// look up history
		for (int i = 0; i < 3; i++){
			if (detected[i]) continue;

			for (Meal m : MainActivity.smie.getHistory().getMealList()){
				if (m.getDate().equals(calendar.getTime()) && (m.getType() == i + 1)){
					meal[i] = m;
					menu[i].accept();
					break;
				}
			}

			// load new meal
			if (!detected[i]) meal[i] = MenuSuggestion.generateOneMeal(i + 1);

			// refresh menu
			menu[i].clear();

			// append data
			for (Dish d : meal[i].getMenu())
				menu[i].add(d);

			detected[i] = true;
		}

		// append list
		groups.append(0, menu[0]);
		groups.append(1, menu[1]);
		groups.append(2, menu[2]);

		// create list view
		ExpandableListView listView = (ExpandableListView) rootView.findViewById(R.id.expandableListView1);
		MenuAdapter adapter = new MenuAdapter(getActivity(), this, groups);
		listView.setAdapter(adapter);

		return rootView;
	}

	public MealMenu getMenu(int type) {
		return menu[type];
	}

	public Meal getMeal(int type) {
		return meal[type];
	}

	public void loadMealMenu() {
		this.getFragmentManager().beginTransaction()
				.detach(this)
				.attach(this)
				.commit();
	}

	public static Meal generateOneMeal(int type) {
		InputStream inStream = null;
		try{
			// Open rule file
			inStream = MainActivity.MAINACTIVITY.getResources().getAssets().open("rule/smartmeal.xml");
		}
		catch (IOException e1){
			e1.printStackTrace();
		}
		MainActivity.smie.setupSession(inStream);

		// Add input here
		MainActivity.smie.inputUser(Save.getSave().getUser());
		// Process user information
		MainActivity.smie.executeRules();

		// Setup a meal
		MainActivity.smie.setupMeal(type);
		MainActivity.smie.executeRules();
		MainActivity.smie.printWorkingMemory();

		// Get result
		Meal meal = MainActivity.smie.getMeal();

		// Finish session
		MainActivity.smie.finishSession();

		return meal;
	}

	public void submitMeal(int type) {
		History history = MainActivity.smie.getHistory();
		history.addMeal(meal[type]);
		MainActivity.smie.setHistory(history);
		Save.getSave().save(history);
		Submitted.reloadHistory();
	}
}
