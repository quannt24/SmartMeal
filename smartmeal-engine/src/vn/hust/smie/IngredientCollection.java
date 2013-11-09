/**
 * 
 */
package vn.hust.smie;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class IngredientCollection {

    ArrayList<Ingredient> ingList;
    
    /**
     * Constructor
     */
    public IngredientCollection() {
	ingList = new ArrayList<Ingredient>();
    }
    
    /**
     * Add ingredient to collection. If ingredient's ID exist in collection, it will not be added.
     * @param ing
     */
    public void addIngredient(Ingredient ing) {
	if (binSearch(ing.getId(), ingList) != null) return;
	
	ingList.add(ing);
	sortIngList();
    }
    
    /**
     * Get ingredient by index
     * @param id
     * @return
     */
    public Ingredient getIngredient(int id) {
	return binSearch(id, ingList);
    }
    
    /**
     * Search ingredient by ID
     * @param id
     * @return
     */
    private Ingredient binSearch(int id, List<Ingredient> list) {
	if (list == null || list.size() <= 0) return null;
	
	int pivot = list.size() / 2;
	Ingredient obj = list.get(pivot);
	int from, to;
	
	if (obj.getId() == id) return obj;
	
	if (obj.getId() > id) {
	    from = 0;
	    to = pivot;
	} else {
	    from = pivot + 1;
	    to = list.size();
	}
	
	if (from < 0 || to > list.size() || from > to) return null;
	
	return binSearch(id, list.subList(from, to));
    }
    
    /**
     * Sort ingredient list by insertion sort
     */
    private void sortIngList() {
	int i, j;
	Ingredient key;
	for (i = 1; i < ingList.size(); i++) {
	    key = ingList.get(i);
	    for (j = i - 1; j >= 0 && ingList.get(j).getId() > key.getId(); j--) {
		ingList.set(j + 1, ingList.get(j));
	    }
	    ingList.set(j + 1, key);
	}
    }
    
}
