package com.dealermela.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;


public class SharedPreferences {
    private final android.content.SharedPreferences sharedPreferences;
    private final Editor editor;
    private static final String SHARED = AppConstants.MY_PREF;
    private static final String LOGIN = AppConstants.PREF_LOGIN;
    private static final String REMEMBER_PWD = AppConstants.REMEMBER_PWD;
    private static final String BILLING = AppConstants.PREF_BILLING_ADDRESS;
    private static final String SHIPPING = AppConstants.PREF_SHIPPING_ADDRESS;
    private static final String PASSWORD = AppConstants.PREF_PASSWORD;
    private static final String EMAIL = AppConstants.PREF_EMAILS;
    private static final String POPULAR_PRODUCTS = "POPULAR_PRODUCTS";
    private static final String Download_Flag = "DOWNLOAD_FLAG";


    public SharedPreferences(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    //Get and Save User email
    public String getEmail() {
        return sharedPreferences.getString(EMAIL, "");
    }

    public void saveEmail(String data) {
        editor.putString(EMAIL, data);
        editor.commit();
    }

    //Get and Save User Id
    public String getRemember() {
        return sharedPreferences.getString(REMEMBER_PWD, "");
    }

    public void saveRemember(String data) {
        editor.putString(REMEMBER_PWD, data);
        editor.commit();
    }

    //Get and Save password
    public String getPassword() {
        return sharedPreferences.getString(PASSWORD, "");
    }

    public void savePassword(String data) {
        editor.putString(PASSWORD, data);
        editor.commit();
    }

  //Get and Save User Id
    public String getLoginData() {
        return sharedPreferences.getString(LOGIN, "");
    }

    public void saveLoginData(String data) {
        editor.putString(LOGIN, data);
        editor.commit();
    }

    //Get and Save Billing Address
    public String getBillingAddress() {
        return sharedPreferences.getString(BILLING, "");
    }

    public void saveBillingAddress(String data) {
        editor.putString(BILLING, data);
        editor.commit();
    }


    //Get and Save Shipping Address
    public String getShippingAddress() {
        return sharedPreferences.getString(SHIPPING, "");
    }

    public void saveShipping(String data) {
        editor.putString(SHIPPING, data);
        editor.commit();
    }

    //Get and Save Shipping Address
    public String getPopularProducts() {
        return sharedPreferences.getString(POPULAR_PRODUCTS, "");
    }

    public void savePopularProducts(String data) {
        editor.putString(POPULAR_PRODUCTS, data);
        editor.commit();
    }

    //Get and Save Download Flag
    public String getDownloadFlag(){
        return sharedPreferences.getString(Download_Flag,"");
    }

    public void saveDownloadFlag(String data){
        editor.putString(Download_Flag, data);
        editor.commit();
    }
}
