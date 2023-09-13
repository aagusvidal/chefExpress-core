package model;

import entities.Recipe;
import interfaces.RecipeScorer;

import java.util.List;

@SuppressWarnings("deprecation")
public class ChefExpress {
    protected List<Recipe> recipes;
    protected RecipeScorer scorer;
    public ChefExpress() {

    }

    public ChefExpress(List<Recipe> recipes, RecipeScorer scorer) {
        this.recipes = recipes;
        this.scorer = scorer;
    }
}