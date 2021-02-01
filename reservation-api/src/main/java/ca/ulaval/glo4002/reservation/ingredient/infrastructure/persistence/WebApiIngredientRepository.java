package ca.ulaval.glo4002.reservation.ingredient.infrastructure.persistence;

import ca.ulaval.glo4002.reservation.ingredient.domain.Ingredient;
import ca.ulaval.glo4002.reservation.ingredient.domain.exceptions.IngredientNotFoundException;
import ca.ulaval.glo4002.reservation.ingredient.domain.repositories.IngredientRepository;
import ca.ulaval.glo4002.reservation.ingredient.infrastructure.APIIngredient;
import ca.ulaval.glo4002.reservation.ingredient.infrastructure.assemblers.IngredientAssembler;
import ca.ulaval.glo4002.reservation.ingredient.infrastructure.exceptions.ExternalRepositoryUnresponsiveException;
import ca.ulaval.glo4002.reservation.ingredient.infrastructure.exceptions.InvalidResponseFormatException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebApiIngredientRepository implements IngredientRepository {
  private String GET_INGREDIENTS_URL = "http://localhost:8080/ingredients";

  public Ingredient getIngredientByName(String ingredientName) throws IngredientNotFoundException {
    String jsonString = getJsonFromUrl(GET_INGREDIENTS_URL);
    APIIngredient ingredient = findIngredientInJsonArray(jsonString, ingredientName);
    IngredientAssembler assembler = new IngredientAssembler();

    return assembler.assembleIngredient(ingredient);
  }

  private String getJsonFromUrl(String stringUrl) {
    String result = "";

    try {
      URL url = new URL(stringUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/json");

      BufferedReader output =
          new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuilder content = new StringBuilder();

      while ((inputLine = output.readLine()) != null) {
        content.append(inputLine);
      }
      output.close();
      connection.disconnect();

      result = content.toString();
    } catch (Exception e) {
      throw new ExternalRepositoryUnresponsiveException();
    }

    return result;
  }

  private APIIngredient findIngredientInJsonArray(String JsonString, String IngredientName)
      throws IngredientNotFoundException {
    ObjectMapper mapper = new ObjectMapper();
    List<APIIngredient> ingredients = new ArrayList<>();
    APIIngredient ingredient;

    try {
      ingredients = mapper.readValue(JsonString, new TypeReference<>() {});
    } catch (Exception e) {
      throw new InvalidResponseFormatException();
    }

    for (APIIngredient apiIngredient : ingredients) {
      ingredient = apiIngredient;
      if (ingredient.getName().equals(IngredientName)) {
        return ingredient;
      }
    }
    throw new IngredientNotFoundException();
  }
}
