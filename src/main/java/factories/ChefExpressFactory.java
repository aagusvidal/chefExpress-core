package factories;

import core.ChefExpress;
import entities.Recipe;
import interfaces.RecipeScorer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChefExpressFactory {
    private RecipeScorersFactory scorerFactory;
    private List<RecipeScorer> recipesScorers;

    public ChefExpressFactory() {
        this.scorerFactory = new RecipeScorersFactory();
    }

    public ChefExpress createChefExpress(String scorersPath, Set<Recipe> recipes) throws Exception {
        this.recipesScorers = this.scorerFactory.create(scorersPath);
        Map<String, RecipeScorer> scorersMap = this.scorersListToMap(this.recipesScorers);
        return new ChefExpress(recipes, recipesScorers.get(0), scorersMap);
    }

    private Map<String, RecipeScorer> scorersListToMap(List<RecipeScorer> recipesScorers) {
        Map<String, RecipeScorer> scorersMap = new HashMap<String, RecipeScorer>();
        for (RecipeScorer scorer : recipesScorers) {
            scorersMap.put(scorer.getName(), scorer);
        }
        return scorersMap;
    }
}
