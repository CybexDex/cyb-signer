package io.cybex.signer.server.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class CancelAllRequest   {
  
  private String assetPair = null;

  public CancelAllRequest () {

  }

  public CancelAllRequest (String assetPair) {
    this.assetPair = assetPair;
  }

    
  @JsonProperty("assetPair")
  public String getAssetPair() {
    return assetPair;
  }
  public void setAssetPair(String assetPair) {
    this.assetPair = assetPair;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CancelAllRequest cancelAllRequest = (CancelAllRequest) o;
    return Objects.equals(assetPair, cancelAllRequest.assetPair);
  }

  @Override
  public int hashCode() {
    return Objects.hash(assetPair);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CancelAllRequest {\n");
    
    sb.append("    assetPair: ").append(toIndentedString(assetPair)).append("\n");
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
