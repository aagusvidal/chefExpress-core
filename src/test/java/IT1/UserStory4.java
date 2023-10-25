package IT1;

import core.ChefExpress;
import entities.Recipe;
import finders.VideoSearcher;
import interfaces.RecipeScorer;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class UserStory4 {
    private ChefExpress chefExpress;
    private List<Recipe> unsortedRecipes;
    private RecipeScorer scorerSaludable;
    private VideoSearcher videoLinkSearcher;

    @BeforeEach
    public void setUp() {
        unsortedRecipes = List.of(
                mockRecipe(0, "less-value-recipe"),
                mockRecipe(1, "medium-value-recipe"),
                mockRecipe(2, "most-value-recipe")
        );
        scorerSaludable = mock(RecipeScorer.class);
        chefExpress = new ChefExpress(new HashSet<>(unsortedRecipes), scorerSaludable);
    }

    @Test
    @Description("No se selecciona puntuador")
    public void ca1NoSeSeleccionaPuntuador() {
        chefExpress.setScorer(null);
        assertNull(chefExpress.getScorer());
    }

    @Test
    @Description("Selección del puntuador por defecto")
    public void ca2SeleccionDeUnPuntuadorPorDefecto() {
        chefExpress.setScorer(scorerSaludable);
        assertEquals(chefExpress.getScorer(), scorerSaludable);
    }

    @Test
    @Description("Selección de nuevo puntuador")
    public void ca3SeleccionDeUnNuevoPuntuador() {
        RecipeScorer scorerCeliac = mock(RecipeScorer.class);
        chefExpress.setScorer(scorerCeliac);
        assertEquals(chefExpress.getScorer(), scorerCeliac);
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
