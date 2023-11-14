package IT1;

import core.ChefExpress;
import core.RecipesUpdater;
import entities.Recipe;
import finders.LocalRecipesFactory;
import core.RecipesProvider;
import interfaces.RecipeScorer;
import interfaces.RecipesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.mock;


public class UserStory6
{
    private ChefExpress chefExpress;

    private RecipesUpdater recipesUpdater;
    private List<Recipe> uniqueRecipes;
    private List<Recipe> noRecipes;
    private List<Recipe> baseRecipes;

    private RecipesProvider recipesProvider;

    @BeforeEach
    public void setUp()
    {
        this.uniqueRecipes = List.of(mockRecipe(1, "R1"));
        this.noRecipes = new ArrayList<>();

        RecipeScorer scorerSaludable = mock(RecipeScorer.class);
        baseRecipes = List.of(mockRecipe(2, "R2"));
        recipesProvider = this.createRecipeProvider(baseRecipes);
        Map<String, RecipeScorer> scorers = new HashMap<String, RecipeScorer>();
        chefExpress = new ChefExpress(recipesProvider,scorerSaludable, scorers);
    }

    @Test
    public void ca1PropagacionDeNuevasRecetas()
    {
       /* recipesUpdater = initRecipesUpdater(chefExpress, uniqueRecipes);
        recipesUpdater.updateRecipes();

        assertEquals(chefExpress.getRecipes(), uniqueRecipes);*/
    }

    @Test
    public void ca2sinPropagacionDeRecetas()
    {
       /* recipesUpdater = initRecipesUpdater(chefExpress, noRecipes);
        recipesUpdater.updateRecipes();

        assertEquals(chefExpress.getRecipes(), baseRecipes);*/
    }

    @Test
    public void ca3DesuscripcionDeObservadores()
    {
       /* recipesUpdater = initRecipesUpdater(chefExpress, uniqueRecipes);
        recipesUpdater.detach(chefExpress);

        recipesUpdater.updateRecipes();

        assertEquals(chefExpress.getRecipes(), baseRecipes);*/
    }

    /*private RecipesUpdater initRecipesUpdater(ChefExpress chefExpress, Set<Recipe> recipes)
    {
       RecipesFactory finder = mock(RecipesFactory.class);
        when(finder.findRecipes("")).thenReturn(recipes);

        RecipesUpdater recipesUpdater = new RecipesUpdater(10L, finder, Set.of(""));
        recipesUpdater.attach(chefExpress);

        return recipesUpdater;
    }*/

    private Recipe mockRecipe(int id, String name)
    {
        Map<String, Float> ingredients = Map.of(
                "ingredient-1", 10.0f,
                "ingredient-2", 20.0f,
                "ingredient-3", 30.0f
        );
        return new Recipe(id, name, ingredients);
    }

    private RecipesProvider createRecipeProvider(List<Recipe> recipesList) {
        RecipesFactory recipesLocalFinder = new LocalRecipesFactory();
        String recipesPath =  "";
        HashSet<Recipe> recipes = new HashSet<>(recipesList);
        RecipesUpdater recipesUpdater = new RecipesUpdater(recipesLocalFinder,  List.of(recipesPath.split(",")),  recipes);
        recipesProvider = new RecipesProvider(recipesUpdater);
        return recipesProvider;
    }
}
