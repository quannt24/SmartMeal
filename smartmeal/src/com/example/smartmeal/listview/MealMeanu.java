package com.example.smartmeal.listview;

import java.util.ArrayList;
import java.util.List;

public class MealMeanu {

	public String				string;
	public final List<String>	children	= new ArrayList<String>();

	public MealMeanu(String string) {
		this.string = string;
	}

}