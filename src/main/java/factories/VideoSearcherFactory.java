package factories;

import core.ChefExpress;
import core.RecipesUpdater;
import services.YTService;

import java.util.Properties;

public class VideoSearcherFactory
{
    public RecipesUpdater.RecipesVideoUpdater create(Properties properties, ChefExpress chefExpress)
    {
        String ytApiPath = properties.getProperty("YTApiPath");
        String apiKey = properties.getProperty("YTApiKey");
        String ytBasePath = properties.getProperty("YTBasePath");

        YTService ytService = new YTService(ytApiPath, apiKey);

        return new RecipesUpdater.RecipesVideoUpdater(ytService, ytBasePath, chefExpress);
    }
}
