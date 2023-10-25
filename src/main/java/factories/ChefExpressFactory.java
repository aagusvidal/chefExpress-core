package factories;

import entities.Recipe;
import interfaces.RecipeScorer;
import core.ChefExpress;

import java.util.List;
import java.util.Set;

public class ChefExpressFactory
{
    private RecipeScorersFactory scorerFactory;
    private List<RecipeScorer> recipeScorers;

    public ChefExpressFactory()
    {
        this.scorerFactory = new RecipeScorersFactory();
    }

    public ChefExpress createChefExpress(String scorersPath, Set<Recipe> recipes) throws Exception
    {
        this.recipeScorers = this.scorerFactory.create(scorersPath);

        return new ChefExpress(recipes, recipeScorers.get(0));
    }

    public List<RecipeScorer> getRecipeScorers() {
        return recipeScorers;
    }
}
