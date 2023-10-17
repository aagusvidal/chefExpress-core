package IT1;

import finders.YTVideoLinkSearcher;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.*;

import static org.mockito.MockitoAnnotations.*;

import services.YTService;

import javax.annotation.meta.When;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class UserStory5 {

    private YTService ytService;

    private YTVideoLinkSearcher ytVideoLinkSearcher;

    private Map<String, List<String>> recipeVideoIDs;
    private Map<String, String> expectedLinks;

    private final String YT_PATH = "https://www.youtube.com/watch?v=";
    ;
    private final String BASE_QUERY = "receta de ";

    @BeforeEach
    public void setUp() {

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
    }

    @Test
    @Description("Receta con múltiples videos")
    public void ca1RecetaConMultiplesVideos() {
        this.ytService = Mockito.mock(YTService.class);
        this.ytVideoLinkSearcher = new YTVideoLinkSearcher(ytService, YT_PATH);

        mockYTServiceCall();

        Assertions.assertEquals(this.expectedLinks.get("R1"), this.ytVideoLinkSearcher.searchLink("R1"));
    }

    @Test
    @Description("Receta con un video")
    public void ca2RecetaConUnVideo() {
        this.ytService = Mockito.mock(YTService.class);
        this.ytVideoLinkSearcher = new YTVideoLinkSearcher(ytService, YT_PATH);

        mockYTServiceCall();

        Assertions.assertEquals(this.expectedLinks.get("R2"), this.ytVideoLinkSearcher.searchLink("R2"));
    }

    @Test
    @Description("Receta sin video")
    public void ca3RecetaSinVideo() {
        this.ytService = Mockito.mock(YTService.class);
        this.ytVideoLinkSearcher = new YTVideoLinkSearcher(ytService, YT_PATH);

        mockYTServiceCall();

        Assertions.assertEquals(this.expectedLinks.get("R3"), this.ytVideoLinkSearcher.searchLink("R3"));
    }

    @Test
    @Description("Busqueda sin nombre de receta")
    public void ca4BusquedaSinNombreDeReceta() {
        this.ytService = Mockito.mock(YTService.class);
        this.ytVideoLinkSearcher = new YTVideoLinkSearcher(ytService, YT_PATH);

        mockYTServiceCall();

        Assertions.assertEquals("", this.ytVideoLinkSearcher.searchLink(""));
    }

    private void mockYTServiceCall() {
        recipeVideoIDs.keySet().forEach(r ->
                Mockito.when(this.ytService.getResults(BASE_QUERY + r))
                        .thenReturn(this.recipeVideoIDs.get(r)));
    }
}
