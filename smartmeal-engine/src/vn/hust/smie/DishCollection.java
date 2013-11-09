/**
 * 
 */
package vn.hust.smie;

import java.util.ArrayList;


/**
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class DishCollection {

    ArrayList<Dish> dishList;

    /**
     * Constructor
     */
    public DishCollection() {
	this.dishList = new ArrayList<Dish>();
    }
    
    public void addDish(Dish dish) {
	dishList.add(dish);
    }
    
}
