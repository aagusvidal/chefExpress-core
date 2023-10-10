package IT0;

import entities.Recipe;
import interfaces.RecipeScorer;
import jdk.jfr.Description;
import core.ChefExpress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserStory1 {
    private ChefExpress chefExpress;
    private List<Recipe> unsortedRecipes;
    private List<Recipe> sortedRecipes;
    private RecipeScorer scorerSaludable;

    @BeforeEach
    public void setUp() {
        unsortedRecipes = List.of(
                mockRecipe(0, "less-value-recipe"),
                mockRecipe(1, "medium-value-recipe"),
                mockRecipe(2, "most-value-recipe")
        );
        sortedRecipes = List.of(
                mockRecipe(2, "most-value-recipe"),
                mockRecipe(1, "medium-value-recipe")
        );
        scorerSaludable = mock(RecipeScorer.class);
        chefExpress = new ChefExpress(new HashSet<>(unsortedRecipes), scorerSaludable);
    }

    @Test
    @Description("Recomendación de múltiples recetas")
    public void ca1RecomendacionDeMultiplesRecetas() {
        final int recipesLimit = 2;
        final Map<Integer, Integer> expectedScores = Map.of(
                0, 0,
                1, 5,
                2, 10
        );
        expectedScores.entrySet().stream().forEach(entry ->
            when(scorerSaludable.score(this.unsortedRecipes.get(entry.getKey())))
                    .thenReturn(entry.getValue())
        );

        List<Recipe> recommendations = chefExpress.recommend();

        assertEquals(recipesLimit, recommendations.size());
        assertEquals(sortedRecipes, recommendations);
    }

    @Test
    @Description("Recomendación  de una receta")
    public void ca2RecomendacionDeUnaReceta() {
        final int expectedRecipesLimit = 1;
        final Map<Integer, Integer> expectedScores = Map.of(
                0, 0,
                1, 0,
                2, 10
        );
        expectedScores.entrySet().stream().forEach(entry ->
                when(scorerSaludable.score(this.unsortedRecipes.get(entry.getKey())))
                        .thenReturn(entry.getValue())
        );

        List<Recipe> recommendations = chefExpress.recommend();

        assertEquals(expectedRecipesLimit, recommendations.size());
        assertEquals(this.unsortedRecipes.get(2), recommendations.get(0));
    }

    @Test
    @Description("No se recomiendan recetas")
    public void ca3NoSeRecomiendanRecetas() {
        List<Recipe> recommendations = chefExpress.recommend();

        assertTrue(recommendations.isEmpty());
    }

    @Test
    @Description("Recomendación sin recetas")
    public void ca4RecomendacionSinRecetas() {
        chefExpress = new ChefExpress(new HashSet<>(), scorerSaludable);

        List<Recipe> recommendations = chefExpress.recommend();

        assertTrue(recommendations.isEmpty());
    }

    @Test
    @Description("Recetas sin puntaje")
    public void ca5RecetaSinPuntaje() {
        final Recipe recipe = mockRecipe(0, "not-scored-recipe");
        final Set<Recipe> unScoredRecipes = Set.of(recipe);
        chefExpress = new ChefExpress(unScoredRecipes, scorerSaludable);

        when(scorerSaludable.score(recipe)).thenReturn(0);

        final List<Recipe> recommendations = chefExpress.recommend();

        assertTrue(recommendations.isEmpty());
    }

    private Recipe mockRecipe(int id, String name) {
        Map<String, Float> ingredients = Map.of(
                "ingredient-1", 10.0f,
                "ingredient-2", 20.0f,
                "ingredient-3", 30.0f
        );
        return new Recipe(id, name, ingredients);
    }
}
