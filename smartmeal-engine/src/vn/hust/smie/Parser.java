package vn.hust.smie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    /**
     * Parse data file and create an IngredientCollection
     * 
     * @param fileName
     * @return
     */
    public static IngredientCollection parseIngredient(String fileName) {
	IngredientCollection ic = new IngredientCollection();
	BufferedReader br = null;
	String line;
	char ch;
	
	try {
	    br = new BufferedReader(new FileReader(fileName));
	    while ((line = br.readLine()) != null) {
		line = line.trim();
		if (line.length() <= 0) continue;
		ch = line.charAt(0);
		if (ch == '\n' || ch == '#') continue;
		
		String[] pattern = line.split(",");
		ic.addIngredient(new Ingredient(pattern[0], pattern[1], pattern[2], pattern[3],
			pattern[4], pattern[5], pattern[6], pattern[7]));
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (br != null) try {
		br.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	
	return ic;
    }
    
    public static DishCollection parseDish(IngredientCollection ic, String fileName) {
	DishCollection dc = new DishCollection();
	BufferedReader br = null;
	String line;
	char ch;
	int i;
	
	try {
	    br = new BufferedReader(new FileReader(fileName));
	    while ((line = br.readLine()) != null) {
		line = line.trim();
		if (line.length() <= 0) continue;
		ch = line.charAt(0);
		if (ch == '\n' || ch == '#') continue;
		
		String[] pattern = line.split(",");
		Dish dish = new Dish(pattern[0], pattern[1], pattern[2], pattern[3], pattern[4]);
		
		// Add components to dish
		i = 5;
		while (true) {
		    try {
			Ingredient ing = ic.getIngredient(Integer.parseInt(pattern[i]));
			double amount = Double.parseDouble(pattern[i+ 1]);
			dish.addComponent(new DishComponent(ing, amount));
			i += 2;
		    } catch (Exception e) {
			break;
		    }
		}
		
		// Add dish to collection
		dc.addDish(dish);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (br != null) try {
		br.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	
	return dc;
    }
    
}
