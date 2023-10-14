package core;

import entities.Recipe;
import interfaces.RecipeScorer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChefExpress
{
    private PropertyChangeSupport support;
    protected Set<Recipe> recipes;
    protected RecipeScorer scorer;
    private List<Recipe> recommendations;
    private final int recipesLimit;

    public ChefExpress(Set<Recipe> recipes, RecipeScorer scorer)
    {
        this.recipes = recipes;
        this.scorer = scorer;
        this.recipesLimit = 2;
        this.recommendations = new ArrayList<>();

        this.support = new PropertyChangeSupport(this);
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

    public void attach(PropertyChangeListener pcl)
    {
        support.addPropertyChangeListener(pcl);
    }

    public void detach(PropertyChangeListener pcl)
    {
        support.removePropertyChangeListener(pcl);
    }

    private void setRecommendations(List<Recipe> recommendations)
    {
        support.firePropertyChange("Recommendations", this.recommendations, recommendations);
        this.recommendations = recommendations;
    }
    public void setScorer(RecipeScorer scorer) {
        this.scorer = scorer;
    }
}