package com.dealermela.cart.model;

public class CartLocalDataItem {
    private int id;
    private String productId,productType,categoryId,sku,ring_size,bangle_size,bracelet_size,pendent_set_type,metal_detail,stone_detail,price,qty,total_item;
    private String proImage;

    public CartLocalDataItem(int id, String productId, String productType, String categoryId, String sku, String ring_size, String bangle_size, String bracelet_size, String pendent_set_type, String metal_detail, String stone_detail, String price, String qty, String proImage, String totalItem ) {
        this.id = id;
        this.productId = productId;
        this.productType = productType;
        this.categoryId = categoryId;
        this.sku = sku;
        this.ring_size = ring_size;
        this.bangle_size = bangle_size;
        this.bracelet_size = bracelet_size;
        this.pendent_set_type = pendent_set_type;
        this.metal_detail = metal_detail;
        this.stone_detail = stone_detail;
        this.price = price;
        this.qty = qty;
        this.proImage = proImage;
        this.total_item = totalItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getRing_size() {
        return ring_size;
    }

    public void setRing_size(String ring_size) {
        this.ring_size = ring_size;
    }

    public String getBangle_size() {
        return bangle_size;
    }

    public void setBangle_size(String bangle_size) {
        this.bangle_size = bangle_size;
    }

    public String getBracelet_size() {
        return bracelet_size;
    }

    public void setBracelet_size(String bracelet_size) {
        this.bracelet_size = bracelet_size;
    }

    public String getPendent_set_type() {
        return pendent_set_type;
    }

    public void setPendent_set_type(String pendent_set_type) {
        this.pendent_set_type = pendent_set_type;
    }

    public String getMetal_detail() {
        return metal_detail;
    }

    public void setMetal_detail(String metal_detail) {
        this.metal_detail = metal_detail;
    }

    public String getStone_detail() {
        return stone_detail;
    }

    public void setStone_detail(String stone_detail) {
        this.stone_detail = stone_detail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage;
    }

    public String getTotal_item() {
        return total_item;
    }

    public void setTotal_item(String total_item) {
        this.total_item = total_item;
    }
}
