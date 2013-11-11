package vn.hust.smie;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Parser {

	/**
	 * Parse data file and create an IngredientCollection
	 * 
	 * @param inputStream
	 * @return
	 */
	public static IngredientCollection parseIngredient(InputStream inputStream) {
		IngredientCollection ic = new IngredientCollection();
		try{
			List<String> lines = IOUtils.readLines(inputStream);
			char ch;
			for (String line : lines){
				line = line.trim();
				if (line.length() <= 0) continue;
				ch = line.charAt(0);
				if (ch == '\n' || ch == '#') continue;

				String[] pattern = line.split(",");
				ic.addIngredient(new Ingredient(pattern[0], pattern[1], pattern[2], pattern[3],
						pattern[4], pattern[5], pattern[6], pattern[7]));
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}

		return ic;
	}

	public static DishCollection parseDish(IngredientCollection ic, InputStream inputStream) {
		DishCollection dc = new DishCollection();
		try{
			List<String> lines = IOUtils.readLines(inputStream);
			char ch;
			int i;
			for (String line : lines){
				line = line.trim();
				if (line.length() <= 0) continue;
				ch = line.charAt(0);
				if (ch == '\n' || ch == '#') continue;

				String[] pattern = line.split(",");
				Dish dish = new Dish(pattern[0], pattern[1], pattern[2], pattern[3], pattern[4]);

				// Add components to dish
				i = 5;
				while(true){
					try{
						Ingredient ing = ic.getIngredient(Integer.parseInt(pattern[i]));
						double amount = Double.parseDouble(pattern[i + 1]);
						dish.addComponent(new DishComponent(ing, amount));
						i += 2;
					}
					catch (Exception e){
						break;
					}
				}

				// Add dish to collection
				dc.addDish(dish);
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}

		return dc;
	}

}
