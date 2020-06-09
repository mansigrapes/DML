package com.dealermela.authentication.myaccount.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("customer_role")
    @Expose
    private String customerRole;
    @SerializedName("data")
    @Expose
    private Data data = null;

    @SerializedName("message")
    @Expose
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerRole() {
        return customerRole;
    }

    public void setCustomerRole(String customerRole) {
        this.customerRole = customerRole;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("referral_comission")
        @Expose
        private String referral_comission;
        @SerializedName("entity_id")
        @Expose
        private String entityId;
        @SerializedName("entity_type_id")
        @Expose
        private String entityTypeId;
        @SerializedName("attribute_set_id")
        @Expose
        private String attributeSetId;
        @SerializedName("increment_id")
        @Expose
        private Object incrementId;
        @SerializedName("parent_id")
        @Expose
        private Integer parentId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("is_active")
        @Expose
        private String isActive;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("country_id")
        @Expose
        private String countryId;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("postcode")
        @Expose
        private String postcode;
        @SerializedName("telephone")
        @Expose
        private String telephone;
        @SerializedName("region_id")
        @Expose
        private String regionId;
        @SerializedName("street")
        @Expose
        private String street;
        @SerializedName("website_id")
        @Expose
        private String websiteId;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("group_id")
        @Expose
        private String groupId;
        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("disable_auto_group_change")
        @Expose
        private String disableAutoGroupChange;
        @SerializedName("created_in")
        @Expose
        private String createdIn;
        @SerializedName("password_hash")
        @Expose
        private String passwordHash;
        @SerializedName("rp_token")
        @Expose
        private String rpToken;
        @SerializedName("contact_number")
        @Expose
        private String contactNumber;
        @SerializedName("refcode")
        @Expose
        private String refcode;
        @SerializedName("wallet")
        @Expose
        private String wallet;
        @SerializedName("pancardno")
        @Expose
        private String pancardno;
        @SerializedName("gstin")
        @Expose
        private String gstin;
        @SerializedName("discount_less_twentyfive")
        @Expose
        private String discountLessTwentyfive;
        @SerializedName("discount_twentyfive_to_lakhs")
        @Expose
        private String discountTwentyfiveToLakhs;
        @SerializedName("discount_above_lakhs")
        @Expose
        private String discountAboveLakhs;
        @SerializedName("notification_token")
        @Expose
        private String notificationToken;
        @SerializedName("device_id")
        @Expose
        private String deviceId;
        @SerializedName("default_billing")
        @Expose
        private String defaultBilling;
        @SerializedName("default_shipping")
        @Expose
        private String defaultShipping;
        @SerializedName("entity_customer")
        @Expose
        private String entityCustomer;
        @SerializedName("_isfranchisee")
        @Expose
        private String isfranchisee;
        @SerializedName("entity")
        @Expose
        private String entity;
        @SerializedName("franchisee_status")
        @Expose
        private String franchiseeStatus;
        @SerializedName("franchise_referral_status")
        @Expose
        private String franchiseReferralStatus;
        @SerializedName("community")
        @Expose
        private String community;
        @SerializedName("invoice_setting")
        @Expose
        private String invoiceSetting;
        @SerializedName("customer_theme")
        @Expose
        private String customerTheme;
        @SerializedName("rp_token_created_at")
        @Expose
        private String rpTokenCreatedAt;
        @SerializedName("download_products")
        @Expose
        private String downloadProducts;
        @SerializedName("default_billing_new")
        @Expose
        private DefaultBillingNew defaultBillingNew;
        @SerializedName("default_shipping_new")
        @Expose
        private DefaultShippingNew defaultShippingNew;
        @SerializedName("rp_token_created_at_is_formated")
        @Expose
        private Boolean rpTokenCreatedAtIsFormated;
        @SerializedName("confirmation")
        @Expose
        private Object confirmation;

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getEntityTypeId() {
            return entityTypeId;
        }

        public void setEntityTypeId(String entityTypeId) {
            this.entityTypeId = entityTypeId;
        }

        public String getAttributeSetId() {
            return attributeSetId;
        }

        public void setAttributeSetId(String attributeSetId) {
            this.attributeSetId = attributeSetId;
        }

        public Object getIncrementId() {
            return incrementId;
        }

        public void setIncrementId(Object incrementId) {
            this.incrementId = incrementId;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getWebsiteId() {
            return websiteId;
        }

        public void setWebsiteId(String websiteId) {
            this.websiteId = websiteId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getDisableAutoGroupChange() {
            return disableAutoGroupChange;
        }

        public void setDisableAutoGroupChange(String disableAutoGroupChange) {
            this.disableAutoGroupChange = disableAutoGroupChange;
        }

        public String getCreatedIn() {
            return createdIn;
        }

        public void setCreatedIn(String createdIn) {
            this.createdIn = createdIn;
        }

        public String getPasswordHash() {
            return passwordHash;
        }

        public void setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
        }

        public String getRpToken() {
            return rpToken;
        }

        public void setRpToken(String rpToken) {
            this.rpToken = rpToken;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getRefcode() {
            return refcode;
        }

        public void setRefcode(String refcode) {
            this.refcode = refcode;
        }

        public String getWallet() {
            return wallet;
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public String getPancardno() {
            return pancardno;
        }

        public void setPancardno(String pancardno) {
            this.pancardno = pancardno;
        }

        public String getGstin() {
            return gstin;
        }

        public void setGstin(String gstin) {
            this.gstin = gstin;
        }

        public String getDiscountLessTwentyfive() {
            return discountLessTwentyfive;
        }

        public void setDiscountLessTwentyfive(String discountLessTwentyfive) {
            this.discountLessTwentyfive = discountLessTwentyfive;
        }

        public String getDiscountTwentyfiveToLakhs() {
            return discountTwentyfiveToLakhs;
        }

        public void setDiscountTwentyfiveToLakhs(String discountTwentyfiveToLakhs) {
            this.discountTwentyfiveToLakhs = discountTwentyfiveToLakhs;
        }

        public String getDiscountAboveLakhs() {
            return discountAboveLakhs;
        }

        public void setDiscountAboveLakhs(String discountAboveLakhs) {
            this.discountAboveLakhs = discountAboveLakhs;
        }

        public String getNotificationToken() {
            return notificationToken;
        }

        public void setNotificationToken(String notificationToken) {
            this.notificationToken = notificationToken;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDefaultBilling() {
            return defaultBilling;
        }

        public void setDefaultBilling(String defaultBilling) {
            this.defaultBilling = defaultBilling;
        }

        public String getDefaultShipping() {
            return defaultShipping;
        }

        public void setDefaultShipping(String defaultShipping) {
            this.defaultShipping = defaultShipping;
        }

        public String getEntityCustomer() {
            return entityCustomer;
        }

        public void setEntityCustomer(String entityCustomer) {
            this.entityCustomer = entityCustomer;
        }

        public String getIsfranchisee() {
            return isfranchisee;
        }

        public void setIsfranchisee(String isfranchisee) {
            this.isfranchisee = isfranchisee;
        }

        public String getEntity() {
            return entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getFranchiseeStatus() {
            return franchiseeStatus;
        }

        public void setFranchiseeStatus(String franchiseeStatus) {
            this.franchiseeStatus = franchiseeStatus;
        }

        public String getFranchiseReferralStatus() {
            return franchiseReferralStatus;
        }

        public void setFranchiseReferralStatus(String franchiseReferralStatus) {
            this.franchiseReferralStatus = franchiseReferralStatus;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getInvoiceSetting() {
            return invoiceSetting;
        }

        public void setInvoiceSetting(String invoiceSetting) {
            this.invoiceSetting = invoiceSetting;
        }

        public String getCustomerTheme() {
            return customerTheme;
        }

        public void setCustomerTheme(String customerTheme) {
            this.customerTheme = customerTheme;
        }

        public String getRpTokenCreatedAt() {
            return rpTokenCreatedAt;
        }

        public void setRpTokenCreatedAt(String rpTokenCreatedAt) {
            this.rpTokenCreatedAt = rpTokenCreatedAt;
        }

        public String getDownloadProducts() {
            return downloadProducts;
        }

        public void setDownloadProducts(String downloadProducts) {
            this.downloadProducts = downloadProducts;
        }

        public DefaultBillingNew getDefaultBillingNew() {
            return defaultBillingNew;
        }

        public void setDefaultBillingNew(DefaultBillingNew defaultBillingNew) {
            this.defaultBillingNew = defaultBillingNew;
        }

        public DefaultShippingNew getDefaultShippingNew() {
            return defaultShippingNew;
        }

        public void setDefaultShippingNew(DefaultShippingNew defaultShippingNew) {
            this.defaultShippingNew = defaultShippingNew;
        }

        public Boolean getRpTokenCreatedAtIsFormated() {
            return rpTokenCreatedAtIsFormated;
        }

        public void setRpTokenCreatedAtIsFormated(Boolean rpTokenCreatedAtIsFormated) {
            this.rpTokenCreatedAtIsFormated = rpTokenCreatedAtIsFormated;
        }

        public Object getConfirmation() {
            return confirmation;
        }

        public void setConfirmation(Object confirmation) {
            this.confirmation = confirmation;
        }

        public String getReferral_comission() {
            return referral_comission;
        }

        public void setReferral_comission(String referral_comission) {
            this.referral_comission = referral_comission;
        }
    }

    public class DefaultBillingNew {

        @SerializedName("entity_id")
        @Expose
        private String entityId;
        @SerializedName("entity_type_id")
        @Expose
        private String entityTypeId;
        @SerializedName("attribute_set_id")
        @Expose
        private String attributeSetId;
        @SerializedName("increment_id")
        @Expose
        private Object incrementId;
        @SerializedName("parent_id")
        @Expose
        private String parentId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("is_active")
        @Expose
        private String isActive;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("country_id")
        @Expose
        private String countryId;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("postcode")
        @Expose
        private String postcode;
        @SerializedName("telephone")
        @Expose
        private String telephone;
        @SerializedName("region_id")
        @Expose
        private String regionId;
        @SerializedName("street")
        @Expose
        private String street;

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getEntityTypeId() {
            return entityTypeId;
        }

        public void setEntityTypeId(String entityTypeId) {
            this.entityTypeId = entityTypeId;
        }

        public String getAttributeSetId() {
            return attributeSetId;
        }

        public void setAttributeSetId(String attributeSetId) {
            this.attributeSetId = attributeSetId;
        }

        public Object getIncrementId() {
            return incrementId;
        }

        public void setIncrementId(Object incrementId) {
            this.incrementId = incrementId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

    }

    public class DefaultShippingNew {

        @SerializedName("entity_id")
        @Expose
        private String entityId;
        @SerializedName("entity_type_id")
        @Expose
        private String entityTypeId;
        @SerializedName("attribute_set_id")
        @Expose
        private String attributeSetId;
        @SerializedName("increment_id")
        @Expose
        private Object incrementId;
        @SerializedName("parent_id")
        @Expose
        private String parentId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("is_active")
        @Expose
        private String isActive;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("country_id")
        @Expose
        private String countryId;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("postcode")
        @Expose
        private String postcode;
        @SerializedName("telephone")
        @Expose
        private String telephone;
        @SerializedName("region_id")
        @Expose
        private String regionId;
        @SerializedName("street")
        @Expose
        private String street;

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getEntityTypeId() {
            return entityTypeId;
        }

        public void setEntityTypeId(String entityTypeId) {
            this.entityTypeId = entityTypeId;
        }

        public String getAttributeSetId() {
            return attributeSetId;
        }

        public void setAttributeSetId(String attributeSetId) {
            this.attributeSetId = attributeSetId;
        }

        public Object getIncrementId() {
            return incrementId;
        }

        public void setIncrementId(Object incrementId) {
            this.incrementId = incrementId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

    }
}


