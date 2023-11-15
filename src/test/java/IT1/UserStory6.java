package IT1;

import core.ChefExpress;
import core.RecipesProvider;
import core.RecipesUpdater;
import entities.Recipe;
import factories.RecipesUpdaterFactory;
import interfaces.RecipeScorer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


public class UserStory6
{
    private static String basePath = "build/resources/test/";
    private ChefExpress chefExpress;

    private RecipesUpdater recipesUpdater;
    private Set<Recipe> defaultRecipes;
    private Set<Recipe> noRecipes;
    private Set<Recipe> modifiedRecipes;

    private RecipesProvider recipesProvider;

    @BeforeEach
    public void setUp()
    {
        this.defaultRecipes = new HashSet<>(List.of(mockRecipe(1, "R1")));
        this.noRecipes = new HashSet<>();

        RecipeScorer scorerSaludable = mock(RecipeScorer.class);
        modifiedRecipes = new HashSet<>(List.of(mockRecipe(2, "R2")));
        Map<String, RecipeScorer> scorers = new HashMap<String, RecipeScorer>();
        recipesUpdater =  this.createRecipesUpdater();
        recipesProvider = new RecipesProvider(recipesUpdater);
        chefExpress = new ChefExpress(recipesProvider,scorerSaludable, scorers);

    }

    @Test
    public void ca1PropagacionDeNuevasRecetas()
    {
        recipesUpdater.attach(recipesProvider);
        recipesUpdater.setNumberPath(0);
        recipesUpdater.updateRecipes();

       assertTrue(this.recipesEquals(chefExpress.getRecipes(), modifiedRecipes));
    }

    @Test
    public void ca2sinPropagacionDeRecetas()
    {
        recipesUpdater.attach(recipesProvider);
        recipesUpdater.setNumberPath(2);
        recipesUpdater.updateRecipes();

    assertTrue(this.recipesEquals(chefExpress.getRecipes(), noRecipes));
    }

    @Test
    public void ca3DesuscripcionDeObservadores()
    {
        recipesUpdater.detach(chefExpress);
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

    private RecipesUpdater createRecipesUpdater() {
        RecipesUpdaterFactory recipesUpdaterFactory = new RecipesUpdaterFactory();
        String pathConfig = basePath + "chefExpress.properties";
        RecipesUpdater recipesUpdater = recipesUpdaterFactory.createRecipesUpdater(pathConfig);
        Properties chefExpressProperties = recipesUpdaterFactory.loadProperties(pathConfig);
        List<String> pathsWithEmpty = new ArrayList<>(recipesUpdater.getPaths());
        pathsWithEmpty.add(chefExpressProperties.getProperty("RecipesEmptyPath"));
        recipesUpdater.setPaths(pathsWithEmpty);
        return recipesUpdater;
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
