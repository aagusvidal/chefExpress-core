package IT0;

import finders.RecipeScorerFactory;
import interfaces.RecipeScorer;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserStory2
{
        private static String basePath = "build/resources/test/";

        @Description("Ubicacion inexistente")
        @Test
        public void ca1UbicacionInexistente() throws Exception
        {
                assertThrows(FileNotFoundException.class,
                        ()->
                        {
                                new RecipeScorerFactory().create("NoExiste");
                        });
        }

        @Description("Ubicacion invalida")
        @Test
        public void ca2UbicacionInvalida() throws Exception
        {

                assertThrows(IllegalArgumentException.class,
                        ()->
                        {
                                new RecipeScorerFactory().create(basePath + "archivo.txt");
                        });

        }

        @Description("Carpeta vacia")
        @Test
        public void ca3CarpetaVacia() throws Exception
        {
                RecipeScorerFactory scorerFactory = new RecipeScorerFactory();
                assert (scorerFactory.create(basePath + "carpetaVacia").isEmpty());
        }

        @Description("No es criterio de puntuacion")
        @Test
        public void ca4NoEsCriterioDePuntuacion() throws Exception
        {
                RecipeScorerFactory scorerFactory = new RecipeScorerFactory();
                assert (scorerFactory.create(basePath + "noEsCriterio").isEmpty());
        }

        @Description("Puntuador simple")
        @Test
        public void ca5PuntuadorSimple() throws Exception
        {
                RecipeScorerFactory scorerFactory = new RecipeScorerFactory();

                List<RecipeScorer> scorers = scorerFactory.create(basePath + "oneImpl");

                assert (scorers.size() == 1);
                // TODO: Add a getName() method in the RecipeScorer interface.
                assert (scorers.get(0).getClass().getName().equals("RecetasSaludables"));
        }

        @Description("Puntuador multiples")
        @Test
        public void ca6PuntuadoresMultiples() throws Exception
        {
                RecipeScorerFactory scorerFactory = new RecipeScorerFactory();
                List<RecipeScorer> scorers = scorerFactory.create(basePath + "twoImpl");

                assert (scorers.size() == 2);
                // TODO: Add a getName() method in the RecipeScorer interface.
                assert (scorers.get(0).getClass().getName().equals("RecetasSaludables"));
                assert (scorers.get(1).getClass().getName().equals("Diabeticos"));
        }
}
