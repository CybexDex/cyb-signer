package io.cybex.signer.server.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class AssetAmount   {
  
  private String assetId = null;
  private Long amount = null;

  public AssetAmount () {

  }

  public AssetAmount (String assetId, Long amount) {
    this.assetId = assetId;
    this.amount = amount;
  }

    
  @JsonProperty("assetId")
  public String getAssetId() {
    return assetId;
  }
  public void setAssetId(String assetId) {
    this.assetId = assetId;
  }

    
  @JsonProperty("amount")
  public Long getAmount() {
    return amount;
  }
  public void setAmount(Long amount) {
    this.amount = amount;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssetAmount assetAmount = (AssetAmount) o;
    return Objects.equals(assetId, assetAmount.assetId) &&
        Objects.equals(amount, assetAmount.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(assetId, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssetAmount {\n");
    
    sb.append("    assetId: ").append(toIndentedString(assetId)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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
