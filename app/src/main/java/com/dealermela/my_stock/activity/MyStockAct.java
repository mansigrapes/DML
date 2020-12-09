package com.dealermela.my_stock.activity;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.listing_and_detail.activity.FilterAct;
import com.dealermela.listing_and_detail.model.FilterItem;
import com.dealermela.my_stock.adapter.MyStockRecyclerAdapter;
import com.dealermela.my_stock.adapter.SortByMyStockRecyclerAdapter;
import com.dealermela.my_stock.model.MyStockItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.NetworkUtils;
import com.dealermela.util.SharedPreferences;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.inventary.activity.InventoryFilterAct.filterFlag;
import static com.dealermela.listing_and_detail.activity.FilterAct.mapFilter;
import static com.dealermela.home.activity.MainActivity.customerId;

public class MyStockAct extends DealerMelaBaseActivity implements View.OnClickListener {

    private RecyclerView recycleViewListing;
    private LinearLayout linSortBy, linFilter;
    private MyStockRecyclerAdapter myStockRecyclerAdapter;
    private List<MyStockItem.Datum> itemArrayList;

    //page count
    private int page_count = 1;
    private boolean flag_scroll = false;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private final int visibleThreshold = 0; // The minimum amount of items to have below your current scroll position before loading more.
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;
    private LoginResponse loginResponse;
    //    private HeaderItem.Datum datum;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar progressCenter, progressBottom;
    private LinearLayout linNoData;

    private List<FilterItem.SortBy> sortByList = new ArrayList<>();
    BottomSheetDialog mBottomSheetDialog;

    private String price = "", gold_purity = "", diamond_quality = "", diamond_shape = "", sku = "", availability = "", sort_by = "";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_my_stock;
    }

    @Override
    public void init() {
        //  datum = (HeaderItem.Datum) getIntent().getSerializableExtra(AppConstants.NAME);
        SharedPreferences sharedPreferences = new SharedPreferences(MyStockAct.this);
        Gson gson = new Gson();
        loginResponse = gson.fromJson(sharedPreferences.getLoginData(), LoginResponse.class);
        itemArrayList = new ArrayList<>();
    }

    @Override
    public void initView() {
        bindToolBar("My Stock");
        linSortBy = findViewById(R.id.linSortBy);
        linFilter = findViewById(R.id.linFilterOrder);
        progressCenter = findViewById(R.id.progressCenter);
        progressBottom = findViewById(R.id.progressBottom);

        gridLayoutManager = new GridLayoutManager(MyStockAct.this, 2);
        recycleViewListing = findViewById(R.id.recycleViewListing);
        recycleViewListing.setLayoutManager(gridLayoutManager);
        linNoData = findViewById(R.id.newlinNoData);
        mBottomSheetDialog = new BottomSheetDialog(MyStockAct.this);
        linSortBy.setVisibility(View.GONE);
    }

    @Override
    public void postInitView() {
    }

    @Override
    public void addListener() {
        linSortBy.setOnClickListener(this);
        linFilter.setOnClickListener(this);

        recycleViewListing.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                                   @Override
                                                   public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                                       super.onScrolled(recyclerView, dx, dy);

                                                       visibleItemCount = recyclerView.getChildCount();
                                                       totalItemCount = gridLayoutManager.getItemCount();
                                                       firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                                                       if (flag_scroll) {
                                                           AppLogger.e("flag-Scroll", flag_scroll + "");
                                                       } else {
                                                           if (loading) {
                                                               Log.e("flag-Loading", loading + "");
                                                               if (totalItemCount > previousTotal) {
                                                                   loading = false;
                                                                   previousTotal = totalItemCount;
                                                                   //Log.e("flag-IF", (totalItemCount > previousTotal) + "");
                                                               }
                                                           }
                                                           if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                                                               // End has been reached
                                                               // Do something
                                                               Log.e("flag-Loading_second_if", loading + "");
                                                               if (NetworkUtils.isNetworkConnected(MyStockAct.this)) {
                                                                   Log.e("total count", "--------------------" + page_count);
                                                                   page_count++;
                                                                   getMyStockProduct(customerId, loginResponse.getData().getGroupId(), String.valueOf(page_count), price, gold_purity, diamond_quality, diamond_shape, sku, availability, sort_by);
                                                               } else {
                                                                   //internet not connected
                                                                   AppLogger.e("connection", "-------internet connection is off");
                                                               }
                                                               loading = true;
                                                           }

                                                       }
                                                   }

                                               }

        );
    }

    @Override
    public void loadData() {
        myStockRecyclerAdapter = new MyStockRecyclerAdapter(MyStockAct.this, itemArrayList);
        recycleViewListing.setAdapter(myStockRecyclerAdapter);
        getMyStockProduct(customerId, loginResponse.getData().getGroupId(), String.valueOf(page_count), price, gold_purity, diamond_quality, diamond_shape, sku, availability, sort_by);
//        getSortFilter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linSortBy:
                View sheetView = getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
                mBottomSheetDialog.setContentView(sheetView);

                RecyclerView recycleViewSortBy = sheetView.findViewById(R.id.recycleViewSortBy);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyStockAct.this);
                recycleViewSortBy.setLayoutManager(linearLayoutManager);

                SortByMyStockRecyclerAdapter sortByListRecyclerAdapter = new SortByMyStockRecyclerAdapter(MyStockAct.this, sortByList);
                recycleViewSortBy.setAdapter(sortByListRecyclerAdapter);

                mBottomSheetDialog.show();

                break;

            case R.id.linFilterOrder:
                startNewActivity(FilterAct.class);
                break;

        }
    }

    private void getMyStockProduct(String categoryId, String groupId, String page, String price, String gold_purity, String diamonod_quality, String diamond_shape, String sku, String availability, String sort_by) {
        if (page_count == 1) {
            progressCenter.setVisibility(View.VISIBLE);
        } else {
            progressBottom.setVisibility(View.VISIBLE);
        }
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<MyStockItem> callApi = apiInterface.getMyStockProduct(categoryId, groupId, page, price, gold_purity, diamonod_quality, diamond_shape, sku, availability, sort_by);
        callApi.enqueue(new Callback<MyStockItem>() {
            @Override
            public void onResponse(@NonNull Call<MyStockItem> call, @NonNull Response<MyStockItem> response) {
                assert response.body() != null;
                if (page_count == 1) {
                    progressCenter.setVisibility(View.GONE);
                } else {
                    progressBottom.setVisibility(View.GONE);
                }
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        itemArrayList.addAll(response.body().getData());
                        myStockRecyclerAdapter.notifyDataSetChanged();
                    } else {
                        linNoData.setVisibility(View.GONE);
                        if (itemArrayList.isEmpty()) {
                            linFilter.setVisibility(View.GONE);
                            linNoData.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyStockItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
                linNoData.setVisibility(View.VISIBLE);
                if (page_count == 1) {
                    progressCenter.setVisibility(View.GONE);
                } else {
                    progressBottom.setVisibility(View.GONE);
                }
            }
        });
    }

 /*   public void getSortFilter() {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<FilterItem> callApi = apiInterface.setFilter();
        callApi.enqueue(new Callback<FilterItem>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<FilterItem> call, @NonNull Response<FilterItem> response) {
                AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
                assert response.body() != null;

                if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                    sortByList.addAll(response.body().getSortBy());
//                    linSortBy.setVisibility(View.VISIBLE);
//                    linFilter.setVisibility(View.VISIBLE);
                }else{
//                    linSortBy.setVisibility(View.GONE);
//                    linFilter.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(@NonNull Call<FilterItem> call, @NonNull Throwable t) {
            }

        });
    } */

    public void sortValueGetAndDialogClose(String value) {
        AppLogger.e("value", "-----------" + value);
        itemArrayList.clear();
        sort_by = value;
        page_count = 1;
        flag_scroll = false;
        previousTotal = 0; // The total number of items in the dataset after the last load
        loading = true; // True if we are still waiting for the last set of data to load.
        mBottomSheetDialog.dismiss();
        myStockRecyclerAdapter = new MyStockRecyclerAdapter(MyStockAct.this, itemArrayList);
        recycleViewListing.setAdapter(myStockRecyclerAdapter);
        getMyStockProduct(customerId, loginResponse.getData().getGroupId(), String.valueOf(page_count), price, gold_purity, diamond_quality, diamond_shape, sku, availability, sort_by);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (filterFlag == 1) {
            itemArrayList.clear();
            page_count = 1;
            flag_scroll = false;
            previousTotal = 0; // The total number of items in the dataset after the last load
            loading = true; // True if we are still waiting for the last set of data to load.

            for (Object key : mapFilter.keySet()) {
                String value = mapFilter.get(key);
                if ("price".equalsIgnoreCase((String) key)) {
                    price = value;
                } else if ("gold_purity".equalsIgnoreCase((String) key)) {
                    gold_purity = value;
                } else if ("diamond_quality".equalsIgnoreCase((String) key)) {
                    diamond_quality = value;
                } else if ("diamond_shape".equalsIgnoreCase((String) key)) {
                    diamond_shape = value;
                } else if ("sku".equalsIgnoreCase((String) key)) {
                    sku = value;
                } else if ("availability".equalsIgnoreCase((String) key)) {
                    availability = value;
                }
                myStockRecyclerAdapter = new MyStockRecyclerAdapter(MyStockAct.this, itemArrayList);
                recycleViewListing.setAdapter(myStockRecyclerAdapter);
                getMyStockProduct(customerId, loginResponse.getData().getGroupId(), String.valueOf(page_count), price, gold_purity, diamond_quality, diamond_shape, sku, availability, sort_by);

                AppLogger.e("data", "--------" + price + gold_purity + diamond_quality + diamond_shape + sku + availability);
            }
        }


    }

}
