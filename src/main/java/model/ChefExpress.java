package model;

import entities.Recipe;
import interfaces.RecipeScorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<Recipe> recommend() {
        final int recipesLimit = 2;
        return this.recipes
                .stream()
                .filter(recipe -> scorer.score(recipe) != 0)
                .sorted((r1, r2) -> scorer.score(r2) - scorer.score(r1))
                .limit(recipesLimit)
                .collect(Collectors.toList());
    }
}