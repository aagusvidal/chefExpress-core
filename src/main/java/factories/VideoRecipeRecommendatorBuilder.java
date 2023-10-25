package factories;

import core.ChefExpress;
import core.VideoRecipeRecommendator;
import finders.VideoSearcher;
import interfaces.RecipeScorer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class VideoRecipeRecommendatorBuilder
{
    private ChefExpressInit chefExpressInit;
    private ChefExpress chefExpress;

    public VideoRecipeRecommendatorBuilder()
    {
        this.chefExpressInit =  new ChefExpressInit();
    }

    public VideoRecipeRecommendator build(String propertyPath) throws Exception
    {
        Properties chefExpressProperties = loadProperties(propertyPath);;
        this.chefExpress = this.chefExpressInit.initChefExpress(chefExpressProperties);

        Properties videoFinderProperties = loadProperties(chefExpressProperties.getProperty("VideoFinderProperties"));
        VideoSearcher videoSearcher = new VideoSearcherFactory().create(videoFinderProperties);

        return new VideoRecipeRecommendator(videoSearcher, chefExpress);
    }

    public ChefExpress getChefExpress(){ return chefExpress;}

    public List<RecipeScorer> getRecipeScorers() { return this.chefExpressInit.getRecipeScorers(); }

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
