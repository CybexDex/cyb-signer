package io.cybex.signer.server.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetPair {

    private String name = null;
    private BigDecimal minTickSize = BigDecimal.ZERO;
    private BigDecimal minQuantity = BigDecimal.ZERO;

    public AssetPair(String name, BigDecimal minTickSize, BigDecimal minQuantity) {
        this.name = name;
        this.minTickSize = minTickSize;
        this.minQuantity = minQuantity;
    }

    public AssetPair()
    {
    }

    public AssetPair(String name)
    {
        this.name = name;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("minTickSize")
    public BigDecimal getMinTickSize() {
        return minTickSize;
    }
    public void setMinTickSize(BigDecimal minTickSize) {
        this.minTickSize = minTickSize;
    }

    @JsonProperty("minQuantity")
    public BigDecimal getMinQuantity() {
        return minQuantity;
    }
    public void setMinQuantity(BigDecimal minQuantity) {
        this.minQuantity = minQuantity;
    }


}
