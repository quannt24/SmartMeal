/**
 * 
 */
package vn.hust.smie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class DishCollection implements Serializable{
	private static final long	serialVersionUID	= 5803768276010584455L;
	
	private ArrayList<Dish> dishList;
    private transient int selected; // ID of most recently selected dish

    /**
     * Constructor
     */
    public DishCollection() {
	this.dishList = new ArrayList<Dish>();
	selected = 0;
    }
    
    public void addDish(Dish dish) {
	dishList.add(dish);
    }
    
    public Dish getDish(int id) {
	for (Dish dish : dishList) {
	    if (dish.getId() == id) return dish;
	}
	return null;
    }
    
    /**
     * Select a dish of a specific type randomly
     * @param type
     */
    public void selectRandom(int type) {
	ArrayList<Dish> tmp = new ArrayList<Dish>();
	
	for (Dish d : dishList) {
	    if (d.getType() == type) {
		tmp.add(d);
	    }
	}
	
	if (tmp.size() <= 0) {
	    selected = 0;
	    return;
	}
	
	Random rand = new Random();
	int i = rand.nextInt(tmp.size());
	
	selected = tmp.get(i).getId();
    }
    
    /**
     * Select a dish randomly which belongs to one of inputed types
     * @param type1
     * @param type2
     */
    public void selectRandom(int type1, int type2) {
	ArrayList<Dish> tmp = new ArrayList<Dish>();

	for (Dish d : dishList) {
	    if (d.getType() == type1 || d.getType() == type2) {
		tmp.add(d);
	    }
	}

	if (tmp.size() <= 0) {
	    selected = 0;
	    return;
	}

	Random rand = new Random();
	int i = rand.nextInt(tmp.size());

	selected = tmp.get(i).getId();
    }

    /**
     * Select a dish which is best match with nutrition indices
     * 
     * @param type
     * @param energy
     * @param pro
     * @param lip
     * @param glu
     */
    public void selectBestMatch(int type, double energy, double pro, double lip, double glu) {
	ArrayList<Dish> tmp = new ArrayList<Dish>();

	for (Dish d : dishList) {
	    if (d.getType() == type) {
		tmp.add(d);
	    }
	}
	
	if (tmp.size() <= 0) selected = 0;
	
	int best = 0; // ID of best match dish
	double minDiff = Double.MAX_VALUE;
	double diff;
	for (Dish d : tmp) {
	    diff = Math.abs(d.getEnergy() - energy + d.getProAmount() - pro + d.getLipAmount() - lip + d.getGluAmount() - glu);
	    if (diff < minDiff) {
		minDiff = diff;
		best = d.getId();
	    }
	}
	
	selected = best;
    }
    
    public void selectBestMatch(int type1, int type2, double energy, double pro, double lip, double glu) {
	ArrayList<Dish> tmp = new ArrayList<Dish>();

	for (Dish d : dishList) {
	    if (d.getType() == type1 || d.getType() == type2) {
		tmp.add(d);
	    }
	}
	
	if (tmp.size() <= 0) selected = 0;
	
	int best = 0; // ID of best match dish
	double minDiff = Double.MAX_VALUE;
	double diff;
	for (Dish d : tmp) {
	    diff = Math.abs(d.getEnergy() - energy + d.getProAmount() - pro + d.getLipAmount() - lip + d.getGluAmount() - glu);
	    if (diff < minDiff) {
		minDiff = diff;
		best = d.getId();
	    }
	}
	
	selected = best;
    }
    
    /**
     * Get most recently selected dish ID
     * @return Selected dish ID
     */
    public int getSelected() {
	return selected;
    }
    
}
