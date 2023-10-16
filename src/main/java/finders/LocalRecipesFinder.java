package finders;

import entities.Recipe;
import interfaces.RecipesFinder;
import parsers.RecipeParser;

import java.util.Set;

public class LocalRecipesFinder implements RecipesFinder {
    private RecipeParser recipeParser;
    public LocalRecipesFinder()
    {
        recipeParser = new RecipeParser();
    }


    public Set<Recipe> findRecipes(String filePath)
    {
        return recipeParser.parseRecipes(filePath);
    }
}
