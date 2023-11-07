package core;

import entities.Recipe;
import entities.Recommendation;
import finders.VideoSearcher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.stream.Collectors;

public class VideoRecipeRecommender implements PropertyChangeListener
{
    private ChefExpress chefExpress;
    private VideoSearcher videoSearcher;
    private List<Recommendation> recommendations;
    private PropertyChangeSupport support;


    public VideoRecipeRecommender(VideoSearcher videoSearcher, ChefExpress chefExpress)
    {
        this.chefExpress = chefExpress;
        this.chefExpress.attach(this);

        this.videoSearcher = videoSearcher;
        this.support = new PropertyChangeSupport(this);
    }

    public List<Recommendation> recommend()
    {
        List<Recipe> recipeRecommendations = chefExpress.recommend();
        List<Recommendation> recommendations = createRecommendation(recipeRecommendations);

        this.setRecommendations(recommendations);

        return recommendations;
    }

    private List<Recommendation> createRecommendation(List<Recipe> recipeRecommendations)
    {
        return recipeRecommendations.stream()
                .map(recipe -> new Recommendation(this.videoSearcher.searchLink(recipe.getName()), recipe))
                .collect(Collectors.toList());
    }

    public void attach(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void detach(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    private void setRecommendations(List<Recommendation> recommendations)
    {
        support.firePropertyChange("Recommendations", this.recommendations, recommendations);
        this.recommendations = recommendations;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        List<Recipe> recipes = (List<Recipe>) evt.getNewValue();
        List<Recommendation> recommendations = createRecommendation(recipes);

        this.setRecommendations(recommendations);
    }
}
