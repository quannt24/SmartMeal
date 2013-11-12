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
    private double reqEnergy; // Energy In KCal
    private double reqProAmount; // Protein amount in gram
    private double reqLipAmount; // Lipid amount in gram
    private double reqGluAmount; // Glucid amount in gram
    
    // Shorted nutrients
    private double shortedEnergy; // Shorted Energy In KCal
    private double shortedPro; // Shorted Protein amount in gram
    private double shortedLip; // Shorted Lipid amount in gram
    private double shortedGlu; // Shorted Glucid amount in gram
    
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
	this.shortedEnergy = this.reqEnergy = reqEnergy;
	this.shortedPro = this.reqProAmount = reqProAmount;
	this.shortedLip = this.reqLipAmount = reqLipAmount;
	this.shortedGlu = this.reqGluAmount = reqGluAmount;
	
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
	if (dish != null) {
	    menu.add(dish);
	    shortedEnergy -= dish.getEnergy();
	    shortedPro -= dish.getProAmount();
	    shortedLip -= dish.getLipAmount();
	    shortedGlu -= dish.getGluAmount();
	}
    }
    
    public void addDish(Dish dish) {
	if (dish != null) {
	    menu.add(dish);
	    shortedEnergy -= dish.getEnergy();
	    shortedPro -= dish.getProAmount();
	    shortedLip -= dish.getLipAmount();
	    shortedGlu -= dish.getGluAmount();
	}
    }
    
    public void removeDish(int dishId) {
	if (dishId == 0) return; // There is no such dish
	
	Dish dish = null;
	for (Dish d : menu) {
	    if (d.getId() == dishId) {
		dish = d;
		break;
	    }
	}
	
	removeDish(dish);
    }
    
    public void removeDish(Dish dish) {
	if (dish != null && menu.contains(dish)) {
	    shortedEnergy += dish.getEnergy();
	    shortedPro += dish.getProAmount();
	    shortedLip += dish.getLipAmount();
	    shortedGlu += dish.getGluAmount();
	    if (shortedEnergy > reqEnergy) shortedEnergy = reqEnergy;
	    if (shortedPro > reqProAmount) shortedPro = reqProAmount;
	    if (shortedLip > reqLipAmount) shortedLip = reqLipAmount;
	    if (shortedGlu > reqGluAmount) shortedGlu = reqGluAmount;
	    menu.remove(dish);
	}
    }
    
    /**
     * Get currently shorted energy in KCal
     * @return
     */
    public double getShortedEnergy() {
	return shortedEnergy;
    }
    
    /**
     * Get currently shorted amount of protein in gram
     * @return
     */
    public double getShortedPro() {
	return shortedPro;
    }
    
    /**
     * Get currently shorted amount of lipid in gram
     * @return
     */
    public double getShortedLip() {
	return shortedLip;
    }
    
    /**
     * Get currently shorted amount of glucid in gram
     * @return
     */
    public double getShortedGlu() {
	return shortedGlu;
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
