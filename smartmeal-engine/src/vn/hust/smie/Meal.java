/**
 * 
 */
package vn.hust.smie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/**
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class Meal implements Serializable{
	private static final long	serialVersionUID	= 6905673432531282644L;
	
	public transient static final int TYPE_BREAKFAST = 1;
    public transient static final int TYPE_LUNCH = 2;
    public transient static final int TYPE_DINNER = 3;
    public transient static final int TYPE_AUXILIARY = 4;
    
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
    private Date date;
    
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
	date = new Date();
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
    
    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
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
    
    public void removeDish(int dishId) {
	if (dishId == 0) return; // There is no such dish
	
	Dish dish = null;
	for (Dish d : menu) {
	    if (d.getId() == dishId) {
		dish = d;
		break;
	    }
	}
	
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
    
    /**
     * Get a balance evaluation of a meal. A negative result means the meal has redundant
     * (in overall) nutrition, a positive result means the meal has short nutrition. The balance
     * score ranges from -3 to 3
     * @return
     */
    public int getBalance() {
	double shorted = shortedEnergy + shortedPro + shortedLip + shortedGlu;
	double bound = (reqEnergy + reqProAmount + reqLipAmount + reqGluAmount) / 2;
	int score = (int) Math.round(shorted / bound * 3);
//	if (score < -3) score = -3;
//	if (score > 3) score = 3;
	return score;
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
