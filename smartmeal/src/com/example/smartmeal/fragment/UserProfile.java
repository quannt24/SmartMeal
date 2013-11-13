package com.example.smartmeal.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import vn.hust.smie.DishCollection;
import vn.hust.smie.IngredientCollection;
import vn.hust.smie.Parser;
import vn.hust.smie.Smie;
import vn.hust.smie.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartmeal.MainActivity;
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
		UserProfile.getBMIeval();
		((TextView) rootView.findViewById(R.id.textView5)).setText(getBMIeval());

		return rootView;
	}

	public static String getBMIeval() {
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
		User user = Save.getSave().getUser();
		smie.inputUser(user);
		// Process user information
		smie.executeRules();

		switch(user.getBmiEval()){
		case User.BMI_UNDERWEIGHT:
			return "";
		case User.BMI_SEVERELY_UNDERWEIGHT:
			return "";
		case User.BMI_NORMAL:
			return "thân hình hoàn toàn bình thường, đề nghị ăn uống giữ sức khỏe, cảm ơn...";
		case User.BMI_OVERWEIGHT:
			return "";
		case User.BMI_OBESE:
			return "";
		}

		return "Chưa xếp loại";
	}
}
