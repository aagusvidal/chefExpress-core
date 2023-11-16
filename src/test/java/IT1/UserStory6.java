package IT1;

import core.ChefExpress;
import core.RecipesProvider;
import core.RecipesUpdater;
import entities.Recipe;
import interfaces.RecipeScorer;
import interfaces.RecipesFactory;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserStory6
{
    private static String basePath = "build/resources/test/";
    private ChefExpress chefExpress;
    private RecipesUpdater recipesUpdater;
    private Set<Recipe> defaultRecipes;
    private Set<Recipe> noRecipes;
    private Set<Recipe> modifiedRecipes;
    private RecipesFactory recipesFinder;
    private RecipesProvider recipesProvider;

    @BeforeEach
    public void setUp()
    {
        this.defaultRecipes = new HashSet<>(List.of(mockRecipe(1, "R1")));
        this.modifiedRecipes = new HashSet<>(List.of(mockRecipe(2, "R2")));
        this.noRecipes = new HashSet<>();

        recipesFinder = mock(RecipesFactory.class);
        recipesUpdater = new RecipesUpdater(recipesFinder, List.of(""), defaultRecipes );

        recipesProvider = new RecipesProvider(recipesUpdater);

        chefExpress = new ChefExpress(recipesProvider, mock(RecipeScorer.class), new HashMap<String, RecipeScorer>());
    }

    @Test
    @Description("Propagación de nuevas recetas")
    public void ca1PropagacionDeNuevasRecetas()
    {
        when(recipesFinder.findRecipes("")).thenReturn(this.modifiedRecipes);
        recipesUpdater.updateRecipes();

        assertTrue(this.recipesEquals(chefExpress.getRecipes(), modifiedRecipes));
    }

    @Test
    @Description("Propagación sin recetas")
    public void ca2PropagacionSinRecetas()
    {
        when(recipesFinder.findRecipes("")).thenReturn(noRecipes);
        recipesUpdater.updateRecipes();

        assertTrue(this.recipesEquals(chefExpress.getRecipes(), noRecipes));
    }

    @Test
    @Description("Desuscripción de observadores")
    public void ca3DesuscripcionDeObservadores()
    {
        recipesUpdater.detach(this.recipesProvider);

        when(recipesFinder.findRecipes("")).thenReturn(this.modifiedRecipes);
        recipesUpdater.updateRecipes();

        assertTrue(this.recipesEquals(chefExpress.getRecipes(), defaultRecipes));
    }

    private Recipe mockRecipe(int id, String name)
    {
        Map<String, Float> ingredients = Map.of(
                "ingredient-1", 1.0f,
                "ingredient-2", 1.0f

        );
        return new Recipe(id, name, ingredients);
    }

    private boolean recipesEquals(Set<Recipe> recipes, Set<Recipe> baseRecipes) {
        if(recipes.size() != baseRecipes.size()){
            return false;
        }
        List<Recipe> recipeList = new ArrayList<>(recipes);
        List<Recipe> baseRecipeList = new ArrayList<>(baseRecipes);

        Collections.sort(recipeList, Comparator.comparingInt(Recipe::getId));
        Collections.sort(baseRecipeList, Comparator.comparingInt(Recipe::getId));

        for (int i = 0; i < recipeList.size(); i++) {
            Recipe recipe = recipeList.get(i);
            Recipe baseRecipe = baseRecipeList.get(i);

            if (recipe.getId() != baseRecipe.getId() || !recipe.getName().equals(baseRecipe.getName())) {
                return false;
            }
           if(!this.ingredientsEquals(recipe.getIngredients(), baseRecipe.getIngredients())){
               return false;
           }
        }
        return true;
    }

    private static boolean ingredientsEquals(Map<String, Float> map1, Map<String, Float> map2) {
        if (map1.size() != map2.size()) {
            return false;
        }
        for (String key : map1.keySet()) {
            if (!map2.containsKey(key)) {
                return false;
            }
            Float value1 = map1.get(key);
            Float value2 = map2.get(key);

            if (!Objects.equals(value1, value2)) {
                return false;
            }
        }
        return true;
    }
}
