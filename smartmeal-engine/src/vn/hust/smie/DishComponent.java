/**
 * 
 */
package vn.hust.smie;


/**
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class DishComponent {

    private Ingredient ingredient;
    private double amount; // Ingredient amount in gram
    
    /**
     * @param ingredient
     * @param amount
     */
    public DishComponent(Ingredient ingredient, double amount) {
	this.ingredient = ingredient;
	this.amount = amount;
    }
    
    /**
     * @return the ingredient
     */
    public Ingredient getIngredient() {
        return ingredient;
    }
    
    /**
     * @param ingredient the ingredient to set
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
}
