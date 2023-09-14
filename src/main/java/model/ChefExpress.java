package model;

import entities.Recipe;
import interfaces.RecipeScorer;

import java.util.Set;

@SuppressWarnings("deprecation")
public class ChefExpress {
    protected Set<Recipe> recipes;
    protected RecipeScorer scorer;
    public ChefExpress() {

    }

    public ChefExpress(Set<Recipe> recipes, RecipeScorer scorer) {
        this.recipes = recipes;
        this.scorer = scorer;
    }
}