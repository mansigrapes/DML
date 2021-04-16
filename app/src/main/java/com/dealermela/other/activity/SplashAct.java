package com.dealermela.other.activity;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;


import androidx.annotation.NonNull;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.dbhelper.DatabaseCartAdapter;
import com.dealermela.home.activity.MainActivity;
import com.dealermela.home.model.PopularProductItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.NetworkUtils;
import com.dealermela.util.SharedPreferences;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.ligl.android.widget.iosdialog.IOSDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashAct extends DealerMelaBaseActivity {

    public static int loginFlag = 0;
    private List<PopularProductItem.ProductImg> arrayListPopularProduct = new ArrayList<>();
    private DatabaseCartAdapter databaseCartAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;
    int splashtimer = 2500;
    public Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        databaseCartAdapter = new DatabaseCartAdapter(SplashAct.this);
//        throw new RuntimeException("Test Crash");   //testing for check crash reports

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                            handler.postDelayed(this,AppConstants.ADD_TIME_OUT);
//                startNewActivity(MainActivity.class);
                if (NetworkUtils.isNetworkConnected(SplashAct.this)) {
                    startNewActivity(MainActivity.class);
                } else {
////                    run();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashAct.this);
//                    builder.setTitle("No internet Connection");
//                    builder.setMessage("Please turn on internet connection to continue");
//                    builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
////                            dialog.dismiss();
//                            run();
//                        }
//                    });
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();

                    new IOSDialog.Builder(SplashAct.this)
                            .setTitle("No internet Connection")
                            .setMessage("Please turn on internet connection to continue")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
                                    run();
                                }
                            }).show();
                }
            }
        },   splashtimer -= 1000);
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

                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
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

                        if (sharedPreferences.getRemember().equalsIgnoreCase("true")) {

                            if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                                startNewActivity(MainActivity.class);
                                // close this activity
                                finish();
                            } else {
                                startNewActivity(MainActivity.class);
                                finish();
                            }
                        } else {
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
                }else {
                }
            }
            @Override
            public void onFailure(@NonNull Call<PopularProductItem> call, @NonNull Throwable t) {
               getPopularProduct("");
            }
        });
    }
}
