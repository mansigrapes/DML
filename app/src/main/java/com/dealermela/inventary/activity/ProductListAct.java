package com.dealermela.inventary.activity;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.interfaces.RecyclerViewClickListener;
import com.dealermela.inventary.adapter.InventoryProductAdapter;
import com.dealermela.inventary.model.InventoryProductItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.APIClientLaravel;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class ProductListAct extends DealerMelaBaseActivity {
    //page count
    private int page_count = 1;
    private RecyclerView recycleViewProductList;
    private ArrayList<InventoryProductItem.Datum> productList = new ArrayList<>();
    private InventoryProductAdapter inventoryProductAdapter;
    boolean isLoading = false;
    private ProgressBar progressBar;
    private ConstraintLayout constraintNoData;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_product_list;
    }

    @Override
    public void init() {
        bindToolBar("Product List");
    }

    @Override
    public void initView() {
        constraintNoData = findViewById(R.id.constraintNoData);
        recycleViewProductList = findViewById(R.id.recycleViewProductList);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void postInitView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductListAct.this);
        recycleViewProductList.setLayoutManager(linearLayoutManager);
        inventoryProductAdapter = new InventoryProductAdapter(ProductListAct.this, productList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }

            @Override
            public void onItemCheck(View view, int position) {
            }

            @Override
            public void onItemUnCheck(View view, int position) {
            }

        });
        recycleViewProductList.setAdapter(inventoryProductAdapter);
    }

    @Override
    public void addListener() {
        recycleViewProductList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == productList.size() - 1) {
                        //bottom of list!
                        page_count++;
                        getProductList("", "");
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public void loadData() {
        getProductList("", "");
    }

    private void getProductList(String date, String submitToDml) {
        if (page_count != 1) {
            productList.add(null);
            inventoryProductAdapter.notifyItemInserted(productList.size() - 1);
        }
        ApiInterface apiInterface = APIClientLaravel.getClient().create(ApiInterface.class);
        AppLogger.e("customerId", "-------" + customerId);
        Call<InventoryProductItem> callApi = apiInterface.getTryProductsList(String.valueOf(page_count), customerId, date, submitToDml);
        callApi.enqueue(new Callback<InventoryProductItem>() {
            @Override
            public void onResponse(@NonNull Call<InventoryProductItem> call, @NonNull Response<InventoryProductItem> response) {
                AppLogger.e("response", "----------" + Objects.requireNonNull(response.body()).getStatus());
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        if (page_count != 1) {
                            productList.remove(productList.size() - 1);
                            int scrollPosition = productList.size();
                            inventoryProductAdapter.notifyItemRemoved(scrollPosition);
                        }
                        productList.addAll(response.body().getData());
                        inventoryProductAdapter.notifyDataSetChanged();
                        isLoading = false;
                        progressBar.setVisibility(View.INVISIBLE);
                        constraintNoData.setVisibility(View.GONE);
                    } else if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_FAIL)) {
                        if (page_count != 1) {
                            productList.remove(productList.size() - 1);
                            int scrollPosition = productList.size();
                            inventoryProductAdapter.notifyItemRemoved(scrollPosition);
                        } else {
                            constraintNoData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (!productList.isEmpty()) {
                            constraintNoData.setVisibility(View.VISIBLE);
                        }

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<InventoryProductItem> call, @NonNull Throwable t) {
                AppLogger.e("error", t.getMessage());
            }
        });
    }


}
