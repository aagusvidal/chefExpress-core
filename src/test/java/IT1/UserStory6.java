package IT1;

import core.ChefExpress;
import core.RecipesUpdater;
import entities.Recipe;
import finders.YTVideoLinkSearcher;
import interfaces.RecipeScorer;
import interfaces.RecipesFinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.*;


public class UserStory6
{
    private ChefExpress chefExpress;
    private RecipesUpdater recipesUpdater;
    private Set<Recipe> uniqueRecipes;
    private Set<Recipe> noRecipes;

    @BeforeEach
    public void setUp()
    {
        this.uniqueRecipes = Set.of(mockRecipe(1, "R1"));
        this.noRecipes = new HashSet<>();
        RecipeScorer scorerSaludable = mock(RecipeScorer.class);
        YTVideoLinkSearcher videoLinkSearcher = mock(YTVideoLinkSearcher.class);
        chefExpress = new ChefExpress(Set.of(mockRecipe(2, "R2")),scorerSaludable, videoLinkSearcher );
    }

    @Test
    public void ca1PropagacionDeNuevasRecetas()
    {
        recipesUpdater = initRecipesUpdater(chefExpress, uniqueRecipes);
        recipesUpdater.updateRecipes();
        assertEquals(chefExpress.getRecipes(), uniqueRecipes);
    }

    private RecipesUpdater initRecipesUpdater(ChefExpress chefExpress, Set<Recipe> recipes)
    {
        RecipesFinder finder = mock(RecipesFinder.class);
        when(finder.findRecipes("")).thenReturn(recipes);

        RecipesUpdater recipesUpdater = new RecipesUpdater(10L, finder, List.of(""));
        recipesUpdater.attach(chefExpress);

        return recipesUpdater;
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
