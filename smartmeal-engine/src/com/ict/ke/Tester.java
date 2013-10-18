/**
 * 
 */
package com.ict.ke;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.rules.InvalidRuleSessionException;
import javax.rules.StatefulRuleSession;
import javax.rules.StatelessRuleSession;

import org.jruleengine.Clause;
import org.jruleengine.StatefulRuleSessionImpl;

import com.ict.ke.engine.Evaluate;
import com.ict.ke.engine.Smie;
import com.ict.ke.engine.user.User;

/**
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setName("L� Qu�n");
		user.setYear(2012);
		user.setHeight(165);
		user.setWeight(72);
		user.setActivity(Evaluate.ACTIVITY_MANY);
		
		Smie smie = new Smie();
		StatefulRuleSession session = smie.setupSession("res/rule/calperday.xml");
		
		// TODO Test rules here
		try {
			// Add input
			ArrayList<Object> input = new ArrayList<Object>();
			input.add(user);
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
		} catch (InvalidRuleSessionException | RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		smie.finishSession(session);
	}

}
