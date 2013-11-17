/**
 * 
 */
package vn.hust.smie;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.rules.Handle;
import javax.rules.InvalidHandleException;
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
    
    private DishCollection dc;
    private History history;
    
    private Handle mealHandle;
    
    public Smie(DishCollection dc, IngredientCollection ic) {
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
	
	// Inputed collections
	this.dc = dc;
	
	// History
	this.history = null;
	
	// Handle of Meal object in engine's working memory
	this.mealHandle = null;
    }
    
    public ArrayList<Dish> getDishes(){
    	return dc.getDishList();
    }
    
    /**
     * @return the history
     */
    public History getHistory() {
        return history;
    }

    /**
     * @param history the history to set
     */
    public void setHistory(History history) {
        this.history = history;
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
	    session.addObject(dc); // Dish collection
	    System.out.println("Pre-installed some objects");
	} catch (Exception e) {
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
	} catch (InvalidRuleSessionException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Setup a meal with requirements then add to working memory. This method should only be called
     * after the engine executes rules with input User object. To setup the meal's menu, execute
     * rules again. 
     * 
     * @param type
     */
    public void setupMeal(int type) {
	double dist; // Energy distribution of this meal
	double reqEnergy; // Total energy of this meal
	double reqProAmount; // Protein amount in gram
	double reqLipAmount; // Lipid amount in gram
	double reqGluAmount; // Glucid amount in gram
	
	switch (type) {
	case Meal.TYPE_BREAKFAST:
	    dist = mealDist.getBreakfastDist();
	    break;
	    
	case Meal.TYPE_LUNCH:
	    dist = mealDist.getLunchDist();
	    break;
	    
	case Meal.TYPE_DINNER:
	    dist = mealDist.getDinnerDist();
	    break;

	default:
	    dist = mealDist.getAuxiliaryDist();
	    break;
	}
	
	// Setup meal's requirements
	reqEnergy = ener.getEnergy() * dist;
	reqProAmount = mnd.getProServing() * dist;
	reqLipAmount = mnd.getLipServing() * dist;
	reqGluAmount = mnd.getGluServing() * dist;
	if (history != null) {
	    reqEnergy += history.getShortedEnergy();
	    reqProAmount += history.getShortedPro();
	    reqLipAmount += history.getShortedLip();
	    reqGluAmount += history.getShortedGlu();
	}
	
	// Remove old meal in working memory
	try {
	    if (mealHandle != null && session.containsObject(mealHandle)) {
	        session.removeObject(mealHandle);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	// Add meal to working memory
	try {
	    mealHandle = session.addObject(new Meal(dc, type, reqEnergy, reqProAmount,
		    reqLipAmount, reqGluAmount));
	} catch (InvalidRuleSessionException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Execute rules. Input should be added before calling this method.
     */
    public void executeRules() {
	try {
	    session.executeRules();
	} catch (InvalidRuleSessionException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Get generated meal. This method should only be called after all necessary inference steps
     * complete and before the session is released (by calling finishSession()).
     * 
     * @return
     */
    public Meal getMeal() {
	if (mealHandle == null) return null;
	
	try {
	    return (Meal) session.getObject(mealHandle);
	} catch (InvalidHandleException e) {
	    e.printStackTrace();
	} catch (InvalidRuleSessionException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	return null;
    }
    
    /**
     * Output working memory to standard output
     */
    @SuppressWarnings("rawtypes")
    public void printWorkingMemory() {
	List results = null;
	try {
	    results = session.getObjects();
	} catch (InvalidRuleSessionException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	// Output
	System.out.println("\nResult of calling getObjects: " + results.size() + " results.");
	// Loop over the results.
	Hashtable wm = ((StatefulRuleSessionImpl) session).getWorkingMemory();
	Enumeration en = wm.keys();
	while (en.hasMoreElements()) {
	    Object obj = en.nextElement();
	    System.out.println("Clause Found: " + obj + " " + wm.get(obj));
	}
    }
    
    /**
     * Release the session after finish with it.
     */
    public void finishSession() {
	try {
	    session.release();
	} catch (InvalidRuleSessionException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
