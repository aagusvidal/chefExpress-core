package entities;

import java.util.HashMap;

public class Recipe {
	protected int id;
	protected String name;

	protected HashMap<String, Float> ingredients;

	public Recipe(){

	}
	public Recipe(int id, String name, HashMap<String, Float> ingredients) {
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public HashMap<String, Float> getIngredients() {
		return ingredients;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setIngredients(HashMap<String, Float> ingredients) {
		this.ingredients = ingredients;
	}

}
