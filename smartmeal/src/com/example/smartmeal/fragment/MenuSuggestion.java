package com.example.smartmeal.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.smartmeal.R;
import com.example.smartmeal.listview.Group;
import com.example.smartmeal.listview.MyAdapter;

public class MenuSuggestion extends Fragment {

	// more efficient than HashMap for mapping integers to objects
	SparseArray<Group>	groups	= new SparseArray<Group>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.menu, container, false);

		// set up time
		TextView day = (TextView) rootView.findViewById(R.id.day);
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+07"));
		day.setText("Thực đơn ngày " + (new SimpleDateFormat("dd/MM/yyyy", Locale.US)).format(calendar.getTime()));

		// initialize
		Group breakfast = new Group("Bữa sáng");
		Group noon = new Group("Bữa trưa");
		Group evening = new Group("Bữa tối");

		// TODO append data
		breakfast.children.add("Tạp phí lù");
		breakfast.children.add("Bít tất");

		noon.children.add("Phá lấu");
		noon.children.add("Ốc móng tay xào chua ngọt");
		noon.children.add("Cơm chiên Dương Châu");

		evening.children.add("Bánh bao rán");
		evening.children.add("Sữa ông thọ");

		// append all
		groups.append(0, breakfast);
		groups.append(1, noon);
		groups.append(2, evening);

		// List View
		ExpandableListView listView = (ExpandableListView) rootView.findViewById(R.id.expandableListView1);
		MyAdapter adapter = new MyAdapter(getActivity(), groups);
		listView.setAdapter(adapter);

		return rootView;
	}
}
