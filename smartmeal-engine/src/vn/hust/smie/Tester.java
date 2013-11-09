/**
 * 
 */
package vn.hust.smie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Quan T. Nguyen <br>
 *         Hanoi University of Science and Technology
 */
public class Tester {

    /**
     * @param args
     */
    public static void main(String[] args) {
	// Create user object
	User user = new User();
	user.setName("Le Quan");
	user.setSex(User.SEX_MALE);
	user.setYearOfBirth(1991);
	user.setHeight(1.65);
	user.setWeight(72);
	user.setActivity(User.ACTIVITY_MANY);

	// Create ingredient collection from data
	IngredientCollection ic = Parser.parseIngredient("res/data/ingredient.csv");
	
	// Create dish collection from data
	DishCollection dc = Parser.parseDish("res/data/dish.csv");
	
	// Open rule file
	InputStream inStream = null;
	try {
	    inStream = new FileInputStream(new File("res/rule/smartmeal.xml"));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}

	// Setup engine
	Smie smie = new Smie();
	smie.setupSession(inStream);
	
	// Add input here
	smie.inputUser(user);
	
	// Execute rules
	smie.executeRules();
	smie.finishSession();
    }

}
