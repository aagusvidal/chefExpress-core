package core;

import entities.Recipe;
import services.YTService;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class VideoRecipesUpdater implements PropertyChangeListener
{
    private YTService ytService;
    private final String YT_BASE_PATH = "https://www.youtube.com/watch?v=";

    public VideoRecipesUpdater(YTService ytService, ChefExpress chefExpress)
    {
        this.ytService = ytService;
        chefExpress.attach(this);
    }

    public String searchLink(String query)
    {
        String queryPrefix = "receta de ";
        String videoID = this.ytService.getResults(queryPrefix + query).stream().findFirst().orElse(null);

        return videoID == null ? "" : YT_BASE_PATH + videoID;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        List<Recipe> recipes = (List<Recipe>) evt.getNewValue();
        recipes.stream().forEach(recipe -> recipe.setVideoLink(searchLink(recipe.getName())));
    }
}