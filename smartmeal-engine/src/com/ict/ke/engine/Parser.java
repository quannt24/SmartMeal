package com.ict.ke.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class Parser {
	public static ArrayList<Ingredient> listIngredients = new ArrayList<>();
	
	public static void parseIngredient(String filename) throws IOException {
		int i = 0;
		for (String line : FileUtils.readFileToString(new File(filename)).split("\n")){
			String[] pattern = line.split(",");
			listIngredients.add(new Ingredient(i++, pattern[0], pattern[1], pattern[2], pattern[3], pattern[4], pattern[5], pattern[6]));
		}
	}
}
