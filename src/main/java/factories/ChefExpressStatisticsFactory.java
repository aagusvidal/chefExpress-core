package factories;

import core.ChefExpress;
import core.ChefExpressStatistics;
import core.RecommendationLogger;
import core.VideoRecipeRecommendator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ChefExpressStatisticsFactory
{
    public ChefExpressStatistics create(VideoRecipeRecommendator recommender, String propertyPath)
    {
        RecommendationLogger logger = new RecommendationLoggerFactory().createRecommendationLogger(recommender);
        return new ChefExpressStatistics(logger);
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
