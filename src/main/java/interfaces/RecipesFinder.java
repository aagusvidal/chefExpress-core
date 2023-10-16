package interfaces;

import entities.Recipe;

import java.util.Set;

public interface RecipesFinder {
    Set<Recipe> findRecipes(String filePath);
}
