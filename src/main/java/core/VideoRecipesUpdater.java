package core;

import entities.Recipe;
import services.YTService;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;

public class VideoRecipesUpdater implements PropertyChangeListener
{
    private YTService ytService;
    private String YT_BASE_PATH;

    private ChefExpress recommender;

    public VideoRecipesUpdater(YTService ytService, String ytPath, ChefExpress chefExpress)
    {
        this.ytService = ytService;
        this.YT_BASE_PATH = ytPath;
        chefExpress.attach(this);
        this.recommender = chefExpress;
    }

    public String searchLink(String query)
    {
        String queryPrefix = "receta de ";
        String videoID =  this.ytService.getResults(queryPrefix + query).stream().findFirst().orElse(null);

        return videoID == null ? "" : YT_BASE_PATH + videoID ;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<Recipe> recipes = (List<Recipe>) evt.getNewValue();
        recipes.stream().forEach(recipe -> recipe.setVideoLink(searchLink(recipe.getName())));
        this.recommender.setRecipes(new HashSet(recipes));
    }
}