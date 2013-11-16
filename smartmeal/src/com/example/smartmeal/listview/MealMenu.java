package com.example.smartmeal.listview;

import java.util.ArrayList;
import java.util.List;

import vn.hust.smie.Dish;

public class MealMenu {

	public String				string;
	public boolean isAccepted = false;
	private final List<Dish>	menu	= new ArrayList<Dish>();

	public MealMenu(String string) {
		this.string = string;
	}

	public boolean getAccept(){
		return this.isAccepted;
	}
	
	public void accept(){
		this.isAccepted = true;
	}
	
	public List<Dish> getMenu(){
		return menu;
	}
	
	public void add(Dish dish) {
		menu.add(dish);
	}

	public void remove(Dish dish){
		menu.remove(menu.indexOf((Dish) dish));
	}
	
	public void clear(){
		menu.clear();
	}
}