package core;

import entities.Recipe;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;

public class RecipesProvider implements PropertyChangeListener {

    private Set<Recipe> recipes;

    public RecipesProvider(RecipesUpdater recipesUpdater) {
       this.recipes = recipesUpdater.getRecipes();
        recipesUpdater.attach(this);
    }

    public Set<Recipe> getRecipes(){
        return this.recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Set<Recipe> recipes = (Set<Recipe>) evt.getNewValue();
        this.setRecipes(recipes);
    }
}
