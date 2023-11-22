package IT1;

import core.ChefExpress;
import core.RecipesProvider;
import core.VideoRecipesUpdater;
import entities.Recipe;
import interfaces.RecipeScorer;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.YTService;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserStory5
{
    private YTService YTService;
    private ChefExpress chefExpress;
    private RecipeScorer scorerMockDefault;
    private VideoRecipesUpdater videoRecipesUpdater;
    private Map<String, List<String>> recipeVideoIDs;
    private Map<Integer, Integer> expectedScores;
    private Set<Recipe> mockedRecipes;
    private List<Recipe> recipes;
    private List<Recipe> expectedRecipes;
    private RecipesProvider recipesProvider;
    private final String YT_PATH = "https://www.youtube.com/watch?v=";
    private final String BASE_QUERY = "receta de ";

    @BeforeEach
    public void setUp()
    {
        List<Integer> recipeIds = Arrays.asList(1, 2, 3);
        this.recipes = mockRecipes(recipeIds);
        this.expectedRecipes = mockRecipes(Map.of(1, YT_PATH + "videoID-A",
                2, YT_PATH + "videoID-C",
                3, ""));

        this.scorerMockDefault = mock(RecipeScorer.class);
        Map<Recipe, Integer> scorerResults = Map.of(recipes.get(0),10,
                recipes.get(1), 10,
                recipes.get(2), 10);
        this.setScorerResult(scorerResults);

        this.recipesProvider = mock(RecipesProvider.class);
        this.chefExpress = new ChefExpress(this.recipesProvider,
                this.scorerMockDefault, new HashMap<>());

        this.YTService = mock(YTService.class);
        this.recipeVideoIDs =  Map.of(
                recipes.get(0).getName(), List.of("videoID-A", "videoID-B"),
                recipes.get(1).getName(), List.of("videoID-C"),
                recipes.get(2).getName(), Collections.emptyList(),
                "", Collections.emptyList()
        );
        this.setYTServiceResult(this.recipeVideoIDs);

        this.videoRecipesUpdater = new VideoRecipesUpdater(this.YTService, this.chefExpress);
    }

    private List<Recipe> mockRecipes(List<Integer> ids)
    {
        return ids.stream()
                .map(recipeId -> new Recipe(recipeId, "R" + recipeId, new HashMap<>()))
                .collect(Collectors.toList());
    }

    private List<Recipe> mockRecipes(Map<Integer, String> recipe)
    {
        List<Recipe> recipes =  new ArrayList<>();
        for (Map.Entry<Integer, String>entry : recipe.entrySet())
        {
            recipes.add(new Recipe(entry.getKey(), "R"+entry.getKey(), new HashMap<>(), entry.getValue()));
        }
        return recipes;
    }

    private void setScorerResult(Map<Recipe, Integer> scorerResult)
    {
        for (Map.Entry<Recipe, Integer> entry : scorerResult.entrySet())
        {
            when(this.scorerMockDefault.score(entry.getKey())).thenReturn(entry.getValue());
        }
    }

    private void setYTServiceResult(Map<String, List<String>> ytResult)
    {
        for (Map.Entry<String, List<String>>entry : ytResult.entrySet())
        {
            when(this.YTService.getResults(this.BASE_QUERY + entry.getKey() )).thenReturn(entry.getValue());
        }
    }

    @Test
    @Description("Actualizacion de recetas con multiples videos")
    public void ca1ActualizacionDeRecetasConMultiplesVideos()
    {
        when(this.recipesProvider.getRecipes()).thenReturn(Set.of(this.recipes.get(0)));
        List<Recipe> actualRecipes = chefExpress.recommend();

        assert actualRecipes.size() == 1;
        assert  expectedRecipes.contains(actualRecipes.get(0));
        assert this.videoRecipesUpdater.getLinksAdded().contains(actualRecipes.get(0).getVideoLink());
    }

    @Test
    @Description("Actualizacion de recetas con un video")
    public void ca2ActualizacionDeRecetasConUnVideo()
    {
        when(this.recipesProvider.getRecipes()).thenReturn(Set.of(this.recipes.get(1)));
        List<Recipe> actualRecipes = chefExpress.recommend();

        assert actualRecipes.size() == 1;
        assert  expectedRecipes.contains(actualRecipes.get(0));
        assert this.videoRecipesUpdater.getLinksAdded().contains(actualRecipes.get(0).getVideoLink());
    }

    @Test
    @Description("Actualizacion de multiples recetas con video.")
    public void ca3ActualizacionDeMultiplesRecetasConVideo()
    {
        when(this.recipesProvider.getRecipes()).thenReturn(Set.of(this.recipes.get(0), this.recipes.get(1)));
        List<Recipe> actualRecipes = chefExpress.recommend();

        assert actualRecipes.size() == 2;
        assert  expectedRecipes.contains(actualRecipes.get(0));
        assert  expectedRecipes.contains(actualRecipes.get(1));

        assert this.videoRecipesUpdater.getLinksAdded().contains(actualRecipes.get(0).getVideoLink());
        assert this.videoRecipesUpdater.getLinksAdded().contains(actualRecipes.get(1).getVideoLink());
    }

    @Test
    @Description("Receta sin video encontrado")
    public void ca4RecetaSinVideoEncontrado()
    {
        when(this.recipesProvider.getRecipes()).thenReturn(Set.of(this.recipes.get(2)));
        List<Recipe> actualRecipes = chefExpress.recommend();

        assert actualRecipes.size() == 1;
        assert  expectedRecipes.contains(actualRecipes.get(0));
        assert this.videoRecipesUpdater.getLinksAdded().contains(actualRecipes.get(0).getVideoLink());
    }

    @Test
    @Description("Búsqueda sin nombre de receta")
    public void ca5BusquedaSinNombreDeReceta()
    {
        Recipe emptyNameRecipe = new Recipe(0, "", new HashMap<>());
        when(this.recipesProvider.getRecipes()).thenReturn(Set.of(emptyNameRecipe));
        List<Recipe> actualRecipes = chefExpress.recommend();

        assert  actualRecipes.isEmpty();
        assert this.videoRecipesUpdater.getLinksAdded().isEmpty();
    }
}


