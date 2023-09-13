package parsers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RecipeParser {
    private ObjectMapper objectMapper;

    public RecipeParser() {
        objectMapper = new ObjectMapper();
    }

    public List<Recipe> parserRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        try {
            InputStream inputStream = RecipeParser.class.getResourceAsStream("/recipes.json");
            TypeReference<List<Recipe>> typeReference = new TypeReference<List<Recipe>>() {};
            recipes = objectMapper.readValue(inputStream, typeReference);

            return recipes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }

}
