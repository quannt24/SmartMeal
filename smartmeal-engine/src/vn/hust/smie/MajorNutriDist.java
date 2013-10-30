/**
 * 
 */
package vn.hust.smie;


/**
 * Distribution of major nutrients in one day. This class includes ratio of major nutrients per day
 * and their corresponding amount in gram.
 * 
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class MajorNutriDist {

    private double proDist; // Protit distribution
    private double lipDist; // Lipit distribution
    private double gluDist; // Gluxit distribution
    
    private double proServing; // Protit serving in gram
    private double lipServing; // Lipit serving in gram
    private double gluServing; // Gluxit serving in gram
    
    public MajorNutriDist() {
	proDist = 0.15;
	lipDist = 0.25;
	gluDist = 0.65;
	// Sum of all distributions should be 1
	
	proServing = 0;
	lipServing = 0;
	gluServing = 0;
    }
    
    /**
     * @return the pro
     */
    public double getProDist() {
        return proDist;
    }
    
    /**
     * @param proDist the pro to set
     */
    public void setProDist(double proDist) {
        if (0 <= proDist && proDist <= 1) this.proDist = proDist;
    }
    
    /**
     * @return the lip
     */
    public double getLipDist() {
        return lipDist;
    }
    
    /**
     * @param lipDist the lip to set
     */
    public void setLipDist(double lipDist) {
        if (0 <= lipDist && lipDist <= 1) this.lipDist = lipDist;
    }
    
    /**
     * @return the glu
     */
    public double getGluDist() {
        return gluDist;
    }

    /**
     * @param gluDist the glu to set
     */
    public void setGluDist(double gluDist) {
        if (0 <= gluDist && gluDist <= 1) this.gluDist = gluDist;
    }

    
    /**
     * @return the proServing
     */
    public double getProServing() {
        return proServing;
    }

    
    /**
     * @param proServing the proServing to set
     */
    public void setProServing(double proServing) {
        this.proServing = proServing;
    }

    
    /**
     * @return the lipServing
     */
    public double getLipServing() {
        return lipServing;
    }

    
    /**
     * @param lipServing the lipServing to set
     */
    public void setLipServing(double lipServing) {
        this.lipServing = lipServing;
    }

    
    /**
     * @return the gluServing
     */
    public double getGluServing() {
        return gluServing;
    }

    
    /**
     * @param gluServing the gluServing to set
     */
    public void setGluServing(double gluServing) {
        this.gluServing = gluServing;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "MajorNutriDist [proDist=" + proDist + ", lipDist=" + lipDist + ", gluDist="
		+ gluDist + ", proServing=" + proServing + ", lipServing=" + lipServing
		+ ", gluServing=" + gluServing + "]";
    }
    
}
