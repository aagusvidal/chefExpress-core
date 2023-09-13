package factories;

import entities.Recipe;
import parsers.RecipeParser;

import java.util.Set;

public class ChefExpressFactory {
    RecipeParser recipeParser;
    protected String propertyPath;
    public ChefExpressFactory(String propertyPath){
        this.propertyPath = propertyPath;
        this.recipeParser = new RecipeParser();
    }

    private Set<Recipe> parserRecipes(){
       return recipeParser.parserRecipes();
    }

}
