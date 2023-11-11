package finders;

import core.RecipesUpdater;
import entities.Recipe;

import java.util.Set;

public class RecipesProvider {

    private Set<Recipe> recipes;

    public RecipesProvider(Set<Recipe> recipes, RecipesUpdater recipesUpdater) {
        this.recipes = recipes;
    }

    public Set<Recipe> getRecipes(){
        return this.recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}
