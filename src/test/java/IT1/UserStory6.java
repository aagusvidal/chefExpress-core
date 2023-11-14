package IT1;

import core.ChefExpress;
import core.RecipesProvider;
import core.RecipesUpdater;
import entities.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;


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
      /*  this.uniqueRecipes = List.of(mockRecipe(1, "R1"));
        this.noRecipes = new ArrayList<>();

        RecipeScorer scorerSaludable = mock(RecipeScorer.class);
        baseRecipes = List.of(mockRecipe(2, "R2"));
        Map<String, RecipeScorer> scorers = new HashMap<String, RecipeScorer>();
        recipesUpdater =  this.createRecipesUpdater();
        recipesProvider = new RecipesProvider(recipesUpdater);
        chefExpress = new ChefExpress(recipesProvider,scorerSaludable, scorers);*/

    }

    @Test
    public void ca1PropagacionDeNuevasRecetas()
    {
       /*recipesUpdater.attach(recipesProvider);
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

    private Recipe mockRecipe(int id, String name)
    {
        Map<String, Float> ingredients = Map.of(
                "ingredient-1", 10.0f,
                "ingredient-2", 20.0f,
                "ingredient-3", 30.0f
        );
        return new Recipe(id, name, ingredients);
    }

    /*private RecipesUpdater createRecipesUpdater() {
        RecipesUpdaterFactory recipesUpdaterFactory = new RecipesUpdaterFactory();
        RecipesUpdater recipesUpdater = recipesUpdaterFactory.createRecipesUpdater("");
        return recipesUpdater;
    }*/
}
