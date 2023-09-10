package entities;
import java.util.HashMap;
import java.util.List;

public class Recipe {

	protected int id;
	protected HashMap<String, Float> ingredients;
	protected List<String> instructions;

	public Recipe(int id, HashMap<String, Float> ingredients, List<String> instructions) {
		this.id = id;
		this.ingredients = ingredients;
		this.instructions = instructions;
	}
	
}
