package com.example.smartmeal.fragment;

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
import com.example.smartmeal.listview.MealMenu;
import com.example.smartmeal.listview.MenuAdapter;
import com.example.smartmeal.save.Save;

public class MenuSuggestion extends Fragment {

	// more efficient than HashMap for mapping integers to objects
	static SparseArray<MealMenu>	groups		= new SparseArray<MealMenu>();

	// initialize
	static MealMenu					menu[]		= new MealMenu[] { new MealMenu("Bữa sáng"), new MealMenu("Bữa trưa"), new MealMenu("Bữa tối") };
	static Meal						menuBreakfast, menuLunch, menuDinner;

	// is generated today
	static boolean					detected	= false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// construct view
		View rootView = inflater.inflate(R.layout.list, container, false);

		// set up time
		TextView day = (TextView) rootView.findViewById(R.id.day);
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+07"));
		day.setText("Thực đơn ngày " + (new SimpleDateFormat("dd/MM/yyyy", Locale.US)).format(calendar.getTime()));

		// TODO look up history
		if (detected){
			// menuBreakfast = new ArrayList<Dish>();
			// menuLunch = new ArrayList<Dish>();
			// menuDinner = new ArrayList<Dish>();
		}else{
			MenuSuggestion.detected = true;

			// clear
			menu[0].clear();
			menu[1].clear();
			menu[2].clear();

			// generate
			menuBreakfast = MenuSuggestion.getMeal(Meal.TYPE_BREAKFAST);
			menuLunch = MenuSuggestion.getMeal(Meal.TYPE_LUNCH);
			menuDinner = MenuSuggestion.getMeal(Meal.TYPE_DINNER);

			// append data
			for (Dish d : menuBreakfast.getMenu())
				menu[0].add(d);

			for (Dish d : menuLunch.getMenu())
				menu[1].add(d);

			for (Dish d : menuDinner.getMenu())
				menu[2].add(d);
		}

		// append all
		groups.append(0, menu[0]);
		groups.append(1, menu[1]);
		groups.append(2, menu[2]);

		ExpandableListView listView = (ExpandableListView) rootView.findViewById(R.id.expandableListView1);
		MenuAdapter adapter = new MenuAdapter(getActivity(), this, groups);
		listView.setAdapter(adapter);

		return rootView;
	}

	public MealMenu getMenu(int type) {
		return menu[type];
	}

	public static void loadMealMenu(MenuSuggestion menuSuggestion) {
		menuSuggestion.getFragmentManager().beginTransaction()
				.detach(menuSuggestion)
				.attach(menuSuggestion)
				.commit();
	}

	public static Meal getMeal(int type) {
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

	public static void submitMeal(int type) {
		History history = MainActivity.smie.getHistory();
		switch(type){
		case Meal.TYPE_BREAKFAST:
			history.addMeal(menuBreakfast);
			break;
		case Meal.TYPE_LUNCH:
			history.addMeal(menuLunch);
			break;
		case Meal.TYPE_DINNER:
			history.addMeal(menuDinner);
			break;
		}
		MainActivity.smie.setHistory(history);
		Save.getSave().save(history);
		Submitted.reloadHistory();
	}
}
