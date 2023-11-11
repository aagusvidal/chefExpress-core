package finders;

import entities.Recipe;

import java.util.Set;

public class RecipesProvider {

    private Set<Recipe> recipes;

    public RecipesProvider(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Set<Recipe> getRecipes(){
        return this.recipes;
    }
}
