package IT1;

import core.ChefExpress;
import core.RecipesUpdater;
import entities.Recipe;
import finders.LocalRecipesFactory;
import core.RecipesProvider;
import interfaces.RecipeScorer;
import interfaces.RecipesFactory;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.HistoricalRecipesCounter;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserStory3
{
    private HistoricalRecipesCounter historicalRecipesCounter;
    private RecipeScorer scorerMock;

    private RecipesProvider recipesProvider;
    private ChefExpress chefExpress;
    private List<Recipe> recipes;

    @BeforeEach
    public void setUp()
    {
        List<Integer> recipeIds = Arrays.asList(1, 2);
        recipes = mockRecipes(recipeIds);

        scorerMock = mock(RecipeScorer.class);
        recipesProvider = this.createRecipesProvider(recipes);
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
    @Description("Sin recetas recomendadas")
    public void ca1SinRecetasRecomendadas()
    {
        Map<Recipe, Integer> scorerResults = Map.of(recipes.get(0),0, recipes.get(1), 0);
        this.setScorerResult(scorerResults);
        this.chefExpress.recommend();
        assertTrue(historicalRecipesCounter.getHistoricRecipes().isEmpty());
    }

    @Test
    @Description("Recetas recomendadas múltiples veces")
    public void ca2RecetasRecomendadasMultiplesVeces()
    {

        this.mockScorerResults(10, 10);
        this.chefExpress.recommend();
        this.mockScorerResults(10, 0);
        this.chefExpress.recommend();

        assert historicalRecipesCounter.getHistoricRecipes().containsKey(recipes.get(0)) && historicalRecipesCounter.getHistoricRecipes().containsKey(recipes.get(1));
        assert historicalRecipesCounter.getHistoricRecipes().get(recipes.get(0)) == 2  && historicalRecipesCounter.getHistoricRecipes().get(recipes.get(1)) == 1;
    }

    @Test
    @Description("Receta recomendada una única vez")
    public void ca3RecetaRecomendadaUnaUnicaVez()
    {
        this.mockScorerResults(10, 0);
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

    private void mockScorerResults(Integer scoreRecipe1, Integer scoreRecipe2) {
        Map<Recipe, Integer> scorerResults = Map.of(recipes.get(0),scoreRecipe1, recipes.get(1), scoreRecipe2);
        this.setScorerResult(scorerResults);
    }

    private RecipesProvider createRecipesProvider(List<Recipe> recipesList) {
        RecipesFactory recipesLocalFinder = new LocalRecipesFactory();
        String recipesPath =  "";
        HashSet<Recipe> recipes = new HashSet<>(recipesList);
        RecipesUpdater recipesUpdater = new RecipesUpdater(recipesLocalFinder,  List.of(recipesPath.split(",")),  recipes);
        RecipesProvider recipesProvider = new RecipesProvider(recipesUpdater);
        return recipesProvider;
    }
}
