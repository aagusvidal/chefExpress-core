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

    private List<Recommendation> uniqueRecommendations;
    private List<Recipe> uniqueRecipeRecommendations;
    private List<Recommendation> multipleRecommendations;
    private List<Recipe> multipleRecipeRecommendations;

    UserStory3()
    {
        List<Integer> uniqueRecipeIds = Arrays.asList(1, 2);
        this.uniqueRecommendations = this.createRecommendations(uniqueRecipeIds);
        this.uniqueRecipeRecommendations = mockRecipes(uniqueRecipeIds);

        this.multipleRecommendations = this.createRecommendations(Arrays.asList(1, 2, 3, 1));
        this.multipleRecipeRecommendations =  mockRecipes(Arrays.asList(1));
    }


    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void ca1SinRecetasMasRecomendadas()
    {
        this.initRecommendationProvider(Collections.emptyList());

        assertTrue(this.recommendationLogger.getTopRecipes().isEmpty());
    }

    @Test
    public void ca2MultiplesRecetasMasRecomendadas()
    {
        this.initRecommendationProvider(uniqueRecommendations);

        List<Recipe> actualRecipes = this.recommendationLogger.getTopRecipes();

        assertEquals(this.uniqueRecipeRecommendations, actualRecipes);
    }

    @Test
    public void ca3UnicaRecetaMasRecomendada()
    {
        this.initRecommendationProvider(multipleRecommendations);

        assertEquals(multipleRecipeRecommendations, this.recommendationLogger.getTopRecipes());
    }


    private void initRecommendationProvider(List<Recommendation> recommendations)
    {
        when(this.recommendationProvider.getHistoricRecommendations()).thenReturn(recommendations);
    }

    private List<Recommendation> createRecommendations(List<Integer> recipeIds)
    {
        return multipleRecommendations = mockRecommendations(mockRecipes(recipeIds));
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
