package factories;

import entities.Recipe;
import finders.RecipeScorerFactory;
import interfaces.RecipeScorer;
import core.ChefExpress;
import parsers.RecipesFinder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ChefExpressFactory
{
    private RecipesFinder recipesFinder;
    private RecipeScorerFactory scorerFactory;
    private Properties chefExpressProperties;

    public ChefExpressFactory(String propertyPath)
    {
        this.chefExpressProperties = loadProperties(propertyPath);
        this.scorerFactory = new RecipeScorerFactory();

        this.recipesFinder = new RecipesFinder();
    }

    public ChefExpress createChefExpress() throws Exception
    {
        List<RecipeScorer> recipeScorers = this.scorerFactory.create(chefExpressProperties.getProperty("ScorersPath"));

        Set<Recipe> recipes = recipesFinder.findRecipes(chefExpressProperties.getProperty("RecipesPath"));

        return new ChefExpress(recipes, recipeScorers.get(0));
    }

    private Properties loadProperties(String configPath)
    {
        Properties prop = new Properties();
        try
        {
            prop.load( new FileInputStream(configPath) ); // Ex: "dir/chefExpress.properties"
        } catch (IOException e) { e.printStackTrace(); }

        return prop;
    }

}
