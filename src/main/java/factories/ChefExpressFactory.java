package factories;

import entities.Recipe;
import finders.RecipeScorerFinder;
import interfaces.RecipeScorer;
import model.ChefExpress;
import parsers.RecipeParser;

import java.util.HashSet;
import java.util.Set;

public class ChefExpressFactory {
    RecipeParser recipeParser;
    RecipeScorerFinder scorerFinder;
    ChefExpress chefExpress;

    public ChefExpressFactory(String propertyPath) {
        this.scorerFinder = new RecipeScorerFinder(propertyPath);
        this.recipeParser = new RecipeParser();
        //this.chefExpress = new ChefExpress(this.recipeScorers(), //agregarScorerDefault);
    }

    private HashSet<RecipeScorer> recipeScorers() throws Exception {
        return scorerFinder.findClasses();
    }
    private Set<Recipe> parserRecipes(){
       return recipeParser.parserRecipes();
    }


}
