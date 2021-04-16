package com.dealermela.home.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.home.adapter.PopularRecyclerAdapter;
import com.dealermela.home.model.PopularProductItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.NetworkUtils;
import com.dealermela.util.SharedPreferences;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularProductAct extends DealerMelaBaseActivity {

    private RecyclerView recyclerViewPopProducts;
    private LinearLayout linNoData;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private LoginResponse loginResponse;
    public static String customerId = "";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_popular_product;
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        bindToolBar("Popular Products");
        recyclerViewPopProducts = findViewById(R.id.recyclerViewPopProducts);
        linNoData = findViewById(R.id.linNoData);
        progressBar = findViewById(R.id.progressBar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(PopularProductAct.this, 2);
        recyclerViewPopProducts.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void postInitView() {
    }

    @Override
    public void addListener() {
    }

    @Override
    public void loadData() {
        if(NetworkUtils.isNetworkConnected(PopularProductAct.this)) {
            sharedPreferences = new SharedPreferences(PopularProductAct.this);
            if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                getPopularProduct("");
            } else {
                Gson gson = new Gson();
                loginResponse = gson.fromJson(sharedPreferences.getLoginData(), LoginResponse.class);
                customerId = loginResponse.getData().getEntityId();
                getPopularProduct(customerId);
            }
        }
    }

    private void getPopularProduct(String customerId) {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<PopularProductItem> callApi = apiInterface.getPopularProduct(customerId);
        callApi.enqueue(new Callback<PopularProductItem>() {
            @Override
            public void onResponse(@NonNull Call<PopularProductItem> call, @NonNull Response<PopularProductItem> response) {
                assert response.body() != null;
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {

                        PopularRecyclerAdapter popularRecyclerAdapter = new PopularRecyclerAdapter(PopularProductAct.this, response.body().getProductImg());
                        recyclerViewPopProducts.setAdapter(popularRecyclerAdapter);
                    }else{
                        linNoData.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PopularProductItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }
}
