package entities;

import java.util.HashMap;
import java.util.Map;

public class Recipe {
	protected int id;
	protected String name;
	protected Map<String, Float> ingredients;
	protected String videoLink;

	public Recipe() { }

	public Recipe(int id, String name, Map<String, Float> ingredients) {
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
	}

	public Recipe(int id, String name, Map<String, Float> ingredients, String videoLink) {
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
		this.videoLink = videoLink;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Map<String, Float> getIngredients() {
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

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Recipe recipe = (Recipe) o;

		if (id != recipe.id) return false;
		if (!name.equals(recipe.name)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Recipe{" +
				"id=" + id +
				", name='" + name + '\'' +
				", ingredients=" + ingredients +
				'}';
	}
}
