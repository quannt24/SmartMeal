package com.example.smartmeal.fragment;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartmeal.R;
import com.example.smartmeal.save.Save;

public class UserProfile extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.user, container, false);

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int age = year - Save.getSave().getInt(Save.BIRTH);

		((TextView) rootView.findViewById(R.id.textView1)).setText("Xin chào, bạn " + Save.getSave().getString(Save.NAME));
		((TextView) rootView.findViewById(R.id.textView2)).setText("Tuổi bạn là " + age);
		((TextView) rootView.findViewById(R.id.textView4)).setText("Cân Nặng " + Save.getSave().getInt(Save.WEIGHT) + " kg");
		((TextView) rootView.findViewById(R.id.textView3)).setText("Chiều cao " + Save.getSave().getInt(Save.HEIGHT) + " cm");
		((TextView) rootView.findViewById(R.id.textView5)).setText("Mức vận động " + Save.getSave().getInt(Save.ACTIVITY));

		// TODO BMI evaluation
		((TextView) rootView.findViewById(R.id.textView5)).setText("Chưa xếp loại");
		
		return rootView;
	}
}
