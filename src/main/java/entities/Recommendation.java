package entities;

import java.util.Objects;

public class Recommendation {

    private String Link;
    private Recipe recipe;


    public Recommendation(String link, Recipe recipe) {
        Link = link;
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(Link, that.Link) && Objects.equals(recipe, that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Link, recipe);
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "Link='" + Link + '\'' +
                ", recipe=" + recipe +
                '}';
    }
}
