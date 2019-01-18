package io.cybex.signer.server.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class NewOrder   {
  private String transactionType = null;
  private String transactionId = null;
  private Long refBlockNum = null;
  private Long refBlockPrefix = null;
  private Long txExpiration = null;
  private AssetAmount fee = null;
  private String seller = null;
  private AssetAmount amountToSell = null;
  private AssetAmount minToReceive = null;
  private Long expiration = null;
  private String signature = null;
  private Integer fillOrKill = null;

  public NewOrder () {

  }

  public NewOrder (String transactionType, Long refBlockNum, Long refBlockPrefix, Long txExpiration, AssetAmount fee, String seller, AssetAmount amountToSell, AssetAmount minToReceive, Long expiration, String signature, Integer fillOrKill, String transactionId) {
    this.refBlockNum = refBlockNum;
    this.refBlockPrefix = refBlockPrefix;
    this.txExpiration = txExpiration;
    this.fee = fee;
    this.seller = seller;
    this.amountToSell = amountToSell;
    this.minToReceive = minToReceive;
    this.expiration = expiration;
    this.signature = signature;
    this.fillOrKill = fillOrKill;
    this.transactionId = transactionId;
    this.transactionType = transactionType;
  }

    
  @JsonProperty("refBlockNum")
  public Long getRefBlockNum() {
    return refBlockNum;
  }
  public void setRefBlockNum(Long refBlockNum) {
    this.refBlockNum = refBlockNum;
  }

    
  @JsonProperty("refBlockPrefix")
  public Long getRefBlockPrefix() {
    return refBlockPrefix;
  }
  public void setRefBlockPrefix(Long refBlockPrefix) {
    this.refBlockPrefix = refBlockPrefix;
  }

    
  @JsonProperty("txExpiration")
  public Long getTxExpiration() {
    return txExpiration;
  }
  public void setTxExpiration(Long txExpiration) {
    this.txExpiration = txExpiration;
  }

    
  @JsonProperty("fee")
  public AssetAmount getFee() {
    return fee;
  }
  public void setFee(AssetAmount fee) {
    this.fee = fee;
  }

    
  @JsonProperty("seller")
  public String getSeller() {
    return seller;
  }
  public void setSeller(String seller) {
    this.seller = seller;
  }

    
  @JsonProperty("amountToSell")
  public AssetAmount getAmountToSell() {
    return amountToSell;
  }
  public void setAmountToSell(AssetAmount amountToSell) {
    this.amountToSell = amountToSell;
  }

    
  @JsonProperty("minToReceive")
  public AssetAmount getMinToReceive() {
    return minToReceive;
  }
  public void setMinToReceive(AssetAmount minToReceive) {
    this.minToReceive = minToReceive;
  }

    
  @JsonProperty("expiration")
  public Long getExpiration() {
    return expiration;
  }
  public void setExpiration(Long expiration) {
    this.expiration = expiration;
  }

    
  @JsonProperty("signature")
  public String getSignature() {
    return signature;
  }
  public void setSignature(String signature) {
    this.signature = signature;
  }

    
  @JsonProperty("fill_or_kill")
  public Integer getFillOrKill() {
    return fillOrKill;
  }
  public void setFillOrKill(Integer fillOrKill) {
    this.fillOrKill = fillOrKill;
  }

    
  @JsonProperty("transactionId")
  public String getTransactionId() {
    return transactionId;
  }
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  @JsonProperty("transactionType")
  public String getTransactionType() {
    return transactionType;
  }
  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewOrder newOrder = (NewOrder) o;
    return Objects.equals(refBlockNum, newOrder.refBlockNum) &&
        Objects.equals(refBlockPrefix, newOrder.refBlockPrefix) &&
        Objects.equals(txExpiration, newOrder.txExpiration) &&
        Objects.equals(fee, newOrder.fee) &&
        Objects.equals(seller, newOrder.seller) &&
        Objects.equals(amountToSell, newOrder.amountToSell) &&
        Objects.equals(minToReceive, newOrder.minToReceive) &&
        Objects.equals(expiration, newOrder.expiration) &&
        Objects.equals(signature, newOrder.signature) &&
        Objects.equals(fillOrKill, newOrder.fillOrKill) &&
        Objects.equals(transactionId, newOrder.transactionId) &&
        Objects.equals(transactionType, newOrder.transactionType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionType, refBlockNum, refBlockPrefix, txExpiration, fee, seller, amountToSell, minToReceive, expiration, signature, fillOrKill, transactionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewOrder {\n");
    sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
    sb.append("    refBlockNum: ").append(toIndentedString(refBlockNum)).append("\n");
    sb.append("    refBlockPrefix: ").append(toIndentedString(refBlockPrefix)).append("\n");
    sb.append("    txExpiration: ").append(toIndentedString(txExpiration)).append("\n");
    sb.append("    fee: ").append(toIndentedString(fee)).append("\n");
    sb.append("    seller: ").append(toIndentedString(seller)).append("\n");
    sb.append("    amountToSell: ").append(toIndentedString(amountToSell)).append("\n");
    sb.append("    minToReceive: ").append(toIndentedString(minToReceive)).append("\n");
    sb.append("    expiration: ").append(toIndentedString(expiration)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
    sb.append("    fillOrKill: ").append(toIndentedString(fillOrKill)).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
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
