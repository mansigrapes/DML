package com.dealermela.listing_and_detail.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FilterItem {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("sort_by")
    @Expose
    private List<SortBy> sortBy = null;

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

    public List<SortBy> getSortBy() {
        return sortBy;
    }

    public void setSortBy(List<SortBy> sortBy) {
        this.sortBy = sortBy;
    }

    public class Datum implements Serializable {

        @SerializedName("option_name")
        @Expose
        private String optionName;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("option_data")
        @Expose
        private List<OptionDatum> optionData = null;
        @SerializedName("option_type")
        @Expose
        private String option_type;

        @SerializedName("icon")
        @Expose
        private String icon;

        public int getFiltercount() {
            return filtercount;
        }

        public void setFiltercount(int filtercount) {
            this.filtercount = filtercount;
        }

        private int filtercount=0;

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<OptionDatum> getOptionData() {
            return optionData;
        }

        public void setOptionData(List<OptionDatum> optionData) {
            this.optionData = optionData;
        }

        public String getOption_type() {
            return option_type;
        }

        public void setOption_type(String option_type) {
            this.option_type = option_type;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public class OptionDatum {
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("value")
        @Expose
        private String value;

        private boolean isSelected=false;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public class SortBy {

        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("value")
        @Expose
        private String value;
        private boolean isSelected = false;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

}

