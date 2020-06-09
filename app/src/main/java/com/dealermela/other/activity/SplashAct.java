package com.dealermela.other.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.dbhelper.DatabaseCartAdapter;
import com.dealermela.home.activity.MainActivity;
import com.dealermela.home.model.PopularProductItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.SharedPreferences;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashAct extends DealerMelaBaseActivity {

    public static int loginFlag = 0;
    private List<PopularProductItem.ProductImg> arrayListPopularProduct = new ArrayList<>();
    private DatabaseCartAdapter databaseCartAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        databaseCartAdapter = new DatabaseCartAdapter(SplashAct.this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_splash;
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        ImageView imgSplash = findViewById(R.id.imgSplash);
    }

    @Override
    public void postInitView() {

    }

    @Override
    public void addListener() {

    }

    @Override
    public void loadData() {

        getPopularProduct("");
    }

    private void getPopularProduct(String customerId) {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<PopularProductItem> callApi = apiInterface.getPopularProduct(customerId);
        callApi.enqueue(new Callback<PopularProductItem>() {
            @Override
            public void onResponse(@NonNull Call<PopularProductItem> call, @NonNull Response<PopularProductItem> response) {
                assert response.body() != null;
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                arrayListPopularProduct = response.body().getProductImg();

//                if (response.isSuccessful()) {
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                SharedPreferences sharedPreferences = new SharedPreferences(SplashAct.this);
                Gson gson = new Gson();
                sharedPreferences.savePopularProducts(gson.toJson(arrayListPopularProduct));

                if (sharedPreferences.getRemember().equalsIgnoreCase("true")){

                    if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                        startNewActivity(MainActivity.class);
                        // close this activity
                        finish();
                    } else {
                        startNewActivity(MainActivity.class);
                        finish();
                    }
                }else{
//                    sharedPreferences.saveLoginData("");
//                    sharedPreferences.saveShipping("");
//                    sharedPreferences.saveBillingAddress("");
//                    sharedPreferences.saveRemember("");
                    startNewActivity(MainActivity.class);
                    finish();
                }
//                        }
//                    }, AppConstants.SPLASH_TIME_OUT);
//                }
            }

            @Override
            public void onFailure(@NonNull Call<PopularProductItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }
}
