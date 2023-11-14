package core;

import entities.Recipe;
import interfaces.RecipeScorer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ChefExpress implements PropertyChangeListener
{
    private PropertyChangeSupport support;

    protected RecipesProvider recipesProvider;

    protected Map<String, RecipeScorer>possiblesScorers;
    protected  RecipeScorer scorer;
    private List<Recipe> recipeRecommendations;

    public ChefExpress(RecipesProvider recipesProvider, RecipeScorer scorer, Map<String, RecipeScorer> possiblesScorers)
    {
        this.recipesProvider = recipesProvider;
        this.scorer = scorer;
        this.recipeRecommendations = new ArrayList<>();
        this.possiblesScorers = possiblesScorers;
        this.support = new PropertyChangeSupport(this);
    }

    public List<Recipe> recommend()
    {
        List<Recipe> recipeRecommendations = this.recipesProvider.getRecipes()
                .stream()
                .filter(recipe -> scorer.score(recipe) != 0)
                .sorted((r1, r2) -> scorer.score(r2) - scorer.score(r1))
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

    public void setScorer(String scorerName)
    {
        if (scorerName == null)
            throw new IllegalArgumentException();
        else
           this.setImplementationScorer(scorerName);
    }

    private void setImplementationScorer(String scorerName) {
        if(possiblesScorers.containsKey(scorerName)){
            this.scorer = possiblesScorers.get(scorerName);
        }
    }

    public RecipeScorer getScorer() {
        return scorer;
    }

    public String getScorerName()
    {
        return scorer.getName();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        Set<Recipe> recipes = (Set<Recipe>) evt.getNewValue();
        this.setRecipes(recipes);
        this.setRecommendations(this.recommend());
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipesProvider.setRecipes(recipes);
    }

    public Set<Recipe> getRecipes(){return this.recipesProvider.getRecipes();}

    public String[] scorersNamesArray(){
        List<String> scorersList = new ArrayList<>(this.possiblesScorers.keySet());
        return scorersList.toArray(new String[0]);
    }
}