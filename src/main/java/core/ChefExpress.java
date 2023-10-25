package core;

import entities.Recipe;
import interfaces.RecipeScorer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChefExpress implements PropertyChangeListener
{
    private PropertyChangeSupport support;
    protected Set<Recipe> recipes;
    protected RecipeScorer scorer;
    private List<Recipe> recipeRecommendations;
    private final int recipesLimit;


    public ChefExpress(Set<Recipe> recipes, RecipeScorer scorer)
    {
        this.recipes = recipes;
        this.scorer = scorer;
        this.recipesLimit = 2;
        this.recipeRecommendations = new ArrayList<>();
        this.support = new PropertyChangeSupport(this);
    }

    public List<Recipe> recommend()
    {
        List<Recipe> recipeRecommendations = this.recipes
                .stream()
                .filter(recipe -> scorer.score(recipe) != 0)
                .sorted((r1, r2) -> scorer.score(r2) - scorer.score(r1))
                .limit(this.recipesLimit)
                .collect(Collectors.toList());

        setRecommendations(recipeRecommendations);
        return recipeRecommendations;
    }

    public void attach(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void detach(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    private void setRecommendations(List<Recipe> recommendations)
    {
        support.firePropertyChange("Recipe recommendations", this.recipeRecommendations, recommendations);
        this.recipeRecommendations = recommendations;
    }

    public void setScorer(RecipeScorer scorer) {
        this.scorer = scorer;
    }

    public RecipeScorer getScorer() {
        return scorer;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        Set<Recipe> recipes = (Set<Recipe>) evt.getNewValue();
        this.setRecipes(recipes);
        this.setRecommendations(this.recommend());
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Set<Recipe> getRecipes(){return this.recipes;}
}