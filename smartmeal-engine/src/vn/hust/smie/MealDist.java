/**
 * 
 */
package vn.hust.smie;


/**
 * Distribution of meals in one day.
 * 
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class MealDist {
    
    private double breakfastDist;
    private double lunchDist;
    private double dinnerDist;
    
    public MealDist() {
	breakfastDist = 0.25;
	lunchDist = 0.25;
	dinnerDist = 0.25;
	// Sum of all distributions should be 1
    }
    
    /**
     * @return the breakfastDist
     */
    public double getBreakfastDist() {
        return breakfastDist;
    }

    /**
     * @param breakfastDist the breakfastDist to set
     */
    public void setBreakfastDist(double breakfastDist) {
        if (breakfastDist <= 1) this.breakfastDist = breakfastDist;
    }

    /**
     * @return the lunchDist
     */
    public double getLunchDist() {
        return lunchDist;
    }

    /**
     * @param lunchDist the lunchDist to set
     */
    public void setLunchDist(double lunchDist) {
	if (lunchDist <= 1) this.lunchDist = lunchDist;
    }

    /**
     * @return the dinnerDist
     */
    public double getDinnerDist() {
        return dinnerDist;
    }

    /**
     * @param dinnerDist the dinnerDist to set
     */
    public void setDinnerDist(double dinnerDist) {
        if (dinnerDist <= 1) this.dinnerDist = dinnerDist;
    }
    
    /**
     * @return Distribution of auxiliary meal
     */
    public double getAuxiliaryDist() {
	double dist = 1.0 - breakfastDist - lunchDist - dinnerDist;
	if (0 <= dist && dist <= 1) return dist;
	else return 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "MealDist [breakfastDist=" + breakfastDist + ", lunchDist=" + lunchDist
		+ ", dinnerDist=" + dinnerDist + ", auxiliaryDist=" + getAuxiliaryDist() + "]";
    }
    
}
