package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import services.entities.SearchYTResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class YTService {

    private final int SUCCESS_STATUS_CODE = 200;
    private String YT_API_PATH;
    private String YT_API_KEY;

    public YTService(String ytPath, String apiKey) {
        this.YT_API_PATH = ytPath;
        this.YT_API_KEY = apiKey;
    }

    public List<String> getResults(String query) {
        try {
            String encodedQuery = java.net.URLEncoder.encode(query, "UTF-8");
            URL url = new URL(YT_API_PATH + "?key=" + YT_API_KEY + "&q=" + encodedQuery);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == SUCCESS_STATUS_CODE) {
                InputStream inputStream = connection.getInputStream();
                byte[] bytes = inputStream.readAllBytes();
                ObjectMapper mapper = new ObjectMapper();
                SearchYTResponse search = mapper.readValue(bytes, SearchYTResponse.class);
                return search.getItems().stream().map(i -> i.getId().getVideoId()).collect(Collectors.toList());
            }
            return Collections.emptyList();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}

