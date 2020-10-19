package com.dealermela.listing_and_detail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailItem {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("products")
    @Expose
    private String products;
    @SerializedName("simpleproductid")
    @Expose
    private String simpleProductId;
    @SerializedName("product_category_type")
    @Expose
    private String productCategoryType;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("slider")
    @Expose
    private List<String> slider = null;
    @SerializedName("rts_slider")
    @Expose
    private List<RtsSlider> rtsSlider = null;
    @SerializedName("ringsize")
    @Expose
    private List<Ringsize> ringsize = null;
    @SerializedName("stone_clarity")
    @Expose
    private List<StoneClarity> stoneClarity = null;
    @SerializedName("carat")
    @Expose
    private List<String> carat = null;
    @SerializedName("metal")
    @Expose
    private List<String> metal = null;
    @SerializedName("belt_price")
    @Expose
    private String belt_price = null;
//    @SerializedName("metalprice")
//    @Expose
//    private List<Integer> metalprice = null;

//    @SerializedName("metalprice")
//    @Expose
//    private Metalprice metalprice;

    @SerializedName("metalprice")
    @Expose
    private Integer metalprice;

    @SerializedName("diamondmainprice")
    @Expose
    private List<Diamondmainprice> diamondmainprice = null;
    @SerializedName("gemstonemainprice")
    @Expose
    private List<Gemstonemainprice> gemstonemainprice = null;
    @SerializedName("product_details")
    @Expose
    private List<ProductDetail> productDetails = null;
    @SerializedName("diamonddetails")
    @Expose
    private List<Diamonddetail> diamonddetails = null;
    @SerializedName("metaldetails")
    @Expose
    private List<Metaldetail> metaldetails = null;
    @SerializedName("bracelets_size")
    @Expose
    private List<BraceletsSize> braceletsSize = null;
    @SerializedName("bangle_size")
    @Expose
    private List<BangleSize> bangleSize = null;
    @SerializedName("pendent_earring")
    @Expose
    private List<PendentEarring> pendentEarring = null;
    @SerializedName("gemstonedetails")
    @Expose
    private List<Gemstonedetail> gemstonedetails = null;

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

    public String getSimpleProductId() {
        return simpleProductId;
    }

    public void setSimpleProductId(String simpleProductId) {
        this.simpleProductId = simpleProductId;
    }

    public String getProductCategoryType() {
        return productCategoryType;
    }

    public void setProductCategoryType(String productCategoryType) {
        this.productCategoryType = productCategoryType;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public List<String> getSlider() {
        return slider;
    }

    public void setSlider(List<String> slider) {
        this.slider = slider;
    }

    public List<RtsSlider> getRtsSlider() {
        return rtsSlider;
    }

    public void setRtsSlider(List<RtsSlider> rtsSlider) {
        this.rtsSlider = rtsSlider;
    }

    public List<Ringsize> getRingsize() {
        return ringsize;
    }

    public void setRingsize(List<Ringsize> ringsize) {
        this.ringsize = ringsize;
    }

    public List<StoneClarity> getStoneClarity() {
        return stoneClarity;
    }

    public void setStoneClarity(List<StoneClarity> stoneClarity) {
        this.stoneClarity = stoneClarity;
    }

    public List<String> getCarat() {
        return carat;
    }

    public void setCarat(List<String> carat) {
        this.carat = carat;
    }

    public String getBelt_price() {
        return belt_price;
    }

    public void setBelt_price(String belt_price) {
        this.belt_price = belt_price;
    }

    public List<String> getMetal() {
        return metal;
    }

    public void setMetal(List<String> metal) {
        this.metal = metal;
    }

//    public List<Integer> getMetalprice() {
//        return metalprice;
//    }
//
//    public void setMetalprice(List<Integer> metalprice) {
//        this.metalprice = metalprice;
//    }

//    public Metalprice getMetalprice() {
//        return metalprice;
//    }
//
//    public void setMetalprice(Metalprice metalprice) {
//        this.metalprice = metalprice;
//    }

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

    public List<Gemstonemainprice> getGemstonemainprice() {
        return gemstonemainprice;
    }

    public void setGemstonemainprice(List<Gemstonemainprice> gemstonemainprice) {
        this.gemstonemainprice = gemstonemainprice;
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

    public List<BraceletsSize> getBraceletsSize() {
        return braceletsSize;
    }

    public void setBraceletsSize(List<BraceletsSize> braceletsSize) {
        this.braceletsSize = braceletsSize;
    }

    public List<BangleSize> getBangleSize() {
        return bangleSize;
    }

    public void setBangleSize(List<BangleSize> bangleSize) {
        this.bangleSize = bangleSize;
    }

    public List<PendentEarring> getPendentEarring() {
        return pendentEarring;
    }

    public void setPendentEarring(List<PendentEarring> pendentEarring) {
        this.pendentEarring = pendentEarring;
    }

    public List<Gemstonedetail> getGemstonedetails() {
        return gemstonedetails;
    }

    public void setGemstonedetails(List<Gemstonedetail> gemstonedetails) {
        this.gemstonedetails = gemstonedetails;
    }

//    public class Metalprice {
//
//        @SerializedName("price")
//        @Expose
//        private Integer price;
//
//        public Integer getPrice() {
//            return price;
//        }
//        public void setPrice(Integer price) {
//            this.price = price;
//        }
//
//    }

    public class RtsSlider {

        @SerializedName("entity_id")
        @Expose
        private String entityId;
        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("attribute_set_id")
        @Expose
        private String attributeSetId;
        @SerializedName("metal_quality")
        @Expose
        private String metalQuality;
        @SerializedName("metal_quality_value")
        @Expose
        private String metalQualityValue;
        @SerializedName("rts_stone_quality")
        @Expose
        private String rtsStoneQuality;
        @SerializedName("rts_ring_size")
        @Expose
        private String rtsRingSize;
        @SerializedName("custom_price")
        @Expose
        private String customPrice;
        @SerializedName("rts_bangle_size")
        @Expose
        private Object rtsBangleSize;
        @SerializedName("rts_bracelet_size")
        @Expose
        private Object rtsBraceletSize;
        @SerializedName("original_sku")
        @Expose
        private String originalSku;
        @SerializedName("is_sold")
        @Expose
        private String isSold;
        @SerializedName("isreadytoship")
        @Expose
        private String isreadytoship;
        @SerializedName("is_salable")
        @Expose
        private String isSalable;
        @SerializedName("stock_item")
        @Expose
        private StockItem stockItem;
        @SerializedName("diamond_weight")
        @Expose
        private Double diamondWeight;

        private boolean isSelected = false;

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

        public String getMetalQuality() {
            return metalQuality;
        }

        public void setMetalQuality(String metalQuality) {
            this.metalQuality = metalQuality;
        }

        public String getMetalQualityValue() {
            return metalQualityValue;
        }

        public void setMetalQualityValue(String metalQualityValue) {
            this.metalQualityValue = metalQualityValue;
        }

        public String getRtsStoneQuality() {
            return rtsStoneQuality;
        }

        public void setRtsStoneQuality(String rtsStoneQuality) {
            this.rtsStoneQuality = rtsStoneQuality;
        }

        public String getRtsRingSize() {
            return rtsRingSize;
        }

        public void setRtsRingSize(String rtsRingSize) {
            this.rtsRingSize = rtsRingSize;
        }

        public String getCustomPrice() {
            return customPrice;
        }

        public void setCustomPrice(String customPrice) {
            this.customPrice = customPrice;
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

        public String getOriginalSku() {
            return originalSku;
        }

        public void setOriginalSku(String originalSku) {
            this.originalSku = originalSku;
        }

        public String getIsSold() {
            return isSold;
        }

        public void setIsSold(String isSold) {
            this.isSold = isSold;
        }

        public String getIsreadytoship() {
            return isreadytoship;
        }

        public void setIsreadytoship(String isreadytoship) {
            this.isreadytoship = isreadytoship;
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

        public Double getDiamondWeight() {
            return diamondWeight;
        }

        public void setDiamondWeight(Double diamondWeight) {
            this.diamondWeight = diamondWeight;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
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

    public class Gemstonemainprice {

        @SerializedName("pieces")
        @Expose
        private Integer pieces;
        @SerializedName("gemstoneprice")
        @Expose
        private String gemstoneprice;

        public Integer getPieces() {
            return pieces;
        }

        public void setPieces(Integer pieces) {
            this.pieces = pieces;
        }

        public String getGemstoneprice() {
            return gemstoneprice;
        }

        public void setGemstoneprice(String gemstoneprice) {
            this.gemstoneprice = gemstoneprice;
        }
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

    public class StockItem {
    }

    public class BraceletsSize {

        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("product_id")
        @Expose
        private String productId;
        private boolean isSelected = false;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public class BangleSize {

        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("product_id")
        @Expose
        private String productId;
        private boolean isSelected = false;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public class PendentEarring {

        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("product_id")
        @Expose
        private String productId;

        private boolean isSelected = false;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public class Gemstonedetail {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("shape")
        @Expose
        private String shape;
        @SerializedName("setting")
        @Expose
        private String setting;
        @SerializedName("approx_size")
        @Expose
        private String approxSize;
        @SerializedName("pieces")
        @Expose
        private String pieces;
        @SerializedName("gemstoneprice")
        @Expose
        private String gemstoneprice;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

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

        public String getApproxSize() {
            return approxSize;
        }

        public void setApproxSize(String approxSize) {
            this.approxSize = approxSize;
        }

        public String getPieces() {
            return pieces;
        }

        public void setPieces(String pieces) {
            this.pieces = pieces;
        }

        public String getGemstoneprice() {
            return gemstoneprice;
        }

        public void setGemstoneprice(String gemstoneprice) {
            this.gemstoneprice = gemstoneprice;
        }

    }

    public class Ringsize {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("option_type_id")
        @Expose
        private String optionTypeId;
        @SerializedName("option_id")
        @Expose
        private String optionId;

        private boolean isSelected = false;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOptionTypeId() {
            return optionTypeId;
        }

        public void setOptionTypeId(String optionTypeId) {
            this.optionTypeId = optionTypeId;
        }

        public String getOptionId() {
            return optionId;
        }

        public void setOptionId(String optionId) {
            this.optionId = optionId;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

    }

    public class StoneClarity {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("option_type_id")
        @Expose
        private String optionTypeId;
        @SerializedName("option_id")
        @Expose
        private String optionId;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOptionTypeId() {
            return optionTypeId;
        }

        public void setOptionTypeId(String optionTypeId) {
            this.optionTypeId = optionTypeId;
        }

        public String getOptionId() {
            return optionId;
        }

        public void setOptionId(String optionId) {
            this.optionId = optionId;
        }

    }

}
