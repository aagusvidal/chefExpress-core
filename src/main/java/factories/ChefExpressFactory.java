package factories;

import entities.Recipe;
import finders.RecipeScorerFinder;
import interfaces.RecipeScorer;
import model.ChefExpress;
import model.ObservableChefExpress;
import parsers.RecipeParser;

import java.util.HashMap;
import java.util.Set;

public class ChefExpressFactory {
    RecipeParser recipeParser;
    RecipeScorerFinder scorerFinder;
    ChefExpress chefExpress;
    HashMap<String, RecipeScorer> recipeScorers;

    String defaultConfig;
    public ChefExpressFactory(String propertyPath, String defaultConfig) {
        this.scorerFinder = new RecipeScorerFinder(propertyPath);
        this.recipeParser = new RecipeParser();
        this.defaultConfig = defaultConfig;
    }

    public ObservableChefExpress createChefExpress() throws Exception{
        this.recipeScorers = recipeScorers();
        ChefExpress recommend = new ChefExpress(parserRecipes(), this.recipeScorers.get(defaultConfig));
        return new ObservableChefExpress(recommend);
    }

    private HashMap<String, RecipeScorer> recipeScorers() throws Exception {
        return scorerFinder.findClasses();
    }
    private Set<Recipe> parserRecipes(){
       return recipeParser.parserRecipes();
    }


}
