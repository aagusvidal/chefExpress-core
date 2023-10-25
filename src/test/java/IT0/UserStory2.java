package IT0;

import factories.RecipeScorersFactory;
import interfaces.RecipeScorer;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserStory2 {
    private static String basePath = "build/resources/test/";

    @Description("Ubicacion inexistente")
    @Test
    public void ca1UbicacionInexistente() {
        assertThrows(FileNotFoundException.class, () -> new RecipeScorersFactory().create("NoExiste"));
    }

    @Description("Ubicacion invalida")
    @Test
    public void ca2UbicacionInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new RecipeScorersFactory().create(basePath + "archivo.txt"));
    }

    @Description("Carpeta vacia")
    @Test
    public void ca3CarpetaVacia() throws Exception {
        RecipeScorersFactory scorerFactory = new RecipeScorersFactory();
        assert (scorerFactory.create(basePath + "carpetaVacia").isEmpty());
    }

    @Description("No es criterio de puntuacion")
    @Test
    public void ca4NoEsCriterioDePuntuacion() throws Exception {
        RecipeScorersFactory scorerFactory = new RecipeScorersFactory();
        assert (scorerFactory.create(basePath + "noEsCriterio").isEmpty());
    }

    @Description("Puntuador simple")
    @Test
    public void ca5PuntuadorSimple() throws Exception {
        RecipeScorersFactory scorerFactory = new RecipeScorersFactory();

        List<RecipeScorer> scorers = scorerFactory.create(basePath + "oneImpl");

        assert (scorers.size() == 1);
        assert (scorers.get(0).getName().equals("Saludables"));
    }

    @Description("Puntuador multiples")
    @Test
    public void ca6PuntuadoresMultiples() throws Exception {
        RecipeScorersFactory scorersFactory = new RecipeScorersFactory();
        List<RecipeScorer> scorers = scorersFactory.create(basePath + "twoImpl");

        List<String> scorersNames = new ArrayList<>(
                List.of(scorers.get(0).getName(),
                        scorers.get(1).getName())
        );

        assert (scorers.size() == 2);
        assert (scorersNames.containsAll(List.of("Sin TACC", "Saludables")));
    }
}
