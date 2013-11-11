package vn.hust.smie;

/**
 * Ingredient with its energy and nutrients per 100g
 * 
 * @author Quan T. Nguyen <br>
 * Hanoi University of Science and Technology
 */
public class Ingredient {

    private int id;

    private String name;
    private String type;

    private float energy;

    private float protein;
    private float lipid;
    private float glucid;
    private float cell; // Celluloza

    public Ingredient(String id, String name, String type, String energy, String protein, String lipid,
		      String glucid, String celluloza) {
	this.id = Integer.parseInt(id);
	this.name = name;
	this.type = type;

	this.energy = Integer.parseInt(energy);
	this.protein = Float.parseFloat(protein);
	this.lipid = Float.parseFloat(lipid);
	this.glucid = Float.parseFloat(glucid);
	this.cell = Float.parseFloat(celluloza);
    }

    public void print() {
	System.out.printf("%-3d %-13s %-10s %-5.1f %-5.1f  %-5.1f  %-5.1f  %-5.1f\n", id, name,
			  type, energy, protein, lipid, glucid, cell);
    }

    public int getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public String getType() {
	return type;
    }

    public float getEnergy() {
	return energy;
    }

    public float getProtein() {
	return protein;
    }

    public float getLipid() {
	return lipid;
    }

    public float getGlucid() {
	return glucid;
    }

    public float getCell() {
	return cell;
    }
}
