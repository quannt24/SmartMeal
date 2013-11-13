/**
 * 
 */
package vn.hust.smie;

import java.util.ArrayList;
import java.util.Date;


/**
 * History of taken meals
 * 
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class History {

    private ArrayList<Meal> mealList;
    
    // Shorted nutrients of previous meals
    private double shortedEnergy; // Shorted Energy In KCal
    private double shortedPro; // Shorted Protein amount in gram
    private double shortedLip; // Shorted Lipid amount in gram
    private double shortedGlu; // Shorted Glucid amount in gram
    
    public History() {
	this(new ArrayList<Meal>());
    }
    
    public History(ArrayList<Meal> mealList) {
	this.mealList = mealList;
	
	shortedEnergy = 0; // Shorted Energy In KCal
	shortedPro = 0; // Shorted Protein amount in gram
	shortedLip = 0; // Shorted Lipid amount in gram
	shortedGlu = 0; // Shorted Glucid amount in gram
    }
    
    /**
     * @return meal list
     */
    public ArrayList<Meal> getMealList() {
	return mealList;
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

	mealList.add(meal);
	
	Date now = new Date();
	long dayDiff = (now.getTime() - meal.getDate().getTime()) / 86400000;
	double w = 1 / Math.pow(2, dayDiff); // Weighing parameter for the role of this added meal
	shortedEnergy += meal.getShortedEnergy() * w;
	shortedPro += meal.getShortedPro() * w;
	shortedLip += meal.getShortedLip() * w;
	shortedGlu += meal.getShortedGlu() * w;
    }
    
    public void clear() {
	mealList.clear();
	
	shortedEnergy = 0; // Shorted Energy In KCal
	shortedPro = 0; // Shorted Protein amount in gram
	shortedLip = 0; // Shorted Lipid amount in gram
	shortedGlu = 0; // Shorted Glucid amount in gram
    }
    
}
