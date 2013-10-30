/**
 * 
 */
package vn.hust.smie;

/**
 * Calculator for Inference Engine. An instance of Calculator can be passed to Working Memory, so
 * that Inference Engine can use it to calculate some expressions.
 * 
 * @author Quan T. Nguyen <br>
 *         Hanoi University of Science and Technology
 */
public class Calculator {

    private double result; // Most recent result
    
    public Calculator() {
	result = 0;
    }
    
    /**
     * Get most recent result
     * 
     * @return Result
     */
    public double getResult() {
	return result;
    }
    
    /**
     * First order polynomial in form "p = a * x + b"
     * 
     * @param a
     * @param x
     * @param b
     */
    public void poly1(double a, double x, double b) {
	result = a * x + b;
    }
    
    /**
     * a * b / c
     * 
     * @param a
     * @param b
     * @param c
     */
    public void muldiv(double a, double b, double c) {
	result = a * b / c;
    }

}
