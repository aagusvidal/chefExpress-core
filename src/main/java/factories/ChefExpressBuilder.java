package factories;

import core.ChefExpress;
import core.RecipesProvider;
import core.RecipesUpdater;
import core.VideoRecipesUpdater;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ChefExpressBuilder
{
    private ChefExpressFactory chefExpressFactory;

    public ChefExpressBuilder()
    {
        this.chefExpressFactory =  new ChefExpressFactory();
    }

    public ChefExpress build(String propertyPath, RecipesUpdater recipesUpdater) throws Exception
    {
        Properties chefExpressProperties = loadProperties(propertyPath);
        Properties videoProperties = loadProperties(chefExpressProperties.getProperty("VideoUpdaterProperties"));
        String chefExpressScorersPath = chefExpressProperties.getProperty("ScorersPath");
        RecipesProvider recipesProvider = new RecipesProvider(recipesUpdater);

        ChefExpress recommender = this.chefExpressFactory.createChefExpress(chefExpressScorersPath, recipesProvider);

        VideoRecipeUpdaterFactory videoRecipesUpdaterFactory = new VideoRecipeUpdaterFactory();
        VideoRecipesUpdater videoRecipeUpdater = videoRecipesUpdaterFactory.create(videoProperties, recommender);

        return recommender;
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
}
