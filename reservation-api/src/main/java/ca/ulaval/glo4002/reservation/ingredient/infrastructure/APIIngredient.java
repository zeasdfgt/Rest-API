package ca.ulaval.glo4002.reservation.ingredient.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties({"shipments"})
public class APIIngredient {
  private int id;
  private String name;
  private String origin;
  private BigDecimal pricePerKg;

  public APIIngredient() {}

  @JsonProperty("id")
  public int getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(int id) {
    this.id = id;
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("origin")
  public String getOrigin() {
    return origin;
  }

  @JsonProperty("origin")
  public void setOrigin(String origin) {
    this.origin = origin;
  }

  @JsonProperty("pricePerKg")
  public BigDecimal getPricePerKg() {
    return pricePerKg;
  }

  @JsonProperty("pricePerKg")
  public void setPricePerKg(BigDecimal pricePerKg) {
    this.pricePerKg = pricePerKg;
  }
}
