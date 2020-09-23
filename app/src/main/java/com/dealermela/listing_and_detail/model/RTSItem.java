package com.dealermela.listing_and_detail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RTSItem {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("products")
    @Expose
    private String products;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("belt_price")
    @Expose
    private String belt_price = null;
    @SerializedName("metalprice")
    @Expose
    private Integer metalprice;
    @SerializedName("diamondmainprice")
    @Expose
    private List<Diamondmainprice> diamondmainprice = null;
    @SerializedName("product_details")
    @Expose
    private List<ProductDetail> productDetails = null;
    @SerializedName("diamonddetails")
    @Expose
    private List<Diamonddetail> diamonddetails = null;
    @SerializedName("metaldetails")
    @Expose
    private List<Metaldetail> metaldetails = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Integer getMetalprice() {
        return metalprice;
    }

    public void setMetalprice(Integer metalprice) {
        this.metalprice = metalprice;
    }

    public List<Diamondmainprice> getDiamondmainprice() {
        return diamondmainprice;
    }

    public void setDiamondmainprice(List<Diamondmainprice> diamondmainprice) {
        this.diamondmainprice = diamondmainprice;
    }

    public String getBelt_price() {
        return belt_price;
    }

    public void setBelt_price(String belt_price) {
        this.belt_price = belt_price;
    }

    public List<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

    public List<Diamonddetail> getDiamonddetails() {
        return diamonddetails;
    }

    public void setDiamonddetails(List<Diamonddetail> diamonddetails) {
        this.diamonddetails = diamonddetails;
    }

    public List<Metaldetail> getMetaldetails() {
        return metaldetails;
    }

    public void setMetaldetails(List<Metaldetail> metaldetails) {
        this.metaldetails = metaldetails;
    }

    public class Diamonddetail {

        @SerializedName("shape")
        @Expose
        private String shape;
        @SerializedName("setting")
        @Expose
        private String setting;
        @SerializedName("quality")
        @Expose
        private String quality;
        @SerializedName("pieces")
        @Expose
        private String pieces;
        @SerializedName("totalweight")
        @Expose
        private String totalweight;
        @SerializedName("caratrate")
        @Expose
        private String caratrate;
        @SerializedName("estimatedprice")
        @Expose
        private String estimatedprice;

        public String getShape() {
            return shape;
        }

        public void setShape(String shape) {
            this.shape = shape;
        }

        public String getSetting() {
            return setting;
        }

        public void setSetting(String setting) {
            this.setting = setting;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getPieces() {
            return pieces;
        }

        public void setPieces(String pieces) {
            this.pieces = pieces;
        }

        public String getTotalweight() {
            return totalweight;
        }

        public void setTotalweight(String totalweight) {
            this.totalweight = totalweight;
        }

        public String getCaratrate() {
            return caratrate;
        }

        public void setCaratrate(String caratrate) {
            this.caratrate = caratrate;
        }

        public String getEstimatedprice() {
            return estimatedprice;
        }

        public void setEstimatedprice(String estimatedprice) {
            this.estimatedprice = estimatedprice;
        }

    }

    public class Diamondmainprice {

        @SerializedName("pices")
        @Expose
        private String pices;
        @SerializedName("dimondprice")
        @Expose
        private String dimondprice;

        public String getPices() {
            return pices;
        }

        public void setPices(String pices) {
            this.pices = pices;
        }

        public String getDimondprice() {
            return dimondprice;
        }

        public void setDimondprice(String dimondprice) {
            this.dimondprice = dimondprice;
        }

    }

    public class Metaldetail {

        @SerializedName("metalquality")
        @Expose
        private String metalquality;
        @SerializedName("metalweight")
        @Expose
        private String metalweight;
        @SerializedName("metalestimatedprice")
        @Expose
        private Integer metalestimatedprice;

        public String getMetalquality() {
            return metalquality;
        }

        public void setMetalquality(String metalquality) {
            this.metalquality = metalquality;
        }

        public String getMetalweight() {
            return metalweight;
        }

        public void setMetalweight(String metalweight) {
            this.metalweight = metalweight;
        }

        public Integer getMetalestimatedprice() {
            return metalestimatedprice;
        }

        public void setMetalestimatedprice(Integer metalestimatedprice) {
            this.metalestimatedprice = metalestimatedprice;
        }

    }

    public class ProductDetail {

        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("product_sku")
        @Expose
        private String productSku;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("certificate_no")
        @Expose
        private String certificateNo;
        @SerializedName("rts_ring_size")
        @Expose
        private String rtsRingSize;
        @SerializedName("rts_bangle_size")
        @Expose
        private Object rtsBangleSize;
        @SerializedName("rts_bracelet_size")
        @Expose
        private Object rtsBraceletSize;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProductSku() {
            return productSku;
        }

        public void setProductSku(String productSku) {
            this.productSku = productSku;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getCertificateNo() {
            return certificateNo;
        }

        public void setCertificateNo(String certificateNo) {
            this.certificateNo = certificateNo;
        }

        public String getRtsRingSize() {
            return rtsRingSize;
        }

        public void setRtsRingSize(String rtsRingSize) {
            this.rtsRingSize = rtsRingSize;
        }

        public Object getRtsBangleSize() {
            return rtsBangleSize;
        }

        public void setRtsBangleSize(Object rtsBangleSize) {
            this.rtsBangleSize = rtsBangleSize;
        }

        public Object getRtsBraceletSize() {
            return rtsBraceletSize;
        }

        public void setRtsBraceletSize(Object rtsBraceletSize) {
            this.rtsBraceletSize = rtsBraceletSize;
        }

    }

}
