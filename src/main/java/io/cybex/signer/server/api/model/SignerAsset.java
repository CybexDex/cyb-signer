package io.cybex.signer.server.api.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignerAsset {
    private String assetName;
    private String assetId;
    private int precision;

    public SignerAsset(){}

    public SignerAsset(String assetName, String assetId, int precision) {
        this.assetName = assetName;
        this.assetId = assetId;
        this.precision = precision;
    }

    @JsonProperty("assetName")
    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }

    @JsonProperty("assetId")
    public String getAssetId() { return assetId; }
    public void setAssetId(String assetId) { this.assetId = assetId; }

    @JsonProperty("precision")
    public int getPrecision() { return precision;}
    public void setPrecision(int precision) { this.precision = precision; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignerAsset signerAsset = (SignerAsset) o;
        return precision == signerAsset.precision &&
                Objects.equals(assetName, signerAsset.assetName) &&
                Objects.equals(assetId, signerAsset.assetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetName, assetId, precision);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SignerAsset {\n");

        sb.append("    assetName: ").append(toIndentedString(assetName)).append("\n");
        sb.append("    assetId: ").append(toIndentedString(assetId)).append("\n");
        sb.append("    precision: ").append(precision).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
