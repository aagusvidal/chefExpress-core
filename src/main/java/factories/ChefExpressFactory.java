package factories;

import entities.Recipe;
import finders.YTVideoLinkSearcher;
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

    private VideoLinkSearcherFactory videoFinderFactory;

    private List<RecipeScorer> recipeScorers;
    public ChefExpressFactory()
    {
        this.scorerFactory = new RecipeScorerFactory();
        this.recipesFinder = new RecipesFinder();
        this.videoFinderFactory = new VideoLinkSearcherFactory();
    }

    public ChefExpress createChefExpress(String propertyPath) throws Exception
    {
        Properties chefExpressProperties = loadProperties(propertyPath);
        recipeScorers = this.scorerFactory.create(chefExpressProperties.getProperty("ScorersPath"));
        Set<Recipe> recipes = recipesFinder.findRecipes(chefExpressProperties.getProperty("RecipesPath"));

        Properties videoFinderProperties = loadProperties(chefExpressProperties.getProperty("VideoFinderProperties"));
        YTVideoLinkSearcher videoFinder= this.videoFinderFactory.create(videoFinderProperties);

        return new ChefExpress(recipes, recipeScorers.get(0), videoFinder);
    }

    private Properties loadProperties(String configPath)
    {
        Properties prop = new Properties();
        try
        {
            prop.load( new FileInputStream(configPath) );
        } catch (IOException e) { e.printStackTrace(); }

        return prop;
    }

    public List<RecipeScorer> getRecipeScorers() {
        return recipeScorers;
    }
}
