package IT0;

import finders.RecipeScorerFinder;
import interfaces.RecipeScorer;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;

public class US2
{
        private static String basePath = "build/resources/test/";

        /** @name = Ubicacion inexistente
         * @exception = FileNotFoundException **/
        @Test(expected = FileNotFoundException.class)
        public void CA1() throws Exception
        {
                new RecipeScorerFinder("NoExiste").find();
        }

        /** @name = Ubicacion invalida
         * @exception = IllegalArgumentException **/
        @Test(expected = IllegalArgumentException.class)
        public void CA2() throws Exception
        {
                new RecipeScorerFinder(basePath + "archivo.txt").find();
        }

//        /** @name = Carpeta vacia **/
//        @Test
//        public void CA3() throws Exception
//        {
//                RecipeScorerFinder finder = new RecipeScorerFinder(basePath + "carpetaVacia");
//                Map<String, RecipeScorer> scorers = finder.find();
//                assert (scorers.isEmpty());
//        }


        /** @name = No es criterio de puntuación **/
        @Test
        public void CA4() throws Exception
        {
                RecipeScorerFinder finder = new RecipeScorerFinder(basePath + "noEsCriterio");
                assert (finder.find().isEmpty());
        }

        /** @name = Puntuador simple **/
        @Test
        public void CA5() throws Exception
        {
                RecipeScorerFinder finder = new RecipeScorerFinder(basePath + "oneImpl");
                Map<String, RecipeScorer> scorers = finder.find();

                assert (scorers.size() == 1);
                assert (scorers.containsKey("RecetasSaludables"));
        }

        /** @name = Puntuador multiples **/
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
