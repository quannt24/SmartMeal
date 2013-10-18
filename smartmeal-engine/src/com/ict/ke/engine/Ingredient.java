package com.ict.ke.engine;


public class Ingredient {

	private int		id;

	private String	name;
	private String	kind;

	private int		energy;

	private float	protein;
	private float	fat;
	private float	carbon;
	private float	cell;

	public Ingredient(int id, String name, String kind, String energy, String protein, String fat, String carbon, String cell) {
		this.id = id;
		this.name = name;
		this.kind = kind;

		this.energy = Integer.parseInt(energy);
		this.protein = Float.parseFloat(protein);
		this.fat = Float.parseFloat(fat);
		this.carbon = Float.parseFloat(carbon);
		this.cell = Float.parseFloat(cell);
	}

	public void print(){
		System.out.printf("%-3d %-13s %-10s %-5d %-5.1f  %-5.1f  %-5.1f  %-5.1f\n", id, name, kind, energy, protein, fat, carbon, cell);
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getKind() {
		return kind;
	}

	public int getEnergy() {
		return energy;
	}

	public float getProtein() {
		return protein;
	}

	public float getFat() {
		return fat;
	}

	public float getCarbon() {
		return carbon;
	}

	public float getCell() {
		return cell;
	}
}
