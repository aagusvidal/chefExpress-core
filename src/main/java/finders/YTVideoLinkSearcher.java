package finders;

import services.YTService;

public class YTVideoLinkSearcher {

    private YTService ytService;
    private String YT_BASE_PATH;

    public YTVideoLinkSearcher(YTService ytService, String ytPath ) {
        this.ytService = ytService;
        this.YT_BASE_PATH = ytPath;
    }

    public String searchLink(String query) {
        String queryPrefix = "receta de ";
        String videoID =  this.ytService.getResults(queryPrefix + query).stream().findFirst().orElse(null);

        return videoID == null ? "" : YT_BASE_PATH + videoID ;

    }
}
