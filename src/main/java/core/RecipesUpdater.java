package core;

import entities.Recipe;
import interfaces.RecipesFinder;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RecipesUpdater {
    private PropertyChangeSupport support;
    private Set<Recipe> recipes;
    private RecipesFinder recipesFinder;
    private List<String> paths;

    public RecipesUpdater(Long updateTime, RecipesFinder recipesFinder, List<String> paths) {
        this.recipesFinder = recipesFinder;
        this.paths = paths;
        this.support = new PropertyChangeSupport(this);
        this.recipes = new HashSet<>();
//        Set<Recipe> recipes = this.recipesFinder.findRecipes(this.paths.get(0));
//        System.out.println("Recipes!");
//        System.out.println(recipes);
        this.executeUpdateScheduleTask(updateTime);
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
        System.out.println("Thread");
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(getTask(), 0, updateTime, TimeUnit.MINUTES);
    }

    private Runnable getTask() {
        System.out.println("Execution!");
        return this::updateRecipes;
    }

    public void updateRecipes() {
        int number = Math.max(0, this.paths.size() - 1);
        int randomIndex = new Random().nextInt(number + 1 );
        Set<Recipe> recipes = this.recipesFinder.findRecipes(this.paths.get(randomIndex));
        System.out.println("Recipes!");
        System.out.println(recipes);
        setRecipes(recipes);
    }
}
