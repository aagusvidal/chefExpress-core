package finders;

import entities.Recipe;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Set;

public class RecipesUpdater implements PropertyChangeListener {

    private Set<Recipe> recipes;

    private PropertyChangeSupport support;

    public RecipesUpdater(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void updateRecipes(Set<Recipe> recipes){
        this.recipes = recipes;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    public void attach(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void detach(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
