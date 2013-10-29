/**
 * 
 */
package vn.hust.smie;


/**
 * Contain facts about needed energy per day.
 * 
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class Energy {
    
    private double basicMetabolism;
    private double energy; // Needed energy per day
    
    public Energy() {
	this.basicMetabolism = 0;
	this.energy = 0;
    }

    /**
     * @return the basicMetabolism
     */
    public double getBasicMetabolism() {
        return basicMetabolism;
    }
    
    /**
     * @param basicMetabolism the basicMetabolism to set
     */
    public void setBasicMetabolism(double basicMetabolism) {
        this.basicMetabolism = basicMetabolism;
    }

    /**
     * @return the energy
     */
    public double getEnergy() {
        return energy;
    }
    
    /**
     * @param energy the energy to set
     */
    public void setEnergy(double energy) {
        this.energy = energy;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Energy [basicMetabolism=" + basicMetabolism + ", energy=" + energy + "]";
    }
    
}
