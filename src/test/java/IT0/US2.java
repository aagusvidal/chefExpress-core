package IT0;

import finders.RecipeScorerFinder;
import interfaces.RecipeScorer;
import org.junit.Test;

import java.util.Set;

public class US2
{
        @Test
        public void CA5_Criterio_Simple()
        {
                RecipeScorerFinder finder = new RecipeScorerFinder("build/resources/test/oneImpl");
                Set<RecipeScorer> scorers = finder.find();
                assert (scorers.size() == 1);
        }

}
