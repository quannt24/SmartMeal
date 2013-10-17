package com.example.smartmeal.engine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.rules.StatefulRuleSession;

import android.app.Activity;
import android.util.Log;

public class Dummy {

	public static ArrayList<Ingredient>	listIngredients	= new ArrayList<Ingredient>();

	public Dummy() {}

	// public String setupKnowledgeBase(Activity activity) {
	// return "pass";
	// }

	public String setupKnowledgeBase(Activity activity) {
		try{
			// TODO parse ingredient
			InputStream is = activity.getResources().getAssets().open("ingredient.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));

			if (is != null){
				String line;
				int i = 0;
				while((line = reader.readLine()) != null){
					String[] pattern = line.split(",");
					Dummy.listIngredients.add(new Ingredient(i++, pattern[0], pattern[1], pattern[2], pattern[3], pattern[4], pattern[5], pattern[6]));
				}
			}
			is.close();

			for (Ingredient ingredient : Dummy.listIngredients)
				ingredient.print();

			// TODO user index
			User user = new User();
			user.setName("L� Qu�n");
			user.setYear(1991);
			user.setHeight(165);
			user.setWeight(72);
			user.setActivity(Evaluate.ACTIVITY_MANY);

			// TODO calculate index;
			user.setBMI(user.getWeight() * 10000 / user.getHeight() / user.getHeight());
			Log.d("Run", "" + 0);
			
			// get an input stream to a test XML ruleset
			InputStream inStream = activity.getResources().getAssets().open("user.xml");
			
			// set up
			Smie smie = new Smie();
			StatefulRuleSession session = smie.setupSession(inStream);

			// operation
			session.addObject(user);
			session.executeRules();
			
			// release
			smie.finishSession(session);
			
			// TODO test
			Log.d("BMI", user.getBMI() + "/" + user.getBMIType());

			// TODO return
			return "Type: " + user.getBMIType();
		}
		catch (NoClassDefFoundError e){
			if (e.getMessage().indexOf("Exception") != -1){
				Log.d("Error", "The Rule Engine Implementation could not be found.");
			}
			else{
				Log.d("Error", e.getMessage());
			}
			return "2" + e.getMessage();
		}
		catch (Exception e){
			// assertTrue(false);
			e.printStackTrace();
			return "3" + e.getMessage();
		}
	}
}
