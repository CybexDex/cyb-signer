package io.cybex.signer.server.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class CancelOrder   {
  private String transactionType = null;
  private String transactionId = null;
  private String originalTransactionId = null;
  private Long refBlockNum = null;
  private Long refBlockPrefix = null;
  private Long txExpiration = null;
  private String orderId = null;
  private AssetAmount fee = null;
  private String feePayingAccount = null;
  private String signature = null;

  public CancelOrder () {

  }

  public CancelOrder (String transactionType, String transactionId, String originalTransactionId, Long refBlockNum, Long refBlockPrefix, Long txExpiration, String orderId, AssetAmount fee, String feePayingAccount, String signature) {
    this.originalTransactionId = originalTransactionId;
    this.refBlockNum = refBlockNum;
    this.refBlockPrefix = refBlockPrefix;
    this.txExpiration = txExpiration;
    this.orderId = orderId;
    this.fee = fee;
    this.feePayingAccount = feePayingAccount;
    this.signature = signature;
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

    
  @JsonProperty("orderId")
  public String getOrderId() {
    return orderId;
  }
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

    
  @JsonProperty("fee")
  public AssetAmount getFee() {
    return fee;
  }
  public void setFee(AssetAmount fee) {
    this.fee = fee;
  }

    
  @JsonProperty("feePayingAccount")
  public String getFeePayingAccount() {
    return feePayingAccount;
  }
  public void setFeePayingAccount(String feePayingAccount) {
    this.feePayingAccount = feePayingAccount;
  }

    
  @JsonProperty("signature")
  public String getSignature() {
    return signature;
  }
  public void setSignature(String signature) {
    this.signature = signature;
  }

    
  @JsonProperty("originalTransactionId")
  public String getOriginalTransactionId() {
    return originalTransactionId;
  }
  public void setOriginalTransactionId(String originalTransactionId) {
    this.originalTransactionId = originalTransactionId;
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
    CancelOrder cancelOrder = (CancelOrder) o;
    return Objects.equals(refBlockNum, cancelOrder.refBlockNum) &&
        Objects.equals(refBlockPrefix, cancelOrder.refBlockPrefix) &&
        Objects.equals(txExpiration, cancelOrder.txExpiration) &&
        Objects.equals(orderId, cancelOrder.orderId) &&
        Objects.equals(fee, cancelOrder.fee) &&
        Objects.equals(feePayingAccount, cancelOrder.feePayingAccount) &&
        Objects.equals(signature, cancelOrder.signature) &&
        Objects.equals(transactionId, cancelOrder.transactionId) &&
            Objects.equals(originalTransactionId, cancelOrder.originalTransactionId) &&
        Objects.equals(transactionType, cancelOrder.transactionType)  ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionType, transactionId, originalTransactionId, refBlockNum, refBlockPrefix, txExpiration, orderId, fee, feePayingAccount, signature);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CancelOrder {\n");
    sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    originalTransactionId: ").append(toIndentedString(originalTransactionId)).append("\n");
    sb.append("    refBlockNum: ").append(toIndentedString(refBlockNum)).append("\n");
    sb.append("    refBlockPrefix: ").append(toIndentedString(refBlockPrefix)).append("\n");
    sb.append("    txExpiration: ").append(toIndentedString(txExpiration)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    fee: ").append(toIndentedString(fee)).append("\n");
    sb.append("    feePayingAccount: ").append(toIndentedString(feePayingAccount)).append("\n");
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
