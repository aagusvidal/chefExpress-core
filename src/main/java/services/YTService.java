package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import services.entities.SearchListResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

public class YTService {

    private final int SUCCESS_STATUS_CODE = 200;
    private String YT_API_PATH;
    private String YT_API_KEY;

    public YTService(String ytPath, String apiKey) {
        this.YT_API_PATH = ytPath;
        this.YT_API_KEY = apiKey;
    }

    public Optional<SearchListResponse> getResults(String query) {
        try {
            String encodedQuery = java.net.URLEncoder.encode(query, "UTF-8");
            URL url = new URL(YT_API_PATH + "?key=" + YT_API_KEY + "&q=" + encodedQuery);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == SUCCESS_STATUS_CODE) {
                InputStream inputStream = connection.getInputStream();
                byte[] bytes = inputStream.readAllBytes();
                ObjectMapper mapper = new ObjectMapper();
                return Optional.of(mapper.readValue(bytes, SearchListResponse.class));
            }
            return Optional.empty();
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}

