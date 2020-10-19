package com.dealermela.order.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailItem {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public class Datum {

        @SerializedName("order_number")
        @Expose
        private String orderNumber;
        @SerializedName("order_status")
        @Expose
        private String orderStatus;
        @SerializedName("order_date")
        @Expose
        private String orderDate;
        @SerializedName("order_subtotal")
        @Expose
        private String orderSubtotal;
        @SerializedName("order_shippingamount")
        @Expose
        private String orderShippingamount;
        @SerializedName("oder_taxamount")
        @Expose
        private Integer oderTaxamount;
        @SerializedName("order_grandtotal")
        @Expose
        private Integer orderGrandtotal;
        @SerializedName("order_item")
        @Expose
        private List<OrderItem> orderItem = null;
        @SerializedName("shiiping_address")
        @Expose
        private String shiipingAddress;
        @SerializedName("billing_address")
        @Expose
        private String billingAddress;
        @SerializedName("shipping_description")
        @Expose
        private String shippingDescription;
        @SerializedName("payment_method")
        @Expose
        private String paymentMethod;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getOrderSubtotal() {
            return orderSubtotal;
        }

        public void setOrderSubtotal(String orderSubtotal) {
            this.orderSubtotal = orderSubtotal;
        }

        public String getOrderShippingamount() {
            return orderShippingamount;
        }

        public void setOrderShippingamount(String orderShippingamount) {
            this.orderShippingamount = orderShippingamount;
        }

        public Integer getOderTaxamount() {
            return oderTaxamount;
        }

        public void setOderTaxamount(Integer oderTaxamount) {
            this.oderTaxamount = oderTaxamount;
        }

        public Integer getOrderGrandtotal() {
            return orderGrandtotal;
        }

        public void setOrderGrandtotal(Integer orderGrandtotal) {
            this.orderGrandtotal = orderGrandtotal;
        }

        public List<OrderItem> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItem> orderItem) {
            this.orderItem = orderItem;
        }

        public String getShiipingAddress() {
            return shiipingAddress;
        }

        public void setShiipingAddress(String shiipingAddress) {
            this.shiipingAddress = shiipingAddress;
        }

        public String getBillingAddress() {
            return billingAddress;
        }

        public void setBillingAddress(String billingAddress) {
            this.billingAddress = billingAddress;
        }

        public String getShippingDescription() {
            return shippingDescription;
        }

        public void setShippingDescription(String shippingDescription) {
            this.shippingDescription = shippingDescription;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }
    }

    public class OrderItem {

        @SerializedName("product_img")
        @Expose
        private String productImg;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("ringsize")
        @Expose
        private Object ringsize;
        @SerializedName("bangleSize")
        @Expose
        private Object bangleSize;
        @SerializedName("braceletsSize")
        @Expose
        private Object braceletsSize;
        @SerializedName("pendentSize")
        @Expose
        private Object pendentSize;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_sku")
        @Expose
        private String productSku;
        @SerializedName("product_stonequality")
        @Expose
        private String productStonequality;
        @SerializedName("product_stoneweight")
        @Expose
        private String productStoneweight;
        @SerializedName("product_size")
        @Expose
        private String productSize;
        @SerializedName("product_metalweight")
        @Expose
        private String productMetalweight;
        @SerializedName("product_metalquality")
        @Expose
        private String productMetalquality;
        @SerializedName("product_type")
        @Expose
        private String productType;
        @SerializedName("product_price")
        @Expose
        private String productPrice;
        @SerializedName("product_rawtotal")
        @Expose
        private String productRawtotal;
        @SerializedName("product_qty")
        @Expose
        private String productQty;
        @SerializedName("CertificateNo")
        @Expose
        private String certificateNo;

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public Object getRingsize() {
            return ringsize;
        }

        public void setRingsize(Object ringsize) {
            this.ringsize = ringsize;
        }

        public Object getBangleSize() {
            return bangleSize;
        }

        public void setBangleSize(Object bangleSize) {
            this.bangleSize = bangleSize;
        }

        public Object getBraceletsSize() {
            return braceletsSize;
        }

        public void setBraceletsSize(Object braceletsSize) {
            this.braceletsSize = braceletsSize;
        }

        public Object getPendentSize() {
            return pendentSize;
        }

        public void setPendentSize(Object pendentSize) {
            this.pendentSize = pendentSize;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductSku() {
            return productSku;
        }

        public void setProductSku(String productSku) {
            this.productSku = productSku;
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

        public String getProductSize() {
            return productSize;
        }

        public void setProductSize(String productSize) {
            this.productSize = productSize;
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

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductRawtotal() {
            return productRawtotal;
        }

        public void setProductRawtotal(String productRawtotal) {
            this.productRawtotal = productRawtotal;
        }

        public String getProductQty() {
            return productQty;
        }

        public void setProductQty(String productQty) {
            this.productQty = productQty;
        }

        public String getCertificateNo() {
            return certificateNo;
        }

        public void setCertificateNo(String certificateNo) {
            this.certificateNo = certificateNo;
        }
    }
}
