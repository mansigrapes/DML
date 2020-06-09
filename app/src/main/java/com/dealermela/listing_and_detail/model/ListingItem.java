package com.dealermela.listing_and_detail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListingItem {
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

        @SerializedName("entity_id")
        @Expose
        private String entityId;
        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("attribute_set_id")
        @Expose
        private String attributeSetId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("url_path")
        @Expose
        private String urlPath;
        @SerializedName("is_sold")
        @Expose
        private String isSold;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;
        @SerializedName("original_sku")
        @Expose
        private String originalSku;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("isreadytoship")
        @Expose
        private String isreadytoship;
        @SerializedName("custom_price")
        @Expose
        private Integer customPrice;
        @SerializedName("rts_stone_quality")
        @Expose
        private Object rtsStoneQuality;
        @SerializedName("metal_quality_value")
        @Expose
        private String metalQualityValue;
        @SerializedName("dml_only")
        @Expose
        private String dmlOnly;
        @SerializedName("qty")
        @Expose
        private String qty;
        @SerializedName("is_salable")
        @Expose
        private String isSalable;
        @SerializedName("stock_item")
        @Expose
        private StockItem stockItem;
        @SerializedName("stock")
        @Expose
        private String stock;
//        @SerializedName("images")
//        @Expose
//        private List<String> images = null;
        @SerializedName("download_flag")
        @Expose
        private Integer downloadFlag;

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getAttributeSetId() {
            return attributeSetId;
        }

        public void setAttributeSetId(String attributeSetId) {
            this.attributeSetId = attributeSetId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrlPath() {
            return urlPath;
        }

        public void setUrlPath(String urlPath) {
            this.urlPath = urlPath;
        }

        public String getIsSold() {
            return isSold;
        }

        public void setIsSold(String isSold) {
            this.isSold = isSold;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getOriginalSku() {
            return originalSku;
        }

        public void setOriginalSku(String originalSku) {
            this.originalSku = originalSku;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsreadytoship() {
            return isreadytoship;
        }

        public void setIsreadytoship(String isreadytoship) {
            this.isreadytoship = isreadytoship;
        }

        public Integer getCustomPrice() {
            return customPrice;
        }

        public void setCustomPrice(Integer customPrice) {
            this.customPrice = customPrice;
        }

        public Object getRtsStoneQuality() {
            return rtsStoneQuality;
        }

        public void setRtsStoneQuality(Object rtsStoneQuality) {
            this.rtsStoneQuality = rtsStoneQuality;
        }

        public String getMetalQualityValue() {
            return metalQualityValue;
        }

        public void setMetalQualityValue(String metalQualityValue) {
            this.metalQualityValue = metalQualityValue;
        }

        public String getDmlOnly() {
            return dmlOnly;
        }

        public void setDmlOnly(String dmlOnly) {
            this.dmlOnly = dmlOnly;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getIsSalable() {
            return isSalable;
        }

        public void setIsSalable(String isSalable) {
            this.isSalable = isSalable;
        }

        public StockItem getStockItem() {
            return stockItem;
        }

        public void setStockItem(StockItem stockItem) {
            this.stockItem = stockItem;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

//        public List<String> getImages() {
//            return images;
//        }
//
//        public void setImages(List<String> images) {
//            this.images = images;
//        }

        public Integer getDownloadFlag() {
            return downloadFlag;
        }

        public void setDownloadFlag(Integer downloadFlag) {
            this.downloadFlag = downloadFlag;
        }

    }

    public class StockItem {


    }
}
