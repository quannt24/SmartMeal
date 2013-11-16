package com.example.smartmeal.save;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import vn.hust.smie.History;
import vn.hust.smie.User;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

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

	private SharedPreferences	preference;

	private Save() {
		preference = MainActivity.MAINACTIVITY.getPreferences(Context.MODE_PRIVATE);

		// initialize user
		preference.getInt(Save.BIRTH, 0);
		preference.getInt(Save.WEIGHT, 0);
		preference.getInt(Save.HEIGHT, 0);
		preference.getInt(Save.ACTIVITY, 0);
	}

	public static Save getSave() {
		if (save == null) save = new Save();
		return save;
	}

	public User getUser() {
		User user = new User();
		user.setName(getString(Save.NAME));
		user.setSex(getInt(Save.GENDER));
		user.setYearOfBirth(getInt(Save.BIRTH));
		user.setHeight(getInt(Save.HEIGHT));
		user.setWeight(getInt(Save.WEIGHT));
		user.setActivity(getInt(Save.ACTIVITY));

		return user;
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

	public History getHistory() {
		FileInputStream fis;
		History history = null;
		try{
			fis = MainActivity.MAINACTIVITY.openFileInput("history");
			ObjectInputStream is = new ObjectInputStream(fis);
			history = (History) is.readObject();
			is.close();
		}
		catch (FileNotFoundException e){
			Toast.makeText(MainActivity.MAINACTIVITY, "File not found", Toast.LENGTH_SHORT).show();
		}
		catch (IOException e){
			Toast.makeText(MainActivity.MAINACTIVITY, "IOException", Toast.LENGTH_SHORT).show();
		}
		catch (ClassNotFoundException e){
			Toast.makeText(MainActivity.MAINACTIVITY, "Class not found", Toast.LENGTH_SHORT).show();
		}

		return history;
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

	public void save(History history) {
		try{
			FileOutputStream fos = MainActivity.MAINACTIVITY.openFileOutput("history", Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(this);
			os.close();
		}
		catch (FileNotFoundException e){
			Toast.makeText(MainActivity.MAINACTIVITY, "File not found", Toast.LENGTH_SHORT).show();
		}
		catch (IOException e){
			Toast.makeText(MainActivity.MAINACTIVITY, "IO failure", Toast.LENGTH_SHORT).show();
		}
	}
}
