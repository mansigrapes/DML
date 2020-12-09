package com.dealermela.transaction.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TransactionItem {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalPaid")
    @Expose
    private Integer totalPaid;
    @SerializedName("totalRemaing")
    @Expose
    private Double  totalRemaing;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Integer totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Double  getTotalRemaing() {
        return totalRemaing;
    }

    public void setTotalRemaing(Double  totalRemaing) {
        this.totalRemaing = totalRemaing;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("cr/dr")
        @Expose
        private String crDr;

        @SerializedName("transaction_item")
        @Expose
        private ArrayList<OrderItem> orderItem = null;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getCrDr() {
            return crDr;
        }

        public void setCrDr(String crDr) {
            this.crDr = crDr;
        }

        public ArrayList<OrderItem> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(ArrayList<OrderItem> orderItem) {
            this.orderItem = orderItem;
        }

    }


    public class OrderItem {

        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("remaing_amount")
        @Expose
        private Integer remaingAmount;
        @SerializedName("paid_date")
        @Expose
        private String paidDate;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getRemaingAmount() {
            return remaingAmount;
        }

        public void setRemaingAmount(Integer remaingAmount) {
            this.remaingAmount = remaingAmount;
        }

        public String getPaidDate() {
            return paidDate;
        }

        public void setPaidDate(String paidDate) {
            this.paidDate = paidDate;
        }

    }
}
