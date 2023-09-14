import entities.Recipe;
import interfaces.RecipeScorer;
import jdk.jfr.Description;
import model.ChefExpress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserStory1 {
    private ChefExpress chefExpress;
    private List<Recipe> unsortedRecipes;
    private List<Recipe> sortedRecipes;
    private RecipeScorer scorerA;

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
        scorerA = mock(RecipeScorer.class);
        chefExpress = new ChefExpress(new HashSet<>(unsortedRecipes), scorerA);
    }

    @Test
    @Description("CA1")
    public void recommendationOfMultipleRecipes() {
        final int recipesLimit = 2;
        when(scorerA.score(this.unsortedRecipes.get(0))).thenReturn(0);
        when(scorerA.score(this.unsortedRecipes.get(1))).thenReturn(5);
        when(scorerA.score(this.unsortedRecipes.get(2))).thenReturn(10);

        List<Recipe> recommendations = chefExpress.recommend();

        assertEquals(recipesLimit, recommendations.size());
        assertEquals(sortedRecipes, recommendations);
    }

    @Test
    @Description("CA2")
    public void recommendationOfOneRecipe() {
        final int expectedRecipesLimit = 1;
        when(scorerA.score(this.unsortedRecipes.get(0))).thenReturn(0);
        when(scorerA.score(this.unsortedRecipes.get(1))).thenReturn(0);
        when(scorerA.score(this.unsortedRecipes.get(2))).thenReturn(10);

        List<Recipe> recommendations = chefExpress.recommend();

        assertEquals(expectedRecipesLimit, recommendations.size());
        assertEquals(this.unsortedRecipes.get(2), recommendations.get(0));
    }

    @Test
    @Description("CA3")
    public void recipesWithoutRecommendations() {
        List<Recipe> recommendations = chefExpress.recommend();
        when(scorerA.score(this.unsortedRecipes.get(0))).thenReturn(0);
        when(scorerA.score(this.unsortedRecipes.get(1))).thenReturn(0);
        when(scorerA.score(this.unsortedRecipes.get(2))).thenReturn(0);

        assertTrue(recommendations.isEmpty());
    }

    @Test
    @Description("CA4")
    public void recommendationWithoutRecipes() {
        chefExpress = new ChefExpress(new HashSet<>(), scorerA);

        List<Recipe> recommendations = chefExpress.recommend();

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
