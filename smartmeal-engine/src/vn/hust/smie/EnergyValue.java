/**
 * 
 */
package vn.hust.smie;


/**
 * Energy value of nutrients in KCal (per gram)
 * 
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public final class EnergyValue {

    public static final double PRO_EV = 4.0;
    public static final double LIP_EV = 9.0;
    public static final double GLU_EV = 4.0;
    
    /**
     * Get energy value of 1 gram protit (in KCal)
     * @return
     */
    public double getProEV() {
	return PRO_EV;
    }
    
    /**
     * Get energy value of 1 gram lipit (in KCal)
     * @return
     */
    public double getLipEV() {
	return LIP_EV;
    }
    
    /**
     * Get energy value of 1 gram gluxit (in KCal)
     * @return
     */
    public double getGluEV() {
	return GLU_EV;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "EnergyValue [getProEV()=" + getProEV() + ", getLipEV()=" + getLipEV()
		+ ", getGluEV()=" + getGluEV() + "]";
    }
    
}
