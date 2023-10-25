package factories;

import finders.RecipeScorerFinder;
import interfaces.RecipeScorer;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RecipeScorersFactory
{
    public List<RecipeScorer> create(String directory) throws FileNotFoundException
    {
        Set<Class<?>> classes = new RecipeScorerFinder().find(directory);
        List<RecipeScorer> scorers = new ArrayList<>();

        for(Class<?> aClass : classes)
        {
            try
            {
                if (RecipeScorer.class.isAssignableFrom(aClass))
                    scorers.add((RecipeScorer) aClass.getDeclaredConstructor().newInstance() );
            }
            catch ( Exception e) { throw new RuntimeException(e); }
        }

        return scorers;
    }
}
