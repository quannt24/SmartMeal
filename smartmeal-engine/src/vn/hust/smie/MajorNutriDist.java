/**
 * 
 */
package vn.hust.smie;


/**
 * Distribution of major nutrients
 * 
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class MajorNutriDist {

    private double pro; // Protit
    private double lip; // Lipit
    private double glu; // Gluxit
    
    public MajorNutriDist() {
	pro = 0.15;
	lip = 0.25;
	glu = 0.65;
	// Sum of all distributions should be 1
    }
    
    /**
     * @return the pro
     */
    public double getPro() {
        return pro;
    }
    
    /**
     * @param pro the pro to set
     */
    public void setPro(double pro) {
        if (0 <= pro && pro <= 1) this.pro = pro;
    }
    
    /**
     * @return the lip
     */
    public double getLip() {
        return lip;
    }
    
    /**
     * @param lip the lip to set
     */
    public void setLip(double lip) {
        if (0 <= lip && lip <= 1) this.lip = lip;
    }
    
    /**
     * @return the glu
     */
    public double getGlu() {
        return glu;
    }

    /**
     * @param glu the glu to set
     */
    public void setGlu(double glu) {
        if (0 <= glu && glu <= 1) this.glu = glu;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "MajorNutriDist [pro=" + pro + ", lip=" + lip + ", glu=" + glu + "]";
    }
    
}
