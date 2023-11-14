package factories;

import core.RecipesUpdater;
import entities.Recipe;
import finders.LocalRecipesFactory;
import interfaces.RecipesFactory;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public class RecipesUpdaterFactory {

    public RecipesUpdaterFactory(){

    }

    public RecipesUpdater  createRecipesUpdater(Properties chefExpressProperties){
        RecipesFactory recipesLocalFinder = new LocalRecipesFactory();
        Set<Recipe> recipes = recipesLocalFinder.findRecipes("/"+ chefExpressProperties.getProperty("RecipesPath"));
        String recipesPath = chefExpressProperties.getProperty("RecipesUpdaterPath");
        RecipesUpdater recipesUpdater = new RecipesUpdater(recipesLocalFinder,  List.of(recipesPath.split(",")),  recipes);
        return recipesUpdater;
    }
}
