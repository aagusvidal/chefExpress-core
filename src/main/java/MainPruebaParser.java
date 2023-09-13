import entities.Recipe;
import parsers.RecipeParser;

import java.util.Set;

public class MainPruebaParser {

    public static void main(String[] args) {
        RecipeParser p = new RecipeParser();
        Set<Recipe> recetas = p.parserRecipes();

        for (Recipe recipe : recetas) {
            System.out.println("ID: " + recipe.getId());
            System.out.println("Nombre: " + recipe.getName());
            System.out.println("Instrucciones: " + recipe.getIngredients());
            System.out.println();
        }
    }
}
