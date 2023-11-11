package IT1;

import core.ChefExpress;
import core.HistoricalRecipesCounter;
import core.RecipesUpdater;
import entities.Recipe;
import finders.LocalRecipesFactory;
import finders.RecipesProvider;
import interfaces.RecipeScorer;
import interfaces.RecipesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserStory3
{
    private HistoricalRecipesCounter historicalRecipesCounter;
    private RecipeScorer scorerMock;
    private ChefExpress chefExpress;
    private List<Recipe> recipes;

    private RecipesProvider recipesProvider;

    @BeforeEach
    public void setUp()
    {
        List<Integer> recipeIds = Arrays.asList(1, 2);
        recipes = mockRecipes(recipeIds);

        scorerMock = mock(RecipeScorer.class);
        recipesProvider = this.createRecipeProvider(recipes);
        chefExpress = new ChefExpress(recipesProvider, scorerMock, new HashMap<>());
        historicalRecipesCounter = new HistoricalRecipesCounter(chefExpress);
    }

    private void setScorerResult(Map<Recipe, Integer> scorerResult)
    {
        for (Map.Entry<Recipe, Integer> entry : scorerResult.entrySet())
        {
            when(this.scorerMock.score(entry.getKey())).thenReturn(entry.getValue());
        }
    }

    @Test
    public void ca1SinRecetasMasRecomendadas()
    {
        Map<Recipe, Integer> scorerResults = Map.of(recipes.get(0),0, recipes.get(1), 0);
        this.setScorerResult(scorerResults);

        this.chefExpress.recommend();

        assertTrue(historicalRecipesCounter.getHistoricRecipes().isEmpty());
    }

    @Test
    public void ca2MultiplesRecetasMasRecomendadas()
    {
        Map<Recipe, Integer> scorerResults = Map.of(recipes.get(0),10, recipes.get(1), 10);
        this.setScorerResult(scorerResults);

        this.chefExpress.recommend();

        scorerResults = Map.of(recipes.get(0),10, recipes.get(1), 0);
        this.setScorerResult(scorerResults);

        this.chefExpress.recommend();

        assert historicalRecipesCounter.getHistoricRecipes().containsKey(recipes.get(0));
        assert historicalRecipesCounter.getHistoricRecipes().containsKey(recipes.get(1));

        assert historicalRecipesCounter.getHistoricRecipes().get(recipes.get(0)) == 2;
        assert historicalRecipesCounter.getHistoricRecipes().get(recipes.get(1)) == 1;
    }

    @Test
    public void ca3UnicaRecetaMasRecomendada()
    {
        Map<Recipe, Integer> scorerResults = Map.of(recipes.get(0),10, recipes.get(1), 0);
        this.setScorerResult(scorerResults);

        this.chefExpress.recommend();

        assert historicalRecipesCounter.getHistoricRecipes().containsKey(recipes.get(0));
        assert historicalRecipesCounter.getHistoricRecipes().get(recipes.get(0)) == 1;
    }


    private List<Recipe> mockRecipes(List<Integer> ids)
    {
        return ids.stream()
                .map(recipeId -> new Recipe(recipeId, "recipe " + recipeId, new HashMap<>()))
                .collect(Collectors.toList());
    }

    private RecipesProvider createRecipeProvider(List<Recipe> recipesList) {
        RecipesFactory recipesLocalFinder = new LocalRecipesFactory();
        String recipesPath =  "";
        HashSet<Recipe> recipes = new HashSet<>(recipesList);
        RecipesUpdater recipesUpdater = new RecipesUpdater(recipesLocalFinder,  List.of(recipesPath.split(",")),  recipes);
        recipesProvider = new RecipesProvider(recipes, recipesUpdater);
        return recipesProvider;
    }
}
