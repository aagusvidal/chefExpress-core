package factories;

import entities.Recipe;
import finders.RecipeScorerFinder;
import interfaces.RecipeScorer;
import core.ChefExpress;
import core.ObservableChefExpress;
import parsers.RecipeParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ChefExpressFactory
{
    private RecipeParser recipeParser;
    private RecipeScorerFinder scorerFinder;
    private String defaultScorerName;

    public ChefExpressFactory(String propertyPath)
    {
        Properties chefExpressProperties = loadProperties(propertyPath);

        this.scorerFinder = new RecipeScorerFinder(chefExpressProperties.getProperty("ScorersPath"));
        this.recipeParser = new RecipeParser(chefExpressProperties.getProperty("RecipesPath"));
        this.defaultScorerName = chefExpressProperties.getProperty("DefaultScorer");
    }

    public ObservableChefExpress createChefExpress() throws Exception
    {
        Map<String, RecipeScorer> recipeScorers = scorerFinder.find();
        Set<Recipe> recipes = recipeParser.parseRecipes();

        ChefExpress recommend = new ChefExpress(recipes, recipeScorers.get(defaultScorerName));

        return new ObservableChefExpress(recommend);
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
