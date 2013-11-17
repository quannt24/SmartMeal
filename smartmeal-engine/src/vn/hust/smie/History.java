/**
 * 
 */
package vn.hust.smie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/**
 * History of taken meals
 * 
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class History implements Serializable{
	private transient static final long	serialVersionUID	= -2546753231872963195L;

	private ArrayList<Meal> mealList;
    
    // Shorted nutrients of previous meals
    private transient double shortedEnergy; // Shorted Energy In KCal
    private transient double shortedPro; // Shorted Protein amount in gram
    private transient double shortedLip; // Shorted Lipid amount in gram
    private transient double shortedGlu; // Shorted Glucid amount in gram
    
    public History() {
	this(new ArrayList<Meal>());
    }
    
    public History(ArrayList<Meal> mealList) {
	this.mealList = mealList;
	calcShortedNutri();
    }
    
    /**
     * @return meal list
     */
    public ArrayList<Meal> getMealList() {
	return mealList;
    }
    
    /**
     * @param mealList the mealList to set
     */
    public void setMealList(ArrayList<Meal> mealList) {
        this.mealList = mealList;
        calcShortedNutri();
    }

    /**
     * @return the shortedEnergy
     */
    public double getShortedEnergy() {
        return shortedEnergy;
    }

    /**
     * @return the shortedPro
     */
    public double getShortedPro() {
        return shortedPro;
    }

    
    /**
     * @return the shortedLip
     */
    public double getShortedLip() {
        return shortedLip;
    }

    /**
     * @return the shortedGlu
     */
    public double getShortedGlu() {
        return shortedGlu;
    }

    /**
     * Add a meal to history. Note: meal date should be set properly before the meal being added to
     * history.
     * 
     * @param meal
     */
    public void addMeal(Meal meal) {
	if (meal == null) return;

	if (mealList == null) mealList = new ArrayList<Meal>();
	mealList.add(meal);
	calcShortedNutri();
    }
    
    /**
     * Delete history prior to specific date.
     * 
     * @param keepDays Number of recent days that the history will be kept
     */
    public void cleanUp(int keptDays) {
	if (mealList == null) return;

	Date now = new Date();
	long dayDiff = 0;
	for (Meal meal : mealList) {
	    dayDiff = (now.getTime() - meal.getDate().getTime()) / 86400000;
	    if (dayDiff > keptDays) mealList.remove(meal);
	}
	
	calcShortedNutri();
    }
    
    /**
     * Clear all history
     */
    public void clear() {
	if (mealList != null) mealList.clear();
	
	shortedEnergy = 0; // Shorted Energy In KCal
	shortedPro = 0; // Shorted Protein amount in gram
	shortedLip = 0; // Shorted Lipid amount in gram
	shortedGlu = 0; // Shorted Glucid amount in gram
    }
    
    /**
     * Calculate shorted nutrients and energy of previous meals
     */
    private void calcShortedNutri() {
	shortedEnergy = 0; // Shorted Energy In KCal
	shortedPro = 0; // Shorted Protein amount in gram
	shortedLip = 0; // Shorted Lipid amount in gram
	shortedGlu = 0; // Shorted Glucid amount in gram
	if (mealList == null) return;
	
	Date now = new Date();
	long dayDiff = 0;
	double w = 1; // Weighing parameter for the role of this added meal
	
	for (Meal meal : mealList) {
	    dayDiff = (now.getTime() - meal.getDate().getTime()) / 86400000;
	    w = 1 / Math.pow(2, dayDiff);
	    shortedEnergy += meal.getShortedEnergy() * w;
	    shortedPro += meal.getShortedPro() * w;
	    shortedLip += meal.getShortedLip() * w;
	    shortedGlu += meal.getShortedGlu() * w;
	}
    }
    
}
