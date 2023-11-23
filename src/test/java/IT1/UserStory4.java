package IT1;

import core.ChefExpress;
import core.RecipesUpdater;
import entities.Recipe;
import finders.LocalRecipesFactory;
import core.RecipesProvider;
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
        chefExpress.setScorer("");
        assert chefExpress.getScorerName().equals(scorerSaludable.getName());
    }

    @Test
    @Description("No se selecciona puntuador")
    public void ca2SeleccionDePuntuadorInvalido()
    {
        assertThrows(IllegalArgumentException.class, () -> chefExpress.setScorer(null));
    }

    @Test
    @Description("Seleccion del puntuador actual")
    public void ca3SeleccionDelPuntuadorActual()
    {
        chefExpress.setScorer("Saludables");
        assertEquals(chefExpress.getScorerName(), scorerSaludable.getName());
    }

    @Test
    @Description("Seleccion de nuevo puntuador")
    public void ca4SeleccionDeUnNuevoPuntuador()
    {
        chefExpress.setScorer("Sin TACC");
        assertEquals(chefExpress.getScorerName(), scorerCeliac.getName());
    }

    @Test
    @Description("Seleccion de un puntuador no valido")
    public void ca5SeleccionDeUnPuntuadorNoValido()
    {
        chefExpress.setScorer("Invalido");
        assertEquals(chefExpress.getScorerName(), scorerSaludable.getName());
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
