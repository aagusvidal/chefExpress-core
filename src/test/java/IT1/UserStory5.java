package IT1;

import core.ChefExpress;
import finders.RecipesVideoUpdater;
import interfaces.RecipeScorer;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import services.YTService;

import java.util.*;

import static org.mockito.Mockito.mock;


public class UserStory5
{

    private YTService YTService;

    private ChefExpress chefExpress;

    private RecipeScorer scorerMock;

    private RecipesVideoUpdater videoSearcher;

    private Map<String, List<String>> recipeVideoIDs;
    private Map<String, String> expectedLinks;

    private final String YT_PATH = "https://www.youtube.com/watch?v=";
    private final String BASE_QUERY = "receta de ";

    @BeforeEach
    public void setUp()
    {
        this.recipeVideoIDs = Map.of(
                "R1", List.of("videoID-A", "videoID-B"),
                "R2", List.of("videoID-C"),
                "R3", Collections.emptyList()
        );

        this.expectedLinks = Map.of(
                "R1", YT_PATH + "videoID-A",
                "R2", YT_PATH + "videoID-C",
                "R3", ""
        );

        scorerMock = mock(RecipeScorer.class);
        chefExpress = new ChefExpress(new HashSet<>(), scorerMock, new HashMap<>());
        this.YTService = Mockito.mock(YTService.class);
        mockYTServiceCall();
    }

    @Test
    @Description("Receta con múltiples videos")
    public void ca1RecetaConMultiplesVideos()
    {
        this.videoSearcher = new RecipesVideoUpdater(YTService, YT_PATH, chefExpress);

        Assertions.assertEquals(this.expectedLinks.get("R1"), this.videoSearcher.searchLink("R1"));
    }

    @Test
    @Description("Receta con un video")
    public void ca2RecetaConUnVideo()
    {
        this.videoSearcher = new RecipesVideoUpdater(YTService, YT_PATH, chefExpress);

        Assertions.assertEquals(this.expectedLinks.get("R2"), this.videoSearcher.searchLink("R2"));
    }

    @Test
    @Description("Receta sin video")
    public void ca3RecetaSinVideo()
    {
        this.videoSearcher = new RecipesVideoUpdater(YTService, YT_PATH, chefExpress);

        Assertions.assertEquals(this.expectedLinks.get("R3"), this.videoSearcher.searchLink("R3"));
    }

    @Test
    @Description("Busqueda sin nombre de receta")
    public void ca4BusquedaSinNombreDeReceta()
    {
        this.videoSearcher = new RecipesVideoUpdater(YTService, YT_PATH, chefExpress);

        Assertions.assertEquals("", this.videoSearcher.searchLink(""));
    }

    private void mockYTServiceCall()
    {
        recipeVideoIDs.keySet().forEach(r ->
                Mockito.when(this.YTService.getResults(BASE_QUERY + r))
                        .thenReturn(this.recipeVideoIDs.get(r)));
    }
}
