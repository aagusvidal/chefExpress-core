package factories;

import finders.VideoSearcher;
import services.YTService;

import java.util.Properties;

public class VideoSearcherFactory
{
    public VideoSearcher create(Properties properties)
    {
        String ytApiPath = properties.getProperty("YTApiPath");
        String apiKey = properties.getProperty("YTApiKey");
        String ytBasePath = properties.getProperty("YTBasePath");

        YTService ytService = new YTService(ytApiPath, apiKey);

        return new VideoSearcher(ytService, ytBasePath);
    }
}
