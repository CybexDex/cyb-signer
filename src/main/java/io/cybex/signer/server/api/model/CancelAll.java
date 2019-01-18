package io.cybex.signer.server.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class CancelAll   {
  private String transactionType = null;
  private String transactionId = null;
  private Long refBlockNum = null;
  private Long refBlockPrefix = null;
  private Long txExpiration = null;
  private AssetAmount fee = null;
  private String seller = null;
  private String sellAssetId = null;
  private String recvAssetId = null;
  private String signature = null;

  public CancelAll () {

  }

  public CancelAll (String transactionType, String transactionId, Long refBlockNum, Long refBlockPrefix, Long txExpiration, AssetAmount fee, String seller, String sellAssetId, String recvAssetId, String signature) {
    this.refBlockNum = refBlockNum;
    this.refBlockPrefix = refBlockPrefix;
    this.txExpiration = txExpiration;
    this.fee = fee;
    this.seller = seller;
    this.sellAssetId = sellAssetId;
    this.recvAssetId = recvAssetId;
    this.signature = signature;
    this.transactionType = transactionType;
    this.transactionId = transactionId;
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

    
  @JsonProperty("sellAssetId")
  public String getSellAssetId() {
    return sellAssetId;
  }
  public void setSellAssetId(String sellAssetId) {
    this.sellAssetId = sellAssetId;
  }

    
  @JsonProperty("recvAssetId")
  public String getRecvAssetId() {
    return recvAssetId;
  }
  public void setRecvAssetId(String recvAssetId) {
    this.recvAssetId = recvAssetId;
  }

    
  @JsonProperty("signature")
  public String getSignature() {
    return signature;
  }
  public void setSignature(String signature) {
    this.signature = signature;
  }

  @JsonProperty("transactionType")
  public String getTransactionType() {
    return transactionType;
  }
  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  @JsonProperty("transactionId")
  public String getTransactionId() {
    return transactionId;
  }
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CancelAll cancelAll = (CancelAll) o;
    return Objects.equals(refBlockNum, cancelAll.refBlockNum) &&
        Objects.equals(refBlockPrefix, cancelAll.refBlockPrefix) &&
        Objects.equals(txExpiration, cancelAll.txExpiration) &&
        Objects.equals(fee, cancelAll.fee) &&
        Objects.equals(seller, cancelAll.seller) &&
        Objects.equals(sellAssetId, cancelAll.sellAssetId) &&
        Objects.equals(recvAssetId, cancelAll.recvAssetId) &&
        Objects.equals(signature, cancelAll.signature) &&
        Objects.equals(transactionType, cancelAll.transactionType) &&
            Objects.equals(transactionId, cancelAll.transactionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionType, transactionId, refBlockNum, refBlockPrefix, txExpiration, fee, seller, sellAssetId, recvAssetId, signature);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CancelAll {\n");
    sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    refBlockNum: ").append(toIndentedString(refBlockNum)).append("\n");
    sb.append("    refBlockPrefix: ").append(toIndentedString(refBlockPrefix)).append("\n");
    sb.append("    txExpiration: ").append(toIndentedString(txExpiration)).append("\n");
    sb.append("    fee: ").append(toIndentedString(fee)).append("\n");
    sb.append("    seller: ").append(toIndentedString(seller)).append("\n");
    sb.append("    sellAssetId: ").append(toIndentedString(sellAssetId)).append("\n");
    sb.append("    recvAssetId: ").append(toIndentedString(recvAssetId)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
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
