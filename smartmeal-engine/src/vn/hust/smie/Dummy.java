package vn.hust.smie;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatelessRuleSession;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import org.junit.Test;

import vn.hust.smie.engine.Evaluate;
import vn.hust.smie.engine.Ingredient;
import vn.hust.smie.engine.Parser;
import vn.hust.smie.engine.user.User;


public class Dummy {

	@Test
	public static void setupKnowledgeBase(User user) {
		try{
			// Load the rule service provider of the reference implementation.
			// Loading this class will automatically register this provider with the provider manager.
			Class.forName("org.jruleengine.RuleServiceProviderImpl");

			// Get the rule service provider from the provider manager.
			RuleServiceProvider serviceProvider = RuleServiceProviderManager.getRuleServiceProvider("org.jruleengine");

			// get the RuleAdministrator.
			RuleAdministrator ruleAdministrator = serviceProvider.getRuleAdministrator();

			// get an input stream to a test XML ruleset
			// This rule execution set is part of the TCK.
			InputStream inStream = new FileInputStream("res/rule/user.xml");

			// parse the ruleset from the XML document
			RuleExecutionSet res1 = ruleAdministrator.getLocalRuleExecutionSetProvider(null).createRuleExecutionSet(inStream, null);
			inStream.close();

			// register the RuleExecutionSet
			String uri = res1.getName();
			ruleAdministrator.registerRuleExecutionSet(uri, res1, null);

			// Get a RuleRuntime and invoke the rule engine.
			RuleRuntime ruleRuntime = serviceProvider.getRuleRuntime();

			// execute
			@SuppressWarnings("rawtypes")
			StatelessRuleSession statelessRuleSession = (StatelessRuleSession) ruleRuntime.createRuleSession(uri, new HashMap(),
					RuleRuntime.STATELESS_SESSION_TYPE);

			// Execute the rules without a filter.
			List<User> users = new ArrayList<User>();
			users.add(user);
			statelessRuleSession.executeRules(users);
			// TODO test
			System.out.println(users.get(0).getBMI() + "/" + users.get(0).getBMIType());

			// release session
			statelessRuleSession.release();
		}
		catch (NoClassDefFoundError e){
			if (e.getMessage().indexOf("Exception") != -1){
				System.err.println("Error: The Rule Engine Implementation could not be found.");
			}
			else{
				System.err.println("Error: " + e.getMessage());
			}
		}
		catch (Exception e){
			// assertTrue(false);
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			// TODO parse ingredient
			Parser.parseIngredient("res/data/ingredient.csv");
			for (Ingredient ingredient : Parser.listIngredients)
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

			// TODO setup knowledge-base
			Dummy.setupKnowledgeBase(user);

			// TODO BMI evaluate

			System.out.println("BMI = " + user.getBMI() + "/" + user.getBMIType());
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally{
			System.out.println("Done");
		}
	}

}
