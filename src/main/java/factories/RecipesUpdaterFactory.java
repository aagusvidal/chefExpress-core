package factories;

import core.RecipesUpdater;
import entities.Recipe;
import finders.LocalRecipesFactory;
import interfaces.RecipesFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class RecipesUpdaterFactory {

    public RecipesUpdater createRecipesUpdater(String propertyPath){
        Properties chefExpressProperties = loadProperties(propertyPath);
        RecipesFactory recipesLocalFinder = new LocalRecipesFactory();
        Set<Recipe> recipes = recipesLocalFinder.findRecipes("/"+ chefExpressProperties.getProperty("RecipesPath"));
        List<String> recipesPaths = List.of(chefExpressProperties.getProperty("RecipesUpdaterPath"),chefExpressProperties.getProperty("RecipesPath"));
        RecipesUpdater recipesUpdater = new RecipesUpdater(recipesLocalFinder,  recipesPaths,  recipes);
        return recipesUpdater;
    }

    public Properties loadProperties(String configPath)
    {
        Properties prop = new Properties();
        try
        {
            prop.load( new FileInputStream(configPath) );
        } catch (IOException e) { e.printStackTrace(); }

        return prop;
    }
}
