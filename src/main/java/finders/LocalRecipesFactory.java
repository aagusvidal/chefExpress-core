package finders;

import entities.Recipe;
import interfaces.RecipesFactory;
import parsers.RecipeParser;

import java.util.Set;

public class LocalRecipesFactory implements RecipesFactory {
    private RecipeParser recipeParser;
    public LocalRecipesFactory()
    {
        recipeParser = new RecipeParser();
    }


    public Set<Recipe> findRecipes(String filePath)
    {
        return recipeParser.parseRecipes(filePath);
    }
}
