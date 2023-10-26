package IT1;

import finders.VideoSearcher;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import services.YTService;

import java.util.Collections;
import java.util.List;
import java.util.Map;


public class UserStory5
{

    private YTService ytService;

    private VideoSearcher videoSearcher;

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

        this.ytService = Mockito.mock(YTService.class);
        mockYTServiceCall();
    }

    @Test
    @Description("Receta con múltiples videos")
    public void ca1RecetaConMultiplesVideos()
    {
        this.videoSearcher = new VideoSearcher(ytService, YT_PATH);

        Assertions.assertEquals(this.expectedLinks.get("R1"), this.videoSearcher.searchLink("R1"));
    }

    @Test
    @Description("Receta con un video")
    public void ca2RecetaConUnVideo()
    {
        this.videoSearcher = new VideoSearcher(ytService, YT_PATH);

        Assertions.assertEquals(this.expectedLinks.get("R2"), this.videoSearcher.searchLink("R2"));
    }

    @Test
    @Description("Receta sin video")
    public void ca3RecetaSinVideo()
    {
        this.videoSearcher = new VideoSearcher(ytService, YT_PATH);

        Assertions.assertEquals(this.expectedLinks.get("R3"), this.videoSearcher.searchLink("R3"));
    }

    @Test
    @Description("Busqueda sin nombre de receta")
    public void ca4BusquedaSinNombreDeReceta()
    {
        this.videoSearcher = new VideoSearcher(ytService, YT_PATH);

        Assertions.assertEquals("", this.videoSearcher.searchLink(""));
    }

    private void mockYTServiceCall()
    {
        recipeVideoIDs.keySet().forEach(r ->
                Mockito.when(this.ytService.getResults(BASE_QUERY + r))
                        .thenReturn(this.recipeVideoIDs.get(r)));
    }
}
