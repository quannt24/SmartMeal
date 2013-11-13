package com.example.smartmeal.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import vn.hust.smie.Dish;
import vn.hust.smie.DishCollection;
import vn.hust.smie.IngredientCollection;
import vn.hust.smie.Meal;
import vn.hust.smie.Parser;
import vn.hust.smie.Smie;
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
import com.example.smartmeal.listview.Group;
import com.example.smartmeal.listview.MyAdapter;
import com.example.smartmeal.save.Save;

public class MenuSuggestion extends Fragment {

	// more efficient than HashMap for mapping integers to objects
	SparseArray<Group>	groups	= new SparseArray<Group>();
	ArrayList<Dish>		menuBreakfast, menuLunch, menuDinner;
	Meal				buffer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.menu, container, false);

		// set up time
		TextView day = (TextView) rootView.findViewById(R.id.day);
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+07"));
		day.setText("Thực đơn ngày " + (new SimpleDateFormat("dd/MM/yyyy", Locale.US)).format(calendar.getTime()));

		// TODO look up history
		boolean detected = false;
		if (detected){
			menuBreakfast = new ArrayList<Dish>();
			menuLunch = new ArrayList<Dish>();
			menuDinner = new ArrayList<Dish>();
		}else{
			buffer = MenuSuggestion.getMeal(Meal.TYPE_BREAKFAST);
			menuBreakfast = buffer.getMenu();

			buffer = MenuSuggestion.getMeal(Meal.TYPE_LUNCH);
			menuLunch = buffer.getMenu();

			buffer = MenuSuggestion.getMeal(Meal.TYPE_DINNER);
			menuDinner = buffer.getMenu();
		}

		// initialize
		Group breakfast = new Group("Bữa sáng");
		Group lunch = new Group("Bữa trưa");
		Group dinner = new Group("Bữa tối");

		// append data
		for (Dish d : menuBreakfast)
			breakfast.children.add(d.getName());

		for (Dish d : menuLunch)
			lunch.children.add(d.getName());

		for (Dish d : menuDinner)
			dinner.children.add(d.getName());

		// append all
		groups.append(0, breakfast);
		groups.append(1, lunch);
		groups.append(2, dinner);

		// List View
		ExpandableListView listView = (ExpandableListView) rootView.findViewById(R.id.expandableListView1);
		MyAdapter adapter = new MyAdapter(getActivity(), groups);
		listView.setAdapter(adapter);

		return rootView;
	}

	public static Meal getMeal(int type) {
		IngredientCollection ic = null;
		DishCollection dc = null;
		InputStream inStream = null;

		try{
			// Create ingredient collection from data
			ic = Parser.parseIngredient(MainActivity.MAINACTIVITY.getResources().getAssets().open("data/ingredient.csv"));
			// Create dish collection from data
			dc = Parser.parseDish(ic, MainActivity.MAINACTIVITY.getResources().getAssets().open("data/dish.csv"));
			// Open rule file
			inStream = MainActivity.MAINACTIVITY.getResources().getAssets().open("rule/smartmeal.xml");

		}
		catch (IOException e1){
			e1.printStackTrace();
		}

		// Setup engine
		Smie smie = new Smie(dc, ic);

		smie.setupSession(inStream);

		// Add input here
		smie.inputUser(Save.getSave().getUser());
		// Process user information
		smie.executeRules();

		// Setup a meal
		smie.setupMeal(type);
		smie.executeRules();
		smie.printWorkingMemory();

		// Get result
		Meal meal = smie.getMeal();

		// Finish session
		smie.finishSession();

		return meal;
	}
}
