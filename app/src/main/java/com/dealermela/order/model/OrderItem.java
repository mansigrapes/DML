package com.dealermela.order.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderItem {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("orderno")
        @Expose
        private String orderno;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;
        @SerializedName("invoice_setting")
        @Expose
        private Object invoiceSetting;
        @SerializedName("order_items")
        @Expose
        private List<OrderSubItem> orderItems = null;
        @SerializedName("order_status")
        @Expose
        private String orderStatus;
        @SerializedName("orderid")
        @Expose
        private String orderid;

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public Object getInvoiceSetting() {
            return invoiceSetting;
        }

        public void setInvoiceSetting(Object invoiceSetting) {
            this.invoiceSetting = invoiceSetting;
        }

        public List<OrderSubItem> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderSubItem> orderItems) {
            this.orderItems = orderItems;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }
    }


    public class OrderSubItem {

        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("product_sku")
        @Expose
        private String productSku;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_stonequality")
        @Expose
        private String productStonequality;
        @SerializedName("product_stoneweight")
        @Expose
        private String productStoneweight;
        @SerializedName("product_metalweight")
        @Expose
        private String productMetalweight;
        @SerializedName("product_metalquality")
        @Expose
        private String productMetalquality;
        @SerializedName("bangleSize")
        @Expose
        private String bangleSize;
        @SerializedName("CertificateNo")
        @Expose
        private String certificateNo;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getProductSku() {
            return productSku;
        }

        public void setProductSku(String productSku) {
            this.productSku = productSku;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductStonequality() {
            return productStonequality;
        }

        public void setProductStonequality(String productStonequality) {
            this.productStonequality = productStonequality;
        }

        public String getProductStoneweight() {
            return productStoneweight;
        }

        public void setProductStoneweight(String productStoneweight) {
            this.productStoneweight = productStoneweight;
        }

        public String getProductMetalweight() {
            return productMetalweight;
        }

        public void setProductMetalweight(String productMetalweight) {
            this.productMetalweight = productMetalweight;
        }

        public String getProductMetalquality() {
            return productMetalquality;
        }

        public void setProductMetalquality(String productMetalquality) {
            this.productMetalquality = productMetalquality;
        }

        public String getBangleSize() {
            return bangleSize;
        }

        public void setBangleSize(String bangleSize) {
            this.bangleSize = bangleSize;
        }

        public String getCertificateNo() {
            return certificateNo;
        }

        public void setCertificateNo(String certificateNo) {
            this.certificateNo = certificateNo;
        }
    }

}
