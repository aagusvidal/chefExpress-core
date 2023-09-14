package IT0;

import finders.RecipeScorerFinder;
import interfaces.RecipeScorer;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserStory2
{
        private static String basePath = "build/resources/test/";

        @Description("Ubicacion inexistente")
        @Test
        public void CA1() throws Exception
        {
                assertThrows(FileNotFoundException.class,
                        ()->
                        {
                                new RecipeScorerFinder("NoExiste").find();
                        });
        }

        @Description("Ubicacion invalida")
        @Test
        public void CA2() throws Exception
        {

                assertThrows(IllegalArgumentException.class,
                        ()->
                        {
                                new RecipeScorerFinder(basePath + "archivo.txt").find();
                        });

        }

        @Description("Carpeta vacia")
        @Test
        public void CA3() throws Exception
        {
                RecipeScorerFinder finder = new RecipeScorerFinder(basePath + "carpetaVacia");
                Map<String, RecipeScorer> scorers = finder.find();
                assert (scorers.isEmpty());
        }

        @Description("No es criterio de puntuacion")
        @Test
        public void CA4() throws Exception
        {
                RecipeScorerFinder finder = new RecipeScorerFinder(basePath + "noEsCriterio");
                assert (finder.find().isEmpty());
        }

        @Description("Puntuador simple")
        @Test
        public void CA5() throws Exception
        {
                RecipeScorerFinder finder = new RecipeScorerFinder(basePath + "oneImpl");
                Map<String, RecipeScorer> scorers = finder.find();

                assert (scorers.size() == 1);
                assert (scorers.containsKey("RecetasSaludables"));
        }

        @Description("Puntuador multiples")
        @Test
        public void CA6() throws Exception
        {
                RecipeScorerFinder finder = new RecipeScorerFinder(basePath + "twoImpl");
                Map<String, RecipeScorer> scorers = finder.find();

                assert (scorers.size() == 2);
                assert (scorers.containsKey("RecetasSaludables"));
                assert (scorers.containsKey("Diabeticos"));
        }
}
