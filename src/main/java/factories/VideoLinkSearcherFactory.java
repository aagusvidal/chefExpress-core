package factories;

import finders.YTVideoLinkSearcher;
import services.YTService;

import java.util.Properties;

public class VideoLinkSearcherFactory {

    public YTVideoLinkSearcher create(Properties properties) {
        String ytApiPath = properties.getProperty("YTApiPath");
        String apiKey = properties.getProperty("YTApiKey");
        String ytBasePath = properties.getProperty("YTBasePath");

        YTService ytService = new YTService(ytApiPath, apiKey);

        return new YTVideoLinkSearcher(ytService, ytBasePath);
    }
}
