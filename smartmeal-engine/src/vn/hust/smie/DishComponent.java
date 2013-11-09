/**
 * 
 */
package vn.hust.smie;


/**
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class DishComponent {

    private int ingredientId;
    private double amount; // Ingredient amount in gram
    
    /**
     * @param ingredientId
     * @param amount
     */
    public DishComponent(int ingredientId, double amount) {
	this.ingredientId = ingredientId;
	this.amount = amount;
    }
    
    /**
     * @param ingredientId
     * @param amount
     */
    public DishComponent(String ingredientId, String amount) {
	this.ingredientId = Integer.parseInt(ingredientId);
	this.amount = Double.parseDouble(amount);
    }
    
    /**
     * @return the ingredientId
     */
    public int getIngredientId() {
        return ingredientId;
    }
    
    /**
     * @param ingredientId the ingredientId to set
     */
    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
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
