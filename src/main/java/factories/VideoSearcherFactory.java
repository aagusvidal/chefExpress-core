package factories;

import core.ChefExpress;
import finders.RecipesVideoUpdater;
import services.YTService;

import java.util.Properties;

public class VideoSearcherFactory
{
    public RecipesVideoUpdater create(Properties properties, ChefExpress chefExpress)
    {
        String ytApiPath = properties.getProperty("YTApiPath");
        String apiKey = properties.getProperty("YTApiKey");
        String ytBasePath = properties.getProperty("YTBasePath");

        YTService ytService = new YTService(ytApiPath, apiKey);

        return new RecipesVideoUpdater(ytService, ytBasePath, chefExpress);
    }
}
