/**
 * 
 */
package vn.hust.smie;

import java.util.ArrayList;
import java.util.Random;


/**
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class DishCollection {

    private ArrayList<Dish> dishList;
    private int selected; // ID of most recently selected dish

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
     * 
     * @param type
     */
    public void selectRandom(int type) {
	ArrayList<Dish> tmp = new ArrayList<Dish>();
	
	for (Dish d : dishList) {
	    if (d.getType() == type) {
		tmp.add(d);
	    }
	}
	
	if (tmp.size() <= 0) selected = 0;
	
	Random rand = new Random();
	int i = rand.nextInt(tmp.size());
	
	selected = tmp.get(i).getId();
    }
    
    /**
     * Get most recently selected dish ID
     * 
     * @return Selected dish ID
     */
    public int getSelected() {
	return selected;
    }
    
}
