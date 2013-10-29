/**
 * 
 */
package vn.hust.smie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.rules.InvalidRuleSessionException;
import javax.rules.StatefulRuleSession;

import org.jruleengine.StatefulRuleSessionImpl;

/**
 * @author Quan T. Nguyen <br>
 *         Hanoi University of Science and Technology
 */
public class Tester {

    /**
     * @param args
     */
    public static void main(String[] args) {
	// TODO Auto-generated method stub
	User user = new User();
	user.setName("Le Quan");
	user.setSex(User.SEX_MALE);
	user.setYearOfBirth(1991);
	user.setHeight(1.65);
	user.setWeight(72);
	user.setActivity(User.ACTIVITY_MANY);

	InputStream inStream = null;
	try {
	    inStream = new FileInputStream(new File("res/rule/smartmeal.xml"));
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	Smie smie = new Smie();
	StatefulRuleSession session = smie.setupSession(inStream);

	// TODO Test rules here
	try {
	    // Add input
	    session.addObject(user);

	    // Execute rules
	    List results = null;
	    session.executeRules();
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
	} catch (InvalidRuleSessionException e) {
	    e.printStackTrace();
	} catch (RemoteException e) {
	    e.printStackTrace();
	} finally {
	    smie.finishSession(session);
	}
    }

}
