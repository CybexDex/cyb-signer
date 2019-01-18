package io.cybex.signer.server.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class CancelOrderRequest   {
  
  private String originalTransactionId = null;

  public CancelOrderRequest () {

  }

  public CancelOrderRequest (String originalTransactionId) {
    this.originalTransactionId = originalTransactionId;
  }

    
  @JsonProperty("originalTransactionId")
  public String getOriginalTransactionId() {
    return originalTransactionId;
  }
  public void setOriginalTransactionId(String originalTransactionId) {
    this.originalTransactionId = originalTransactionId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CancelOrderRequest cancelOrderRequest = (CancelOrderRequest) o;
    return Objects.equals(originalTransactionId, cancelOrderRequest.originalTransactionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(originalTransactionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CancelOrderRequest {\n");
    
    sb.append("    originalTransactionId: ").append(toIndentedString(originalTransactionId)).append("\n");
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
