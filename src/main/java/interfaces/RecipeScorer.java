package interfaces;

import entities.Recipe;

public interface RecipeScorer
{
    String getName();
    int score(Recipe recipe);
}
