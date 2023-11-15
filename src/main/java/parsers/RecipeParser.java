package parsers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Recipe;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class RecipeParser {
    private ObjectMapper objectMapper;

    public RecipeParser()
    {
        objectMapper = new ObjectMapper();
    }

    public Set<Recipe> parseRecipes(String filePath)
    {
        Set<Recipe> recipes = new HashSet<Recipe>();
        try
        {
            InputStream inputStream = RecipeParser.class.getResourceAsStream(filePath);
            TypeReference<Set<Recipe>> typeReference = new TypeReference<Set<Recipe>>() {};
            if(inputStream != null) {
                recipes = objectMapper.readValue(inputStream, typeReference);
            }
            return recipes;
        } catch (IOException e) { e.printStackTrace(); }

        return recipes;
    }

}
