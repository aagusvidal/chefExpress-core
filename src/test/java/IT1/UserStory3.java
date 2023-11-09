package IT1;

import core.ChefExpress;
import core.HistoricalRecipesCounter;
import entities.Recipe;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.openMocks;


public class UserStory3 {

    private HistoricalRecipesCounter historicalRecipesCounter;

    private ChefExpress chefExpress;
    @BeforeEach
    public void setUp() {
        openMocks(this);
        chefExpress = mock(ChefExpress.class);
        historicalRecipesCounter =  new HistoricalRecipesCounter();
        chefExpress.attach(historicalRecipesCounter);
    }

   /* @Test
    public void ca1SinRecetasMasRecomendadas() {
        when(this.chefExpress.recommend()).thenReturn(Collections.emptyList());
        this.chefExpress.recommend();
        assertTrue(historicalRecipesCounter.getHistoricRecipes().isEmpty());
    }*/

   /* @Test
    public void ca2MultiplesRecetasMasRecomendadas() {
       /* List<Integer> recipeIds = Arrays.asList(1, 2);
        when(this.chefExpress.recommend()).thenReturn(mockRecipes(recipeIds));
        this.chefExpress.recommend();
        Map<Recipe, Integer> actualRecipes = mockRecommendations(mockRecipes(recipeIds));
        assertTrue(this.areMapsEqual(historicalRecipesCounter.getHistoricRecipes(), actualRecipes));

    } */
    /*@Test
    public void ca3UnicaRecetaMasRecomendada() {
       List<Integer> recipeIds = Arrays.asList(1, 2, 3, 1);
        Map<Recipe, Integer> multipleRecommendations = mockRecommendations(mockRecipes(recipeIds));

        List<Integer> expectedRecipeIds = Arrays.asList(1);
        List<Recipe> expectedRecipes = mockRecipes(expectedRecipeIds);

        when(this.recommendationProvider.getHistoricRecommendations()).thenReturn(multipleRecommendations);

        List<Recipe> actualRecipes = this.recommendationLogger.getTopRecipes();

        assertEquals(expectedRecipes, actualRecipes);
    }*/

    private Map<Recipe, Integer> mockRecommendations(List<Recipe> recipes) {
        Map<Recipe, Integer> recommendationMap = new HashMap<>();

        for (Recipe recipe : recipes) {
            int quantity = 1;
            recommendationMap.put(recipe, quantity);
        }
        return recommendationMap;
    }

    private List<Recipe> mockRecipes(List<Integer> ids) {
        return ids.stream()
                .map(recipeId -> new Recipe(recipeId, "recipe " + recipeId, new HashMap<>()))
                .collect(Collectors.toList());
    }

    private boolean areMapsEqual(Map<Recipe, Integer> map1, Map<Recipe, Integer> map2) {
        if (map1.size() != map2.size()) {
            return false;
        }
        for (Map.Entry<Recipe, Integer> entry : map1.entrySet()) {
            Recipe key = entry.getKey();
            Integer value1 = entry.getValue();
            Integer value2 = map2.get(key);
            if (value2 == null || !Objects.equals(value1, value2)) {
                return false;
            }
        }
        return true;
    }
}
