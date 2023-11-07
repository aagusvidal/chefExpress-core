package IT1;

import core.ChefExpress;
import interfaces.RecipeScorer;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserStory4
{
    private ChefExpress chefExpress;
    private RecipeScorer scorerSaludable;
    private RecipeScorer scorerCeliac;

    private Map<String, RecipeScorer> scorersMockEmpty;
    @BeforeEach
    public void setUp()
    {
        scorerSaludable = mock(RecipeScorer.class);
        when(scorerSaludable.getName()).thenReturn("Saludables");

        scorerCeliac = mock(RecipeScorer.class);
        when(scorerCeliac.getName()).thenReturn("Sin TACC");
        scorersMockEmpty = new HashMap<String, RecipeScorer>();
        chefExpress = new ChefExpress(new HashSet<>(Collections.emptyList()), scorerSaludable, scorersMockEmpty);
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
}
