package core;

import entities.Recipe;
import interfaces.RecipesFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RecipesUpdater implements PropertyChangeListener {
    private PropertyChangeSupport support;
    private Set<Recipe> recipes;
    private RecipesFactory recipesFinder;
    private List<String> paths;

    public RecipesUpdater(RecipesFactory recipesFinder, List<String> paths, Set<Recipe> recipes) {
        this.recipesFinder = recipesFinder;
        this.paths = paths;
        this.support = new PropertyChangeSupport(this);
        this.recipes = recipes;
    }

    public void attach(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void detach(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    private void setRecipes(Set<Recipe> recipes) {
        if(! recipes.isEmpty())
        {
            support.firePropertyChange("Recipes", this.recipes, recipes);
            this.recipes = recipes;
        }
    }

    private void executeUpdateScheduleTask(Long updateTime) {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(getTask(), 1, updateTime, TimeUnit.MINUTES);
    }

    private Runnable getTask() {
        return this::updateRecipes;
    }

    public void updateRecipes() {
        int number = Math.max(0, this.paths.size() - 1);
        int randomIndex = new Random().nextInt(number + 1 );
        setRecipes(this.recipesFinder.findRecipes(this.paths.get(randomIndex)));
    }

    public Set<Recipe> getRecipes(){
        return this.recipes;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
