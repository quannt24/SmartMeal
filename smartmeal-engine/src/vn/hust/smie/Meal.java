/**
 * 
 */
package vn.hust.smie;

import java.util.ArrayList;


/**
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class Meal {

    public static final int TYPE_BREAKFAST = 1;
    public static final int TYPE_LUNCH = 2;
    public static final int TYPE_DINNER = 3;
    public static final int TYPE_AUXILIARY = 4;
    
    private DishCollection dc;
    private int type;
    
    // Required (input information) total energy and nutrients
    private double reqEnergy; // In KCal
    private double reqProAmount; // Protein amount in gram
    private double reqLipAmount; // Lipid amount in gram
    private double reqGluAmount; // Glucid amount in gram
    
    private ArrayList<Dish> menu;
    
    /**
     * @param type
     * @param reqEnergy
     * @param reqProAmount
     * @param reqLipAmount
     * @param reqGluAmount
     */
    public Meal(DishCollection dc, int type, double reqEnergy, double reqProAmount, double reqLipAmount,
		double reqGluAmount) {
	this.dc = dc;
	this.type = type;
	this.reqEnergy = reqEnergy;
	this.reqProAmount = reqProAmount;
	this.reqLipAmount = reqLipAmount;
	this.reqGluAmount = reqGluAmount;
	
	menu = new ArrayList<Dish>();
    }
    
    /**
     * @return the type
     */
    public int getType() {
	return type;
    }
    
    public ArrayList<Dish> getMenu() {
	return menu;
    }
    
    public void addDish(int dishId) {
	if (dishId == 0) return; // There is no such dish
	
	Dish dish = dc.getDish(dishId);
	if (dish != null) menu.add(dish);
    }
    
    /**
     * Get currently shorted energy in KCal
     * @return
     */
    public double getShortedEnergy() {
	double tmp = 0;
	for (Dish dish : menu) {
	    tmp += dish.getEnergy();
	}
	return reqEnergy - tmp;
    }
    
    /**
     * Get currently shorted amount of protein in gram
     * @return
     */
    public double getShortedPro() {
	double tmp = 0;
	for (Dish dish : menu) {
	    tmp += dish.getProAmount();
	}
	return reqProAmount - tmp;
    }
    
    /**
     * Get currently shorted amount of lipid in gram
     * @return
     */
    public double getShortedLip() {
	double tmp = 0;
	for (Dish dish : menu) {
	    tmp += dish.getLipAmount();
	}
	return reqLipAmount - tmp;
    }
    
    /**
     * Get currently shorted amount of glucid in gram
     * @return
     */
    public double getShortedGlu() {
	double tmp = 0;
	for (Dish dish : menu) {
	    tmp += dish.getGluAmount();
	}
	return reqGluAmount - tmp;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Meal [type=" + type + ", reqEnergy=" + reqEnergy + ", reqProAmount=" + reqProAmount
		+ ", reqLipAmount=" + reqLipAmount + ", reqGluAmount=" + reqGluAmount + ", Dishes: " + menu.size() + "]";
    }
    
}
