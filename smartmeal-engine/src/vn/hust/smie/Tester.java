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
	smie.setupSession(inStream);
	
	// Add input here
	smie.inputUser(user);
	smie.executeRules();
	smie.finishSession();
    }

}
