package IT1;

import core.RecommendationLogger;
import entities.Recipe;
import entities.Recommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import providers.RecommendationProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


public class UserStory3
{
    @Mock
    private RecommendationProvider recommendationProvider;
    @InjectMocks
    private RecommendationLogger recommendationLogger;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void ca1SinRecetasMasRecomendadas()
    {
        when(this.recommendationProvider.getHistoricRecommendations()).thenReturn(Collections.emptyList());
        assertTrue(this.recommendationLogger.getTopRecipes().isEmpty());
    }

    @Test
    public void ca2MultiplesRecetasMasRecomendadas()
    {
        List<Integer> recipeIds = Arrays.asList(1, 2);
        List<Recommendation> uniqueRecommendations = mockRecommendations(mockRecipes(recipeIds));

        List<Recipe> expectedRecipes = mockRecipes(recipeIds);

        when(this.recommendationProvider.getHistoricRecommendations()).thenReturn(uniqueRecommendations);

        List<Recipe> actualRecipes = this.recommendationLogger.getTopRecipes();

        assertEquals(expectedRecipes, actualRecipes);
    }

    @Test
    public void ca3UnicaRecetaMasRecomendada()
    {
        List<Integer> recipeIds = Arrays.asList(1, 2, 3, 1);
        List<Recommendation> multipleRecommendations = mockRecommendations(mockRecipes(recipeIds));

        List<Integer> expectedRecipeIds = Arrays.asList(1);
        List<Recipe> expectedRecipes = mockRecipes(expectedRecipeIds);

        when(this.recommendationProvider.getHistoricRecommendations()).thenReturn(multipleRecommendations);

        List<Recipe> actualRecipes = this.recommendationLogger.getTopRecipes();

        assertEquals(expectedRecipes, actualRecipes);
    }

    private List<Recommendation> mockRecommendations(List<Recipe> recipes)
    {
        return recipes
                .stream()
                .map(recipe -> new Recommendation("linka",recipe)).collect(Collectors.toList());
    }

    private List<Recipe> mockRecipes(List<Integer> ids)
    {
        return ids.stream()
                .map(recipeId -> new Recipe(recipeId, "recipe " + recipeId, new HashMap<>()))
                .collect(Collectors.toList());
    }
}
