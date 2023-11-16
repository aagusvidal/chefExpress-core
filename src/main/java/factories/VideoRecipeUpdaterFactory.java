package factories;

import core.ChefExpress;
import core.VideoRecipesUpdater;
import services.YTService;

import java.util.Properties;

public class VideoRecipeUpdaterFactory
{
    public VideoRecipesUpdater create(Properties properties, ChefExpress chefExpress)
    {
        String ytApiPath = properties.getProperty("YTApiPath");
        String apiKey = properties.getProperty("YTApiKey");

        YTService ytService = new YTService(ytApiPath, apiKey);

        return new VideoRecipesUpdater(ytService, chefExpress);
    }
}
