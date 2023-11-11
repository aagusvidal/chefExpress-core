package factories;

import core.ChefExpress;
import core.RecipesUpdater;
import entities.Recipe;
import finders.LocalRecipesFactory;
import finders.RecipesProvider;
import interfaces.RecipesFactory;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ChefExpressInit
{
    private ChefExpressFactory chefExpressFactory;

    public ChefExpressInit()
    {
        this.chefExpressFactory =  new ChefExpressFactory();
    }

    public ChefExpress initChefExpress(Properties chefExpressProperties) throws Exception
    {
        RecipesFactory recipesLocalFinder = new LocalRecipesFactory();
        Set<Recipe> recipes = recipesLocalFinder.findRecipes("/"+ chefExpressProperties.getProperty("RecipesPath"));
        String recipesPath = chefExpressProperties.getProperty("RecipesUpdaterPath");
        RecipesUpdater recipesUpdater = new RecipesUpdater(recipesLocalFinder,  List.of(recipesPath.split(",")),  recipes);
        RecipesProvider recipesProvider = new RecipesProvider(recipes, recipesUpdater);

        String chefExpressScorersPath = chefExpressProperties.getProperty("ScorersPath");
        ChefExpress chefExpress = chefExpressFactory.createChefExpress(chefExpressScorersPath, recipesProvider);

        recipesUpdater.attach(chefExpress);

        return chefExpress;
    }
}
