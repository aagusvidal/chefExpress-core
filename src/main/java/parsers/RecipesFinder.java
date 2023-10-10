package parsers;

import entities.Recipe;

import java.util.Set;

public class RecipesFinder
{
    private RecipeParser recipeParser;
    public RecipesFinder()
    {
        recipeParser = new RecipeParser();
    }

    public Set<Recipe> findRecipes(String filePath)
    {
        return recipeParser.parseRecipes(filePath);
    }
}
