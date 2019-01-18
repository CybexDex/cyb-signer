package io.cybex.signer.server.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class NewOrderRequest   {
  
  private String assetPair = null;
  private Double price = null;
  private Double quantity = null;
  private String side = null;

  public NewOrderRequest () {

  }

  public NewOrderRequest (String assetPair, Double price, Double quantity, String side) {
    this.assetPair = assetPair;
    this.price = price;
    this.quantity = quantity;
    this.side = side;
  }

    
  @JsonProperty("assetPair")
  public String getAssetPair() {
    return assetPair;
  }
  public void setAssetPair(String assetPair) {
    this.assetPair = assetPair;
  }

    
  @JsonProperty("price")
  public Double getPrice() {
    return price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }

    
  @JsonProperty("quantity")
  public Double getQuantity() {
    return quantity;
  }
  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

    
  @JsonProperty("side")
  public String getSide() {
    return side;
  }
  public void setSide(String side) {
    this.side = side;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewOrderRequest newOrderRequest = (NewOrderRequest) o;
    return Objects.equals(assetPair, newOrderRequest.assetPair) &&
        Objects.equals(price, newOrderRequest.price) &&
        Objects.equals(quantity, newOrderRequest.quantity) &&
        Objects.equals(side, newOrderRequest.side);
  }

  @Override
  public int hashCode() {
    return Objects.hash(assetPair, price, quantity, side);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewOrderRequest {\n");
    
    sb.append("    assetPair: ").append(toIndentedString(assetPair)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    side: ").append(toIndentedString(side)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
