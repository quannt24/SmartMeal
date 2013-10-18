package com.ict.ke.engine.user;

import java.util.Calendar;

public class User {

	/* Private info */
	private String	name;

	private int		year;
	private int		height;
	private int		weight;
	private int		activity;

	/* Index */
	private float	BMI;
	private int		BMIType;

	public User() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getBMIType() {
		return BMIType;
	}

	public void setBMIType(int BMIType) {
		this.BMIType = BMIType;
	}

	public void setBMI(float BMI) {
		this.BMI = BMI;
	}

	public float getBMI() {
		return BMI;
	}
	
	public int getAge() {
		return Calendar.getInstance().get(Calendar.YEAR) - year;
	}
}
