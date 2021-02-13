package com.dealermela.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QrCodeResponse  {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("product_id")
    @Expose
    private Integer productId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

}
