package model;

import entities.Recipe;
import interfaces.RecipeScorer;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChefExpress
{
    protected Set<Recipe> recipes;
    protected RecipeScorer scorer;
    private final int recipesLimit;

    public ChefExpress(Set<Recipe> recipes, RecipeScorer scorer)
    {
        this.recipes = recipes;
        this.scorer = scorer;
        this.recipesLimit = 2;
    }

    public List<Recipe> recommend()
    {
        return this.recipes
                .stream()
                .filter(recipe -> scorer.score(recipe) != 0)
                .sorted((r1, r2) -> scorer.score(r2) - scorer.score(r1))
                .limit(this.recipesLimit)
                .collect(Collectors.toList());
    }
}