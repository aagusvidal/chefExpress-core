package services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class YouTubeApiService {

    private final String GOOGLE_PATH = "https://www.googleapis.com/youtube/v3/search";
    private final String YOUTUBE_PATH = "https://www.youtube.com/watch";
    private final int SUCCESS_STATUS_CODE = 200;

    public Optional<SearchListResponse> getResults(String query) {
        //TODO: Sacar esto de una variable de ambiente
        String key = "AIzaSyD9INqvR6LkiJQvoaenu7Pan09k5gDx8fM";
        try {
            String encodedQuery = java.net.URLEncoder.encode(query, "UTF-8");
            URL url = new URL(GOOGLE_PATH + "?key=" + key + "&q=" + encodedQuery);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == SUCCESS_STATUS_CODE) {
                InputStream inputStream = connection.getInputStream();
                byte[] bytes = inputStream.readAllBytes();
                ObjectMapper mapper = new ObjectMapper();
                return Optional.of(mapper.readValue(bytes, SearchListResponse.class));
            }
            return Optional.empty();
        }  catch (IOException e) {
            return Optional.empty();
        }
    }

    public String getRecipeVideoLink(String query) {
        // TODO: Ver donde pasamos esta lógica
        YouTubeApiService s = new YouTubeApiService();

        String queryPrefix = "receta de ";
        String videoID =  s.getResults(queryPrefix + query)
                .orElseThrow(RuntimeException::new)
                .getItems()
                .stream()
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getId()
                .getVideoId();
        return YOUTUBE_PATH + "?v=" + videoID;
    }

    public static void main(String[] args) {
        YouTubeApiService s = new YouTubeApiService();
        System.out.println(s.getRecipeVideoLink("asado"));
    }
}

