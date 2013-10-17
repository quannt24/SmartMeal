/**
 * 
 */
package com.example.smartmeal.engine;

import java.io.InputStream;
import java.util.HashMap;

import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.RuleSession;
import javax.rules.StatefulRuleSession;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import android.util.Log;

/**
 * Smart-meal Inference Engine
 * 
 * @author Quan T. Nguyen <br>
 *         Hanoi University of Science and Technology
 */
public class Smie {

	private RuleServiceProvider	serviceProvider;
	private RuleAdministrator	ruleAdministrator;

	public Smie() {
		try{
			Class.forName("org.jruleengine.RuleServiceProviderImpl");

			serviceProvider = RuleServiceProviderManager.getRuleServiceProvider("org.jruleengine");

			ruleAdministrator = serviceProvider.getRuleAdministrator();

			Log.d("Engine", "\nAdministration API\n");
			Log.d("Engine", "Acquired RuleAdministrator: " + ruleAdministrator);
		}
		catch (ClassNotFoundException e){
			Log.d("Engine", "Error: The Rule Engine Implementation could not be found");
		}
		catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Setup a stateful rule session to use with a specific rule set. The session must be
	 * explicitly released after being used, this can be done by calling finishSession().
	 * 
	 * @param rulesetFile
	 *            File name of XML rule set
	 * @return statefulRuleSession
	 */
	@SuppressWarnings("rawtypes")
	public StatefulRuleSession setupSession(InputStream inStream) {
		RuleExecutionSet res;
		String uri;
		StatefulRuleSession statefulRuleSession;

		// Rarse the ruleset from the XML document
		try{
			res = ruleAdministrator.getLocalRuleExecutionSetProvider(null)
					.createRuleExecutionSet(inStream, null);
			inStream.close();
			System.out.println("Loaded RuleExecutionSet: " + res);
		}
		catch (Exception e){
			System.err.println("Error: Cannot parse ruleset XML file");
			return null;
		}

		// Register the RuleExecutionSet
		try{
			uri = res.getName();
			ruleAdministrator.registerRuleExecutionSet(uri, res, null);
			System.out.println("Bound RuleExecutionSet to URI: " + uri);
		}
		catch (Exception e){
			System.err.println("Error: Cannot register RuleExecutionSet");
			return null;
		}

		// Get a RuleRuntime and create session
		try{
			RuleRuntime ruleRuntime = serviceProvider.getRuleRuntime();
			System.out.println("Acquired RuleRuntime: " + ruleRuntime);

			// create a StatefulRuleSession
			statefulRuleSession = (StatefulRuleSession) ruleRuntime
					.createRuleSession(uri, new HashMap(), RuleRuntime.STATEFUL_SESSION_TYPE);
		}
		catch (Exception e){
			System.err.println("Error: Cannot create session");
			return null;
		}

		return statefulRuleSession;
	}

	/**
	 * Release a rule session after finish with it.
	 * 
	 * @param session
	 */
	public void finishSession(RuleSession session) {
		try{
			session.release();
		}
		catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
