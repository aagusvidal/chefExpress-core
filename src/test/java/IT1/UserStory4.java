package IT1;

import core.ChefExpress;
import core.RecipesUpdater;
import entities.Recipe;
import finders.LocalRecipesFactory;
import finders.RecipesProvider;
import interfaces.RecipeScorer;
import interfaces.RecipesFactory;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserStory4
{
    private ChefExpress chefExpress;
    private RecipeScorer scorerSaludable;
    private RecipeScorer scorerCeliac;

    private RecipesProvider recipesProvider;
    private Map<String, RecipeScorer> scorersMockEmpty;
    @BeforeEach
    public void setUp()
    {
        scorerSaludable = mock(RecipeScorer.class);
        when(scorerSaludable.getName()).thenReturn("Saludables");
        scorerCeliac = mock(RecipeScorer.class);
        when(scorerCeliac.getName()).thenReturn("Sin TACC");
        scorersMockEmpty = new HashMap<String, RecipeScorer>();
        scorersMockEmpty.put(scorerSaludable.getName(), scorerSaludable);
        scorersMockEmpty.put(scorerCeliac.getName(), scorerCeliac);
        recipesProvider = this.createRecipeProvider(new ArrayList<Recipe>());
        chefExpress = new ChefExpress(recipesProvider, scorerSaludable, scorersMockEmpty);
    }

    @Test
    @Description("No se selecciona puntuador")
    public void ca1NoSeSeleccionaPuntuador()
    {
        assertThrows(IllegalArgumentException.class, () -> chefExpress.setScorer(null));
    }

    @Test
    @Description("Selección del puntuador por defecto")
    public void ca2SeleccionDeUnPuntuadorPorDefecto()
    {
        chefExpress.setScorer("Saludables");
        assertEquals(chefExpress.getScorerName(), scorerSaludable.getName());
    }

    @Test
    @Description("Selección de nuevo puntuador")
    public void ca3SeleccionDeUnNuevoPuntuador()
    {
        chefExpress.setScorer("Sin TACC");
        assertEquals(chefExpress.getScorerName(), scorerCeliac.getName());
    }

    @Test
    @Description("Selección de un puntuador no válido")
    public void ca4SeleccionDeUnPuntuadorNoValido()
    {
        chefExpress.setScorer("Banana");
        assertEquals(chefExpress.getScorerName(), scorerSaludable.getName());
    }

    private RecipesProvider createRecipeProvider(List<Recipe> recipesList) {
        RecipesFactory recipesLocalFinder = new LocalRecipesFactory();
        String recipesPath =  "";
        HashSet<Recipe> recipes = new HashSet<>(recipesList);
        RecipesUpdater recipesUpdater = new RecipesUpdater(recipesLocalFinder,  List.of(recipesPath.split(",")),  recipes);
        recipesProvider = new RecipesProvider(recipes, recipesUpdater);
        return recipesProvider;
    }
}
