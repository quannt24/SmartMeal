package com.example.smartmeal.save;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.smartmeal.MainActivity;

public class Save {

	private static Save			save		= null;

	public static final String	NAME		= "name";
	public static final String	GENDER		= "gender";

	public static final String	BIRTH		= "birth";
	public static final String	WEIGHT		= "weight";
	public static final String	HEIGHT		= "height";
	public static final String	ACTIVITY	= "activity";

	public static final String	IS_SETUP	= "setup";

	public static final int		MALE		= 0;
	public static final int		FEMALE		= 1;

	private SharedPreferences	preference;

	private Save(Activity activity) {
		preference = activity.getPreferences(Context.MODE_PRIVATE);

		// initialize user
		preference.getInt(Save.BIRTH, 0);
		preference.getInt(Save.WEIGHT, 0);
		preference.getInt(Save.HEIGHT, 0);
		preference.getInt(Save.ACTIVITY, 0);
	}

	public static Save getSave() {
		if (save == null) save = new Save(MainActivity.MAINACTIVITY);
		return save;
	}

	public boolean isSetup() {
		return save.preference.getBoolean(IS_SETUP, false);
	}

	public int getInt(String type) {
		return save.preference.getInt(type, 0);
	}

	public String getString(String type) {
		return save.preference.getString(type, "");
	}

	public void save(String type, boolean value) {

		if (type.equals(IS_SETUP)){
			Editor editor = preference.edit();
			editor.putBoolean(type, value);
			editor.commit();
		}

	}

	public void save(String type, String value) {

		if (type.equals(NAME)){
			Editor editor = preference.edit();
			editor.putString(type, value);
			editor.commit();
		}

	}

	public void save(String type, int value) {

		if (type.equals(GENDER) || type.equals(BIRTH) || type.equals(WEIGHT) || type.equals(HEIGHT) || type.equals(ACTIVITY)){
			Editor editor = preference.edit();
			editor.putInt(type, value);
			editor.commit();
		}

	}
}
