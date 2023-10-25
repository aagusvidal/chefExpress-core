package factories;

import core.ChefExpress;
import core.RecipesUpdater;
import entities.Recipe;
import finders.LocalRecipesFinder;
import interfaces.RecipeScorer;
import interfaces.RecipesFinder;

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
        RecipesFinder recipesLocalFinder = new LocalRecipesFinder();
        Set<Recipe> recipes = recipesLocalFinder.findRecipes(chefExpressProperties.getProperty("RecipesPath"));

        String chefExpressScorersPath = chefExpressProperties.getProperty("ScorersPath");
        ChefExpress chefExpress = chefExpressFactory.createChefExpress(chefExpressScorersPath, recipes);

        String recipesPath = chefExpressProperties.getProperty("RecipesUpdaterPath");
        RecipesUpdater recipesUpdater = new RecipesUpdater(1L, recipesLocalFinder,
                                            List.of(recipesPath.split(",")));
        recipesUpdater.attach(chefExpress);

        return chefExpress;
    }

    public List<RecipeScorer> getRecipeScorers() {
        return this.chefExpressFactory.getRecipeScorers();
    }
}
