/**
 * 
 */
package vn.hust.smie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Quan T. Nguyen <br>
 *         Hanoi University of Science and Technology
 */
public class Tester {

    /**
     * @param args
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException {
	// Create user object
	User user = new User();
	user.setName("Tester");
	user.setSex(User.SEX_FEMALE);
	user.setYearOfBirth(1991);
	user.setHeight(1.65);
	user.setWeight(60);
	user.setActivity(User.ACTIVITY_NORMAL);

	// Create ingredient collection from data
	IngredientCollection ic = Parser.parseIngredient(new FileInputStream(new File("res/data/ingredient.csv")));
	
	// Create dish collection from data
	DishCollection dc = Parser.parseDish(ic, new FileInputStream(new File("res/data/dish.csv")));
	
	// Open rule file
	InputStream inStream = null;
	try {
	    inStream = new FileInputStream(new File("res/rule/smartmeal.xml"));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}

	// Setup engine
	Smie smie = new Smie(dc, ic);
	smie.setHistory(null); // History object can be set at any time
	smie.setupSession(inStream);
	
	// Add input here
	smie.inputUser(user); // User information
	// Process user information
	smie.executeRules();
	
	// TODO Setup meal 1
	smie.setupMeal(Meal.TYPE_BREAKFAST);
	smie.executeRules();
	// Get result
	Meal meal1 = smie.getMeal();
	// Print result
	System.out.println("Result meal 1:");
	ArrayList<Dish> menu1 = meal1.getMenu();
	for (Dish d : menu1) {
	    System.out.println(d);
	}
	System.out.println("Shorted E P L G: " + meal1.getShortedEnergy() + " "
		+ meal1.getShortedPro() + " " + meal1.getShortedLip() + " " + meal1.getShortedGlu());

	// TODO Setup meal 2
	smie.setupMeal(Meal.TYPE_LUNCH);
	smie.executeRules();
	Meal meal2 = smie.getMeal();
	System.out.println("Result meal 2:");
	ArrayList<Dish> menu2 = meal2.getMenu();
	for (Dish d : menu2) {
	    System.out.println(d);
	}
	System.out.println("Shorted E P L G: " + meal2.getShortedEnergy() + " "
		+ meal2.getShortedPro() + " " + meal2.getShortedLip() + " " + meal2.getShortedGlu());

	// Finish session
	smie.finishSession();
    }

}
