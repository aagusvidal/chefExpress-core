package core;

import entities.Recipe;

import java.util.List;

public class ChefExpressStatistics
{
    private RecommendationLogger logger;

    public ChefExpressStatistics(RecommendationLogger logger)
    {
        this.logger = logger;
    }

    public List<Recipe> getTopRecipes()
    {
        return logger.getTopRecipes();
    }
}
