/**
 * 
 */
package vn.hust.smie;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Quan T. Nguyen <br>
 *         Hanoi University of Science and Technology
 */
public class Dish implements Serializable {

	private static final long					serialVersionUID	= 8724551202513120324L;

	public transient static final int			TYPE_GRAIN			= 1;
	public transient static final int			TYPE_VEGETABLE		= 2;
	public transient static final int			TYPE_PROTEIN		= 3;
	public transient static final int			TYPE_FRUIT			= 4;
	public transient static final int			TYPE_MIXED			= 5;

	private transient int						id;
	private String								name;
	private transient int						type;

	private transient int						quantity;
	private transient String					unit;

	private transient ArrayList<DishComponent>	compList;

	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param quantity
	 * @param unit
	 */
	public Dish(String id, String name, String type, String quantity, String unit) {
		this.id = Integer.parseInt(id);
		this.name = name;
		this.type = Integer.parseInt(type);
		this.quantity = Integer.parseInt(quantity);
		this.unit = unit;
		compList = new ArrayList<DishComponent>();
	}

	public void addComponent(DishComponent comp) {
		compList.add(comp);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @return the compList
	 */
	public ArrayList<DishComponent> getCompList() {
		return compList;
	}

	/**
	 * Get total energy of this dish in KCal
	 * 
	 * @return
	 */
	public double getEnergy() {
		double tmp = 0;
		for (DishComponent c : compList){
			tmp += c.getIngredient().getEnergy() * c.getAmount() / 100;
		}
		return tmp;
	}

	/**
	 * Get total protein amount in gram
	 * 
	 * @return
	 */
	public double getProAmount() {
		double tmp = 0;
		for (DishComponent c : compList){
			tmp += c.getIngredient().getProtein() * c.getAmount() / 100;
		}
		return tmp;
	}

	/**
	 * Get total lipid amount in gram
	 * 
	 * @return
	 */
	public double getLipAmount() {
		double tmp = 0;
		for (DishComponent c : compList){
			tmp += c.getIngredient().getLipid() * c.getAmount() / 100;
		}
		return tmp;
	}

	/**
	 * Get total glucid amount in gram
	 * 
	 * @return
	 */
	public double getGluAmount() {
		double tmp = 0;
		for (DishComponent c : compList){
			tmp += c.getIngredient().getGlucid() * c.getAmount() / 100;
		}
		return tmp;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dish [id=" + id + ", name=" + name + ", type=" + type + ", quantity=" + quantity
				+ ", unit=" + unit + ", getEnergy()=" + getEnergy() + ", getProAmount()="
				+ getProAmount() + ", getLipAmount()=" + getLipAmount() + ", getGluAmount()="
				+ getGluAmount() + "]";
	}

}
