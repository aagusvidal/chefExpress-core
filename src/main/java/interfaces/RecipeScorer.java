package interfaces;

import entities.Recipe;

public interface RecipeScorer {
    int score(Recipe recipe);
}
