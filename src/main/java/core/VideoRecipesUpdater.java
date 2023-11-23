package core;

import entities.Recipe;
import services.YTService;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class VideoRecipesUpdater implements PropertyChangeListener
{
    private YTService ytService;
    private String ytBasePath = "https://www.youtube.com/watch?v=";
    private List<String> lastLinksAdded;
    public VideoRecipesUpdater(YTService ytService, ChefExpress chefExpress)
    {
        this.ytService = ytService;
        chefExpress.attach(this);
        lastLinksAdded = new ArrayList<>();
    }

    public String searchLink(String query)
    {
        String queryPrefix = "receta de ";
        String videoID = this.ytService.getResults(queryPrefix + query).stream().findFirst().orElse(null);

        return videoID == null ? "" : ytBasePath + videoID;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        lastLinksAdded.clear();
        List<Recipe> recipes = (List<Recipe>) evt.getNewValue();
        recipes.stream().forEach(recipe -> recipe.setVideoLink(searchLink(recipe.getName())));
        recipes.stream().forEach(recipe -> lastLinksAdded.add(recipe.getVideoLink()));
    }

    public List<String> getLinksAdded()
    {
        return this.lastLinksAdded;
    }

    public void setYTBasePath(String YT_BASE_PATH)
    {
        ytBasePath = YT_BASE_PATH;
    }
}