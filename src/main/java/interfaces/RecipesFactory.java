package interfaces;

import entities.Recipe;

import java.util.Set;

public interface RecipesFactory {
    Set<Recipe> findRecipes(String filePath);
}
