package com.dealermela.cart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartServerDataItem {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("grandtotal")
    @Expose
    private Integer grandtotal;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("shipping_charge")
    @Expose
    private Integer shippingCharge;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Integer getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(Integer grandtotal) {
        this.grandtotal = grandtotal;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Integer getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(Integer shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public class Datum {

        @SerializedName("product_type")
        @Expose
        private String product_type;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("ringsize")
        @Expose
        private Object ringsize;
        @SerializedName("pendents")
        @Expose
        private String pendents;
        @SerializedName("bangles")
        @Expose
        private Object bangles;
        @SerializedName("bracelets")
        @Expose
        private Object bracelets;
        @SerializedName("metaldetails")
        @Expose
        private String metaldetails;
        @SerializedName("stonedetails")
        @Expose
        private String stonedetails;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("itemid")
        @Expose
        private String itemid;
        @SerializedName("qty")
        @Expose
        private String qty;
        @SerializedName("image")
        @Expose
        private String image;

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getRingsize() {
            return ringsize;
        }

        public void setRingsize(Object ringsize) {
            this.ringsize = ringsize;
        }

        public String getPendents() {
            return pendents;
        }

        public void setPendents(String pendents) {
            this.pendents = pendents;
        }

        public Object getBangles() {
            return bangles;
        }

        public void setBangles(Object bangles) {
            this.bangles = bangles;
        }

        public Object getBracelets() {
            return bracelets;
        }

        public void setBracelets(Object bracelets) {
            this.bracelets = bracelets;
        }

        public String getMetaldetails() {
            return metaldetails;
        }

        public void setMetaldetails(String metaldetails) {
            this.metaldetails = metaldetails;
        }

        public String getStonedetails() {
            return stonedetails;
        }

        public void setStonedetails(String stonedetails) {
            this.stonedetails = stonedetails;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getItemid() {
            return itemid;
        }

        public void setItemid(String itemid) {
            this.itemid = itemid;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

}
