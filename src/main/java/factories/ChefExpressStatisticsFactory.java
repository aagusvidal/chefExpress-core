package factories;

import core.ChefExpress;
import core.ChefExpressStatistics;
import core.HistoricalRecipesCounter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ChefExpressStatisticsFactory
{
    public ChefExpressStatistics create(ChefExpress recommender, String propertyPath)
    {
        HistoricalRecipesCounter historicalRecipesCounter = new HistoricalRecipesCounterFactory().createHistoricalRecipesCounter(recommender);
        return new ChefExpressStatistics(historicalRecipesCounter);
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
