package IT0;

import finders.RecipeScorerFinder;
import interfaces.RecipeScorer;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

public class US2
{
        @Test(expected = FileNotFoundException.class)
        public void CA1() throws Exception {
                RecipeScorerFinder finder = new RecipeScorerFinder("NoExiste");
                Map<String, RecipeScorer> scorers = finder.find();
        }

        @Test(expected = IllegalArgumentException.class)
        public void CA2() throws Exception
        {
                RecipeScorerFinder finder = new RecipeScorerFinder("build/resources/test/archivo.txt");
                Map<String, RecipeScorer> scorers = finder.find();
        }

        @Test
        public void CA3() throws Exception
        {
                RecipeScorerFinder finder = new RecipeScorerFinder("build/resources/test/carpetaVacia");
                Map<String, RecipeScorer> scorers = finder.find();
                assert (scorers.isEmpty());
        }

        @Test
        public void CA4() throws Exception
        {
                RecipeScorerFinder finder = new RecipeScorerFinder("build/resources/test/noEsCriterio");
                Map<String, RecipeScorer> scorers = finder.find();
                assert (scorers.isEmpty());
        }

        @Test
        public void CA5() throws Exception
        {
                RecipeScorerFinder finder = new RecipeScorerFinder("build/resources/test/oneImpl");
                Map<String, RecipeScorer> scorers = finder.find();

                assert (scorers.size() == 1);
                assert (scorers.containsKey("RecetasSaludables"));
        }

        @Test
        public void CA6() throws Exception
        {
                RecipeScorerFinder finder = new RecipeScorerFinder("build/resources/test/twoImpl");
                Map<String, RecipeScorer> scorers = finder.find();

                assert (scorers.size() == 2);
                assert (scorers.containsKey("RecetasSaludables"));
                assert (scorers.containsKey("Diabeticos"));
        }
}
