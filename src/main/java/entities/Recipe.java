package entities;
import java.util.List;

public class Recipe {

	protected int id;
	protected List<Ingredient> ingredients;
	
	public Recipe(int id, List<Ingredient> ingredients) {
		this.id = id;
		this.ingredients = ingredients;
	}
	
}
