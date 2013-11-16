package com.example.smartmeal.fragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

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
import com.example.smartmeal.listview.HistoryAdapter;
import com.example.smartmeal.listview.MealMenu;

public class Submitted extends Fragment {

	public static Submitted	submitted;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// construct view
		View rootView = inflater.inflate(R.layout.list, container, false);

		((TextView) rootView.findViewById(R.id.day)).setText("Các bữa đã chấp nhận");
		
		History history = MainActivity.smie.getHistory();

		int i = 0;
		MealMenu menu;
		SparseArray<MealMenu> groups = new SparseArray<MealMenu>();

		for (Meal m : history.getMealList()){
			String type = "";
			switch(m.getType()){
			case Meal.TYPE_BREAKFAST:
				type = "sáng";
				break;
			case Meal.TYPE_LUNCH:
				type = "trưa";
				break;
			case Meal.TYPE_DINNER:
				type = "tối";
				break;
			}
			menu = new MealMenu("Bữa " + type + " ngày " + (new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(m.getDate().getTime())));
			for (Dish d : m.getMenu())
				menu.add(d);
			groups.append(i++, menu);
		}

		ExpandableListView listView = (ExpandableListView) rootView.findViewById(R.id.expandableListView1);
		HistoryAdapter adapter = new HistoryAdapter(getActivity(), groups);
		listView.setAdapter(adapter);

		submitted = this;
		return rootView;
	}

	public static void reloadHistory() {
		Submitted.submitted.getFragmentManager().beginTransaction()
				.detach(Submitted.submitted)
				.attach(Submitted.submitted)
				.commit();
	}
}
