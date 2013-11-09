/**
 * 
 */
package vn.hust.smie;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.rules.InvalidRuleSessionException;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatefulRuleSession;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import org.jruleengine.StatefulRuleSessionImpl;

/**
 * Smart-meal Inference Engine
 * 
 * @author Quan T. Nguyen <br>
 *         Hanoi University of Science and Technology
 */
public class Smie {

    private RuleServiceProvider serviceProvider;
    private RuleAdministrator ruleAdministrator;
    private StatefulRuleSession session;

    private Calculator calc;
    private Energy ener;
    private MealDist mealDist;
    private MajorNutriDist mnd;
    private EnergyValue enerVal;
    
    public Smie() {
	try {
	    Class.forName("org.jruleengine.RuleServiceProviderImpl");

	    serviceProvider = RuleServiceProviderManager.getRuleServiceProvider("org.jruleengine");

	    ruleAdministrator = serviceProvider.getRuleAdministrator();

	    System.out.println("Engine: " + "Administration API\n");
	    System.out.println("Engine: " + "Acquired RuleAdministrator: " + ruleAdministrator);
	} catch (ClassNotFoundException e) {
	    System.out.println("Engine "
		    + "Error: The Rule Engine Implementation could not be found");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	// Create built-in object
	calc = new Calculator();
	ener = new Energy();
	mealDist = new MealDist();
	mnd = new MajorNutriDist();
	enerVal = new EnergyValue();
    }

    /**
     * Setup a stateful rule session to use with a specific rule set. The session must be explicitly
     * released after being used, this can be done by calling finishSession().
     * 
     * @param inRuleset
     *            InputStream of a XML rule set
     */
    @SuppressWarnings("rawtypes")
    public void setupSession(InputStream inRuleset) {
	RuleExecutionSet res;
	String uri;

	// Load ruleset from the XML document
	try {
	    res = ruleAdministrator.getLocalRuleExecutionSetProvider(null)
		    .createRuleExecutionSet(inRuleset, null);
	    inRuleset.close();
	    System.out.println("Loaded RuleExecutionSet: " + res);
	} catch (Exception e) {
	    System.err.println("Error: Cannot parse ruleset XML file");
	    return;
	}

	// Register the RuleExecutionSet
	try {
	    uri = res.getName();
	    ruleAdministrator.registerRuleExecutionSet(uri, res, null);
	    System.out.println("Bound RuleExecutionSet to URI: " + uri);
	} catch (Exception e) {
	    System.err.println("Error: Cannot register RuleExecutionSet");
	    return;
	}

	// Get a RuleRuntime and create session
	try {
	    RuleRuntime ruleRuntime = serviceProvider.getRuleRuntime();
	    System.out.println("Acquired RuleRuntime: " + ruleRuntime);

	    // create a StatefulRuleSession
	    session = (StatefulRuleSession) ruleRuntime
		    .createRuleSession(uri, new HashMap(), RuleRuntime.STATEFUL_SESSION_TYPE);
	} catch (Exception e) {
	    System.err.println("Error: Cannot create session");
	    return;
	}

	// Pre-install some objects
	try {
	    session.addObject(calc); // Calculator
	    session.addObject(ener); // Energy fact
	    session.addObject(mealDist); // Meal distribution
	    session.addObject(mnd); // Major nutrient distribution
	    session.addObject(enerVal); // Energy value of nutrients
	    System.out.println("Pre-installed some objects");
	} catch (InvalidRuleSessionException | RemoteException e) {
	    System.err.println("Error: Cannot add pre-installed objects");
	    return;
	}
    }

    /**
     * Input user information object
     * 
     * @param user
     */
    public void inputUser(User user) {
	try {
	    session.addObject(user);
	} catch (InvalidRuleSessionException | RemoteException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Execute rules. Input should be added before calling this method.
     */
    @SuppressWarnings("rawtypes")
    public void executeRules() {
	try {
	    session.executeRules();

	    // TODO Print out working memory for test
	    List results = null;
	    results = session.getObjects();

	    // Output
	    System.out.println("Result of calling getObjects: " + results.size() + " results.");
	    // Loop over the results.
	    Hashtable wm = ((StatefulRuleSessionImpl) session).getWorkingMemory();
	    Enumeration en = wm.keys();
	    while (en.hasMoreElements()) {
		Object obj = en.nextElement();
		System.out.println("Clause Found: " + obj + " " + wm.get(obj));
	    }
	} catch (InvalidRuleSessionException | RemoteException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Release the session after finish with it.
     */
    public void finishSession() {
	try {
	    session.release();
	} catch (InvalidRuleSessionException | RemoteException e) {
	    e.printStackTrace();
	}
    }

}
