package com.dealermela.cart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderSummaryItem {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;
    @SerializedName("grand_total")
    @Expose
    private Integer grandTotal;
    @SerializedName("tax_ammount")
    @Expose
    private Integer taxAmmount;
    @SerializedName("payment")
    @Expose
    private String payment;
    @SerializedName("shipping_charges")
    @Expose
    private Integer shippingCharges;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Integer getTaxAmmount() {
        return taxAmmount;
    }

    public void setTaxAmmount(Integer taxAmmount) {
        this.taxAmmount = taxAmmount;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Integer getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(Integer shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("metaldetails")
        @Expose
        private String metaldetails;
        @SerializedName("stonequality")
        @Expose
        private String stonequality;
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
        @SerializedName("qty")
        @Expose
        private Integer qty;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("subtotal")
        @Expose
        private Integer subtotal;
        @SerializedName("billing_address")
        @Expose
        private String billingAddress;
        @SerializedName("billing_address_individual")
        @Expose
        private String billingAddressIndividual;
        @SerializedName("billing_city_individual")
        @Expose
        private String billingCityIndividual;
        @SerializedName("billing_zip_individual")
        @Expose
        private String billingZipIndividual;
        @SerializedName("billing_country_individual")
        @Expose
        private String billingCountryIndividual;
        @SerializedName("billing_region_individual")
        @Expose
        private String billingRegionIndividual;
        @SerializedName("shipping_address")
        @Expose
        private String shippingAddress;
        @SerializedName("shipping_address_individual")
        @Expose
        private String shippingAddressIndividual;
        @SerializedName("shipping_city_individual")
        @Expose
        private String shippingCityIndividual;
        @SerializedName("shipping_zip_individual")
        @Expose
        private String shippingZipIndividual;
        @SerializedName("shipping_country_individual")
        @Expose
        private String shippingCountryIndividual;
        @SerializedName("shipping_region_individual")
        @Expose
        private String shippingRegionIndividual;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("contact_number")
        @Expose
        private String contactNumber;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getMetaldetails() {
            return metaldetails;
        }

        public void setMetaldetails(String metaldetails) {
            this.metaldetails = metaldetails;
        }

        public String getStonequality() {
            return stonequality;
        }

        public void setStonequality(String stonequality) {
            this.stonequality = stonequality;
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

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(Integer subtotal) {
            this.subtotal = subtotal;
        }

        public String getBillingAddress() {
            return billingAddress;
        }

        public void setBillingAddress(String billingAddress) {
            this.billingAddress = billingAddress;
        }

        public String getBillingAddressIndividual() {
            return billingAddressIndividual;
        }

        public void setBillingAddressIndividual(String billingAddressIndividual) {
            this.billingAddressIndividual = billingAddressIndividual;
        }

        public String getBillingCityIndividual() {
            return billingCityIndividual;
        }

        public void setBillingCityIndividual(String billingCityIndividual) {
            this.billingCityIndividual = billingCityIndividual;
        }

        public String getBillingZipIndividual() {
            return billingZipIndividual;
        }

        public void setBillingZipIndividual(String billingZipIndividual) {
            this.billingZipIndividual = billingZipIndividual;
        }

        public String getBillingCountryIndividual() {
            return billingCountryIndividual;
        }

        public void setBillingCountryIndividual(String billingCountryIndividual) {
            this.billingCountryIndividual = billingCountryIndividual;
        }

        public String getBillingRegionIndividual() {
            return billingRegionIndividual;
        }

        public void setBillingRegionIndividual(String billingRegionIndividual) {
            this.billingRegionIndividual = billingRegionIndividual;
        }

        public String getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        public String getShippingAddressIndividual() {
            return shippingAddressIndividual;
        }

        public void setShippingAddressIndividual(String shippingAddressIndividual) {
            this.shippingAddressIndividual = shippingAddressIndividual;
        }

        public String getShippingCityIndividual() {
            return shippingCityIndividual;
        }

        public void setShippingCityIndividual(String shippingCityIndividual) {
            this.shippingCityIndividual = shippingCityIndividual;
        }

        public String getShippingZipIndividual() {
            return shippingZipIndividual;
        }

        public void setShippingZipIndividual(String shippingZipIndividual) {
            this.shippingZipIndividual = shippingZipIndividual;
        }

        public String getShippingCountryIndividual() {
            return shippingCountryIndividual;
        }

        public void setShippingCountryIndividual(String shippingCountryIndividual) {
            this.shippingCountryIndividual = shippingCountryIndividual;
        }

        public String getShippingRegionIndividual() {
            return shippingRegionIndividual;
        }

        public void setShippingRegionIndividual(String shippingRegionIndividual) {
            this.shippingRegionIndividual = shippingRegionIndividual;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }
    }
}
