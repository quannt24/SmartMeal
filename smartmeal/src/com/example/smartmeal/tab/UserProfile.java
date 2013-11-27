package com.example.smartmeal.tab;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import vn.hust.smie.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartmeal.MainActivity;
import com.example.smartmeal.R;
import com.example.smartmeal.save.Save;

public class UserProfile extends Fragment {

	public static float	Calor;
	public static float	Pro;
	public static float	Lip;
	public static float	Glu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.user, container, false);

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int age = year - Save.getSave().getInt(Save.BIRTH);

		((TextView) rootView.findViewById(R.id.textView1)).setText("Xin chào, bạn " + Save.getSave().getString(Save.NAME));
		((TextView) rootView.findViewById(R.id.textView2)).setText("Tuổi bạn là " + age);
		((TextView) rootView.findViewById(R.id.textView4)).setText("Cân Nặng " + Save.getSave().getInt(Save.WEIGHT) + " kg");
		((TextView) rootView.findViewById(R.id.textView3)).setText("Chiều cao " + (int) (Save.getSave().getFloat(Save.HEIGHT) * 100) + " cm");
		((TextView) rootView.findViewById(R.id.textView5)).setText("Mức vận động: cấp " + (Save.getSave().getInt(Save.ACTIVITY) + 1));

		// BMI evaluation
		((TextView) rootView.findViewById(R.id.textView7)).setText(getBMIevalAndStandard());

		// standard
		((TextView) rootView.findViewById(R.id.standardCalor)).setText("Calor (Kcal): " + UserProfile.Calor);
		((TextView) rootView.findViewById(R.id.standardPro)).setText("Protein (gram): " + UserProfile.Pro);
		((TextView) rootView.findViewById(R.id.standardLipid)).setText("Lipid (gram): " + UserProfile.Lip);
		((TextView) rootView.findViewById(R.id.standardGlucid)).setText("Glucid (gram): " + UserProfile.Glu);

		return rootView;
	}

	public static String getBMIevalAndStandard() {
		InputStream inStream = null;

		try{
			inStream = MainActivity.MAINACTIVITY.getResources().getAssets().open("rule/smartmeal.xml");
		}
		catch (IOException e1){
			e1.printStackTrace();
		}

		MainActivity.smie.setupSession(inStream);

		// Add input here
		User user = Save.getSave().getUser();
		MainActivity.smie.inputUser(user);
		// Process user information
		MainActivity.smie.executeRules();

		// save standard
		UserProfile.Calor = ((int) MainActivity.smie.getEnergy() * 100) / 100.00f;
		UserProfile.Pro = ((int) MainActivity.smie.getPro() * 100) / 100.00f;
		UserProfile.Lip = ((int) MainActivity.smie.getLip() * 100) / 100.00f;
		UserProfile.Glu = ((int) MainActivity.smie.getGlu() * 100) / 100.00f;

		MainActivity.smie.finishSession();

		Log.d("BMI", "" + user.getWeight() + "/" + user.getHeight() + user.getBmiEval());
		switch(user.getBmiEval()){
		case User.BMI_SEVERELY_UNDERWEIGHT:
			return "thân hình da bọc xương";
		case User.BMI_UNDERWEIGHT:
			return "thân hình hơi gầy một tí";
		case User.BMI_NORMAL:
			return "thân hình hoàn toàn bình thường";
		case User.BMI_OVERWEIGHT:
			return "thân hình hơi béo một tí";
		case User.BMI_OBESE:
			return "thân hình đồ sộ";
		}

		return "Chưa xếp loại";
	}
}
