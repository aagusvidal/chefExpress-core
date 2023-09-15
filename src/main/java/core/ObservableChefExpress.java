package core;

import entities.Recipe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ObservableChefExpress {
    private PropertyChangeSupport support;
    private ChefExpress adaptee;
    private List<Recipe> recommendations;

    public ObservableChefExpress(ChefExpress recommend)
    {
        this.adaptee = recommend;
        this.support = new PropertyChangeSupport(this);
        this.recommendations = new ArrayList<>();
    }

    public void recommend()
    {
        this.setRecommendations(this.adaptee.recommend());
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

}
