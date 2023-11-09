package core;

import entities.Recipe;

import java.util.Map;

public class ChefExpressStatistics
{
    private HistoricalRecipesCounter historicalRecipesCounter;

    public ChefExpressStatistics(HistoricalRecipesCounter historicalRecipesCounter)
    {
        this.historicalRecipesCounter = historicalRecipesCounter;
    }

    public Map<Recipe, Integer> getTopRecipes()
    {
        return historicalRecipesCounter.getHistoricRecipes();
    }
}
