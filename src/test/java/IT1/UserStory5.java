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
    private RecipesProvider recipesProvider;
    private final String YT_PATH = "https://www.youtube.com/watch?v=";
    private final String BASE_QUERY = "receta de ";

    @BeforeEach
    public void setUp()
    {
        this.scorerMockDefault = mock(RecipeScorer.class);
        this.recipesProvider = mock(RecipesProvider.class);
        this.YTService = mock(YTService.class);

        this.chefExpress = new ChefExpress(this.recipesProvider,
                this.scorerMockDefault, new HashMap<>());
        this.videoRecipesUpdater = new VideoRecipesUpdater(this.YTService, this.chefExpress);

        this.recipeVideoIDs =  Map.of(
                "R1", List.of("videoID-A", "videoID-B"),
                "R2", List.of("videoID-C"),
                "R3", Collections.emptyList(),
                "", Collections.emptyList()
        );

        this.mockedRecipes = Set.of(
                mockRecipe(1, "R1"),
                mockRecipe(2, "R2"),
                mockRecipe(3, "R3"),
                mockRecipe(4, "")
        );

        expectedScores = Map.of(
                1, 8,
                2, 5,
                3,5,
                4,5
        );
    }

    @Test
    @Description("Actualización de recetas con múltiples videos")
    public void ca1ActualizacionDeRecetasConMultiplesVideos()
    {

        Map<String, List<String>> recipeVideoIDs = this.getSubMap(List.of("R1","R2"));
        Set<Recipe> recipes = getSubset(List.of(1, 2));

        List<Recipe> expectedRecipes = List.of(
                mockRecipeWithVideoLink(1, "R1", "videoID-A"),
                mockRecipeWithVideoLink(2, "R2", "videoID-C")
        );

        when(this.recipesProvider.getRecipes()).thenReturn(recipes);

        setExpectedMockScore(recipes);

        recipeVideoIDs.keySet().forEach(r ->
                when(this.YTService.getResults(this.BASE_QUERY + r))
                        .thenReturn(this.recipeVideoIDs.get(r)));

        List<Recipe> actualRecipes = chefExpress.recommend();

        assertEquals(actualRecipes, expectedRecipes);

    }

    @Test
    @Description("Actualización de recetas con un video")
    public void ca2ActualizacionDeRecetasConUnVideo()
    {
        Map<String, List<String>> recipeVideoIDs = this.getSubMap(List.of("R2"));
        Set<Recipe> recipes = getSubset(List.of(2));

        List<Recipe> expectedRecipes = List.of(
                mockRecipeWithVideoLink(2, "R2", "videoID-C")
        );

        when(this.recipesProvider.getRecipes()).thenReturn(recipes);

        setExpectedMockScore(recipes);

        recipeVideoIDs.keySet().forEach(r ->
                when(this.YTService.getResults(this.BASE_QUERY + r))
                        .thenReturn(this.recipeVideoIDs.get(r)));

        List<Recipe> actualRecipes = chefExpress.recommend();

        assertEquals(actualRecipes, expectedRecipes);
    }

    @Test
    @Description("Receta sin video encontrado")
    public void ca3RecetaSinVideoEncontrado()
    {
        Map<String, List<String>> recipeVideoIDs = this.getSubMap(List.of("R3"));

        Set<Recipe> recipes = getSubset(List.of(3));

        List<Recipe> expectedRecipes = List.of(
                mockRecipe(3, "R3")
        );

        when(this.recipesProvider.getRecipes()).thenReturn(recipes);

        setExpectedMockScore(recipes);

        recipeVideoIDs.keySet().forEach(r ->
                when(this.YTService.getResults(this.BASE_QUERY + r))
                        .thenReturn(this.recipeVideoIDs.get(r)));

        List<Recipe> actualRecipes = chefExpress.recommend();

        assertEquals(actualRecipes, expectedRecipes);
    }

    @Test
    @Description("Búsqueda sin nombre de receta")
    public void ca4BusquedaSinNombreDeReceta()
    {
        Map<String, List<String>> recipeVideoIDs = this.getSubMap(List.of(""));

        Set<Recipe> recipes = getSubset(List.of(4));

        List<Recipe> expectedRecipes = List.of(
                mockRecipe(4, "")
        );

        when(this.recipesProvider.getRecipes()).thenReturn(recipes);

        setExpectedMockScore(recipes);

        recipeVideoIDs.keySet().forEach(r ->
                when(this.YTService.getResults(this.BASE_QUERY + r))
                        .thenReturn(this.recipeVideoIDs.get(r)));

        List<Recipe> actualRecipes = chefExpress.recommend();

        assertEquals(actualRecipes, expectedRecipes);
    }

    private Recipe mockRecipe(int id, String name) {
        Map<String, Float> ingredients = Map.of(
                "ingredient-1", 10.0f,
                "ingredient-2", 20.0f,
                "ingredient-3", 30.0f
        );
        return new Recipe(id, name, ingredients);
    }

    private Recipe mockRecipeWithVideoLink(int id, String name, String
            videoLink) {
        Map<String, Float> ingredients = Map.of(
                "ingredient-1", 10.0f,
                "ingredient-2", 20.0f,
                "ingredient-3", 30.0f
        );
        return new Recipe(id, name, ingredients, this.YT_PATH + videoLink);
    }

    private Map<String, List<String>> getSubMap(List<String> keys)
    {
        return keys.stream()
                .filter(recipeVideoIDs::containsKey)
                .collect(Collectors.toMap(
                        key -> key,
                        key -> recipeVideoIDs.get(key)
                ));
    }

    private Set<Recipe> getSubset(List<Integer> recipeIds)
    {
        return mockedRecipes.stream()
                .filter(recipe -> recipeIds.contains(recipe.getId()))
                .collect(Collectors.toSet());
    }

    private void setExpectedMockScore(Set<Recipe> recipes)
    {
        recipes.stream().forEach(recipe ->
                when(scorerMockDefault.score(recipe))
                        .thenReturn(expectedScores.get(recipe.getId()))
        );
    }

}


