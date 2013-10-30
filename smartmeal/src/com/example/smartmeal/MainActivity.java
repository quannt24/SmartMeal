package com.example.smartmeal;

import java.util.Calendar;
import java.util.Locale;

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

import com.example.smartmeal.fragment.AboutUs;
import com.example.smartmeal.fragment.History;
import com.example.smartmeal.fragment.MenuSuggestion;
import com.example.smartmeal.fragment.Setup;
import com.example.smartmeal.fragment.UserProfile;
import com.example.smartmeal.save.Save;

public class MainActivity extends FragmentActivity {

	public static Activity	MAINACTIVITY;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter	mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager				mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// save reference
		MAINACTIVITY = this;

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
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
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
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
					fragment = new History();
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
	 * TODO submit user profile
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
				int birth = Integer.parseInt(birthStr);
				int year = Calendar.getInstance().get(Calendar.YEAR);
				Log.d("Current", "" + year);
				if ((((year - birth) < 10)) || (birth < 1900)) throw new NumberFormatException();

				step = 1;
				int weight = Integer.parseInt(weightStr);
				if (weight <= 0) throw new NumberFormatException();

				step = 2;
				int height = Integer.parseInt(heightStr);
				if (weight <= 0) throw new NumberFormatException();

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
				if (step == 0) error = "Bạn chưa đủ tuổi dùng sản phẩm (10 tuổi), hoặc nhập chưa đúng mẫu";
				else if (step == 1) error = "Khối lượng nhâp chưa đúng mẫu";
				else if (step == 2) error = "Chiều cao nhập chưa đúng mẫu";

				AlertDialog alertDialog = new AlertDialog.Builder(MAINACTIVITY).setMessage(error).setPositiveButton("Sửa lại", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
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
}
