package com.example.smartmeal;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import vn.hust.smie.DishCollection;
import vn.hust.smie.History;
import vn.hust.smie.IngredientCollection;
import vn.hust.smie.Parser;
import vn.hust.smie.Smie;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.smartmeal.save.Save;
import com.example.smartmeal.tab.AboutUs;
import com.example.smartmeal.tab.MenuSuggestion;
import com.example.smartmeal.tab.Setup;
import com.example.smartmeal.tab.Submitted;
import com.example.smartmeal.tab.UserProfile;

public class MainActivity extends FragmentActivity {

	public static Activity	MAINACTIVITY;
	public static Smie		smie;
	SectionsPagerAdapter	mSectionsPagerAdapter;
	ViewPager				mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// save reference
		MAINACTIVITY = this;

		// set up engine
		IngredientCollection ic = null;
		DishCollection dc = null;

		try{
			// Create ingredient collection from data
			ic = Parser.parseIngredient(getResources().getAssets().open("data/ingredient.csv"));
			// Create dish collection from data
			dc = Parser.parseDish(ic, getResources().getAssets().open("data/dish.csv"));
			// Setup engine
			MainActivity.smie = new Smie(dc, ic);
			MainActivity.smie.setHistory(Save.getSave().getHistory());
		}
		catch (IOException e1){
			e1.printStackTrace();
		}

		setContentView(R.layout.activity_main);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;

			if (Save.getSave().isSetup()){
				switch(position){
				case 0:
					fragment = new UserProfile();
					break;
				case 1:
					fragment = new MenuSuggestion();
					break;
				case 2:
					fragment = new Submitted();
					break;
				case 3:
					fragment = new AboutUs();
					break;
				}
			}else{
				fragment = new Setup();
			}

			return fragment;
		}

		@Override
		public int getCount() {
			if (Save.getSave().isSetup()) return 4;
			else return 1;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch(position){
			case 0:
				if (Save.getSave().isSetup()) return getString(R.string.title_section1).toUpperCase(l);
				else return getString(R.string.title_section0).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * submit user profile
	 */
	public void onSetup(View v) {
		Log.d("Submit", "OK");

		if (v.getId() == R.id.submit){

			// collect info
			String name = ((EditText) MAINACTIVITY.findViewById(R.id.editText1)).getText().toString();
			RadioGroup radios = (RadioGroup) MAINACTIVITY.findViewById(R.id.radioGroup2);
			int genderInt = radios.indexOfChild(MAINACTIVITY.findViewById(radios.getCheckedRadioButtonId()));
			String birthStr = ((EditText) MAINACTIVITY.findViewById(R.id.editText4)).getText().toString();
			String weightStr = ((EditText) MAINACTIVITY.findViewById(R.id.editText2)).getText().toString();
			String heightStr = ((EditText) MAINACTIVITY.findViewById(R.id.editText3)).getText().toString();
			radios = (RadioGroup) MAINACTIVITY.findViewById(R.id.radioGroup1);
			int act = radios.indexOfChild(MAINACTIVITY.findViewById(radios.getCheckedRadioButtonId()));

			Log.d("name", name);
			Log.d("gender", "" + genderInt);
			Log.d("birth", birthStr);
			Log.d("weight", weightStr);
			Log.d("height", heightStr);
			Log.d("act", "" + act);

			int step = 0;
			try{
				// validate info
				step = 0;
				if (name.trim().equals("")) throw new NumberFormatException();

				step = 1;
				int birth = Integer.parseInt(birthStr);
				int year = Calendar.getInstance().get(Calendar.YEAR);
				Log.d("Current", "" + year);
				if ((((year - birth) < 10)) || (birth < 1900)) throw new NumberFormatException();

				step = 2;
				int weight = Integer.parseInt(weightStr);
				if (weight <= 0) throw new NumberFormatException();

				step = 3;
				float height = Integer.parseInt(heightStr) / 100.0f;
				if (height <= 0) throw new NumberFormatException();

				// save info
				Save.getSave().save(Save.NAME, name);
				Save.getSave().save(Save.GENDER, genderInt);
				Save.getSave().save(Save.BIRTH, birth);
				Save.getSave().save(Save.WEIGHT, weight);
				Save.getSave().save(Save.HEIGHT, height);
				Save.getSave().save(Save.ACTIVITY, act);
			}
			catch (NumberFormatException e){
				e.printStackTrace();

				String error = "";
				if (step == 0) error = "Bạn chưa nhập tên";
				else if (step == 1) error = "Bạn chưa đủ tuổi dùng sản phẩm (10 tuổi), hoặc nhập chưa đúng mẫu";
				else if (step == 2) error = "Khối lượng nhâp chưa đúng mẫu";
				else if (step == 3) error = "Chiều cao nhập chưa đúng mẫu";

				AlertDialog alertDialog = new AlertDialog.Builder(MAINACTIVITY).setMessage(error).setPositiveButton("Sửa lại", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
						.create();
				alertDialog.show();

				return;
			}
		}

		// save setup
		Save.getSave().save(Save.IS_SETUP, !Save.getSave().isSetup());

		// start new activity
		Intent i = new Intent(this, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		MainActivity.this.finish();
	}

	public void clearHistory(View v) {
		AlertDialog alertDialog = new AlertDialog.Builder(this)
				.setTitle("Xóa lịch sử các bữa ăn")
				.setMessage("Bạn chuẩn bị xóa toàn bộ lịch sử các bữa ăn")
				.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						History history = new History();
						Save.getSave().save(history);
						MainActivity.smie.setHistory(history);
						MenuSuggestion.detected = new boolean[] { false, false, false };
						Submitted.reloadHistory();
						MenuSuggestion.reloadMenu();
						dialog.dismiss();
					}
				})
				.setNegativeButton("Không", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create();
		alertDialog.show();
	}
}
