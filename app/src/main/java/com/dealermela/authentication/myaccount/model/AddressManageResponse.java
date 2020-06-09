package com.dealermela.authentication.myaccount.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressManageResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("default_billing")
    @Expose
    private DefaultBilling defaultBilling;
    @SerializedName("default_shipping")
    @Expose
    private DefaultShipping defaultShipping;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DefaultBilling getDefaultBilling() {
        return defaultBilling;
    }

    public void setDefaultBilling(DefaultBilling defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    public DefaultShipping getDefaultShipping() {
        return defaultShipping;
    }

    public void setDefaultShipping(DefaultShipping defaultShipping) {
        this.defaultShipping = defaultShipping;
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
        @SerializedName("postcode")
        @Expose
        private String postcode;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("country_id")
        @Expose
        private String countryId;
        @SerializedName("telephone")
        @Expose
        private String telephone;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("region_id")
        @Expose
        private String regionId;
        @SerializedName("street")
        @Expose
        private String street;
//        @SerializedName("street1")
//        @Expose
//        private String street1;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("country")
        @Expose
        private String country;

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

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
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

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
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

//        public String getStreet1() {
//            return street1;
//        }
//
//        public void setStreet1(String street1) {
//            this.street1 = street1;
//        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public class DefaultBilling {

        @SerializedName("entity_id")
        @Expose
        private String entityId;
        @SerializedName("country")
        @Expose
        private String country;
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
//        @SerializedName("street1")
//        @Expose
//        private String street1;

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

//        public String getStreet1() {
//            return street1;
//        }
//
//        public void setStreet1(String street1) {
//            this.street1 = street1;
//        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public class DefaultShipping {

        @SerializedName("country")
        @Expose
        private String country;
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
//        @SerializedName("street1")
//        @Expose
//        private String street1;

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

//        public String getStreet1() {
//            return street1;
//        }
//
//        public void setStreet1(String street1) {
//            this.street1 = street1;
//        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

}
