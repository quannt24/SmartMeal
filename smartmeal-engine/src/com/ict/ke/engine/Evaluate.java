package com.ict.ke.engine;

public class Evaluate {

	public static final int	STARVE			= -2;
	public static final int	UNDERWEIGHT		= -1;
	public static final int	NORMAL			= 0;
	public static final int	OVERWEIGHT		= 1;
	public static final int	OBESE			= 2;

	public static final int	ACTIVITY_FEW	= 0;
	public static final int	ACTIVITY_NORMAL	= 1;
	public static final int	ACTIVITY_MANY	= 2;

	public static int evaluate(float BMI) {
		if (BMI < 15) return STARVE;
		else if (BMI < 18.5) return UNDERWEIGHT;
		else if (BMI < 25) return NORMAL;
		else if (BMI < 30) return OVERWEIGHT;
		else return OBESE;
	}

}
