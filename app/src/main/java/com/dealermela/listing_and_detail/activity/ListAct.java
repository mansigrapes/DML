package com.dealermela.listing_and_detail.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.listing_and_detail.adapter.ListingRecyclerAdapter;
import com.dealermela.listing_and_detail.adapter.SortByListRecyclerAdapter;
import com.dealermela.listing_and_detail.model.FilterItem;
import com.dealermela.listing_and_detail.model.ListingItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.NetworkUtils;
import com.dealermela.util.SharedPreferences;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ligl.android.widget.iosdialog.IOSDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;
import static com.dealermela.listing_and_detail.activity.FilterAct.filterFlag;
import static com.dealermela.listing_and_detail.activity.FilterAct.selectFilter;

public class ListAct extends DealerMelaBaseActivity implements View.OnClickListener {
    private RecyclerView recycleViewListing;
    private LinearLayout linSortBy, linFilter;
    private ListingRecyclerAdapter listingRecyclerAdapter;
    private List<ListingItem.Datum> itemArrayList;
    public static List<FilterItem.Datum> filterSelectItems = new ArrayList<>();
    //    public static List<FilterItem.Datum> filterItems = new ArrayList<>();
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
    private TextView tvCount,tvListCartCount;
    private List<FilterItem.SortBy> sortByList = new ArrayList<>();
    private BottomSheetDialog mBottomSheetDialog;
    private SharedPreferences sharedPreferences;

//    private String price = "", gold_purity = "", diamond_quality = "", diamond_shape = "", sku = "", availability = "", sort_by = "";

    private StringBuilder price = new StringBuilder();
    private StringBuilder gold_purity = new StringBuilder();
    private StringBuilder diamond_quality = new StringBuilder();
    private StringBuilder diamond_shape = new StringBuilder();
    private StringBuilder sku = new StringBuilder();
    private StringBuilder availability = new StringBuilder();
    private StringBuilder sort_by = new StringBuilder();

    private String id, name;
    private SwipeRefreshLayout swipeRefreshData;
    private ShimmerFrameLayout parentShimmerLayout;
    private FloatingActionButton fabDownload;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_list;
    }

    @Override
    public void init() {

//        closeOptionsMenu();        //Add for check cart count update or not 8/06/20

        id = getIntent().getStringExtra(AppConstants.ID);
        name = getIntent().getStringExtra(AppConstants.NAME);
        String checkList = getIntent().getStringExtra(AppConstants.bannerListCheck);

        AppLogger.e("data", "-------------" + id);

        if (checkList.equalsIgnoreCase("Banner")) {
            HashMap<String, String> hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("map");

            for (Object key : hashMap.keySet()) {
                String value = hashMap.get(key);
                if ("price".equalsIgnoreCase((String) key)) {
                    price.append(value);
                } else if ("gold_purity".equalsIgnoreCase((String) key)) {
                    gold_purity.append(value);
                } else if ("diamond_quality".equalsIgnoreCase((String) key)) {
                    diamond_quality.append(value);
                } else if ("diamond_shape".equalsIgnoreCase((String) key)) {
                    diamond_shape.append(value);
                } else if ("sku".equalsIgnoreCase((String) key)) {
                    sku.append(value);
                } else if ("availability".equalsIgnoreCase((String) key)) {
                    availability.append(value);
                }
                AppLogger.e("data print", "--------" + price + gold_purity + diamond_quality + diamond_shape + sku + availability);
            }
        }
        sharedPreferences = new SharedPreferences(ListAct.this);
        Gson gson = new Gson();
        loginResponse = gson.fromJson(sharedPreferences.getLoginData(), LoginResponse.class);
        itemArrayList = new ArrayList<>();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void initView() {
        bindToolBar(CommonUtils.capitalizeString(name));

        tvListCartCount = findViewById(R.id.cart_badge);
        linSortBy = findViewById(R.id.linSortBy);
        linFilter = findViewById(R.id.linFilterOrder);
        progressCenter = findViewById(R.id.progressCenter);
        progressBottom = findViewById(R.id.progressBottom);
        tvCount = findViewById(R.id.tvCount);

        gridLayoutManager = new GridLayoutManager(ListAct.this, 2);
        recycleViewListing = findViewById(R.id.recycleViewListing);
        recycleViewListing.setLayoutManager(gridLayoutManager);
        linNoData = findViewById(R.id.linNoData);
        swipeRefreshData = findViewById(R.id.swipeRefreshData);

        mBottomSheetDialog = new BottomSheetDialog(ListAct.this);
        parentShimmerLayout = findViewById(R.id.parentShimmerLayout);
        fabDownload = findViewById(R.id.fabDownload);
//        parentShimmerLayout.startShimmerAnimation();

        if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
            fabDownload.setVisibility(View.GONE);
        }

    }

    @Override
    public void postInitView() {
    }

    @Override
    public void addListener() {
        linSortBy.setOnClickListener(this);
        linFilter.setOnClickListener(this);

        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final StringBuilder stringBuilder = new StringBuilder();
                int count = 0;
                for (ListingItem.Datum productImg : listingRecyclerAdapter.itemArrayList) {
                    if (productImg.getDownloadFlag() == 0) {
                        stringBuilder.append(productImg.getEntityId()).append(",");
                        count++;
                    }
                }
                if (stringBuilder.toString().equals("")) {
                    CommonUtils.showWarningToast(ListAct.this, "Loaded Product is already downloaded, scroll down and download.");
                } else {
                    new IOSDialog.Builder(ListAct.this)
                            .setTitle("Download Product")
                            .setMessage("Are you sure want to add " + count + " products in download list?")
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    AppLogger.e("stringBuilder ids", "---------" + stringBuilder);
                                    addToDownloadProduct(stringBuilder.toString(), customerId);

                                }
                            })
                            .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }

                /*if (downloadItemArrayList.isEmpty()){
                    CommonUtils.showToast(ListAct.this,"Loaded Product is already downloaded, scroll down and download.");
                }else{
                    new IOSDialog.Builder(ListAct.this)
                            .setTitle("Download Product")
                            .setMessage("Are you sure want to  add "+ downloadItemArrayList.size() +" products in cart.?")
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    StringBuilder stringBuilder=new StringBuilder();


                                    for(ListingItem.ProductImg productImg: downloadItemArrayList){
                                        AppLogger.e("product ids","---------"+productImg.getEntityId());
                                        stringBuilder.append(productImg.getEntityId()).append(",");
                                    }

                                    AppLogger.e("stringBuilder ids","---------"+stringBuilder);
                                    addToDownloadProduct(stringBuilder.toString(),customerId);

                                }
                            })
                            .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }*/

            }
        });

        recycleViewListing.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                                   @Override
                                                   public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                                       super.onScrolled(recyclerView, dx, dy);
                                                       if (!sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                                                           if (dy > 0 ||dy<0 && fabDownload.isShown())
                                                           {
                                                               fabDownload.hide();
                                                           }
                                                       }

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
                                                               if (NetworkUtils.isNetworkConnected(ListAct.this)) {
                                                                   Log.e("total count", "--------------------" + page_count);
                                                                   page_count++;

                                                                   if (id.equalsIgnoreCase("search")) {
                                                                       linSortBy.setVisibility(View.GONE);
                                                                       linFilter.setVisibility(View.GONE);
                                                                       searchProduct(name, String.valueOf(page_count));
                                                                   } else {

                                                                       if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                                                                           getCategoryProduct(id, "", String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString());
                                                                       } else {
                                                                           getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString());
                                                                       }
                                                                   }

                                                               } else {
                                                                   //internet not connected
                                                                   AppLogger.e("connection", "-------internet connection is off");
                                                               }
                                                               loading = true;
                                                           }
                                                       }
                                                   }

                                                   @Override
                                                   public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                                       if (!sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                                                           if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                                               fabDownload.show();
                                                           }
                                                       }
                                                       super.onScrollStateChanged(recyclerView, newState);
                                                   }
                                               }
        );

        swipeRefreshData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData() {
        swipeRefreshData.setRefreshing(false);
    }

    @Override
    public void loadData() {
        listingRecyclerAdapter = new ListingRecyclerAdapter(ListAct.this, itemArrayList);
        recycleViewListing.setAdapter(listingRecyclerAdapter);

        if (id.equalsIgnoreCase("search")) {
            linSortBy.setVisibility(View.GONE);
            linFilter.setVisibility(View.GONE);
            searchProduct(name, String.valueOf(page_count));
            AppLogger.e("NameInSearch","------"+name);
            AppLogger.e("Page","---------"+String.valueOf(page_count));
        } else {
            if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                getCategoryProduct(id, "", String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString());
            } else {
                getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString());
            }
            getSortFilter();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linSortBy:
                View sheetView = getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
                mBottomSheetDialog.setContentView(sheetView);

                RecyclerView recycleViewSortBy = sheetView.findViewById(R.id.recycleViewSortBy);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListAct.this);
                recycleViewSortBy.setLayoutManager(linearLayoutManager);

                SortByListRecyclerAdapter sortByListRecyclerAdapter = new SortByListRecyclerAdapter(ListAct.this, sortByList);
                recycleViewSortBy.setAdapter(sortByListRecyclerAdapter);

                mBottomSheetDialog.show();

                break;

            case R.id.linFilterOrder:
                Intent intent = new Intent(ListAct.this, FilterAct.class);
//                filterSelectItems = filterItems;
//                Gson gson = new Gson();
//                String data = gson.toJson(filterItems);
//                intent.putExtra(AppConstants.NAME, data);
                startActivity(intent);
                break;

        }
    }

    private void getCategoryProduct(String categoryId, String groupId, String page, String price, String gold_purity, String diamonod_quality, String diamond_shape, String sku, String availability, String sort_by) {
        if (page_count == 1) {
//            progressCenter.setVisibility(View.VISIBLE);
        } else {
            progressBottom.setVisibility(View.VISIBLE);
        }
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<ListingItem> callApi = apiInterface.getCategoryProduct(customerId, categoryId, groupId, page, price, gold_purity, diamonod_quality, diamond_shape, sku, availability, sort_by);
        callApi.enqueue(new Callback<ListingItem>() {
            @Override
            public void onResponse(@NonNull Call<ListingItem> call, @NonNull Response<ListingItem> response) {
                assert response.body() != null;
                if (page_count == 1) {
                    progressCenter.setVisibility(View.GONE);
                } else {
                    progressBottom.setVisibility(View.GONE);
                }
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
//                        parentShimmerLayout.stopShimmerAnimation();

                        linNoData.setVisibility(View.GONE);         //added for coming back from Reset filter & data display back
                        linSortBy.setVisibility(View.VISIBLE);
                        parentShimmerLayout.setVisibility(View.GONE);
                        recycleViewListing.setVisibility(View.VISIBLE);
                        itemArrayList.addAll(response.body().getData());
                        listingRecyclerAdapter.notifyDataSetChanged();

                    } else {

                        if (itemArrayList.isEmpty()) {
                            linNoData.setVisibility(View.VISIBLE);
                            linSortBy.setVisibility(View.GONE);
                            linFilter.setVisibility(View.GONE);
                            parentShimmerLayout.setVisibility(View.GONE);
                            fabDownload.hide();
                            if(filterFlag == 1){
                                linFilter.setVisibility(View.VISIBLE);
                            }
                        } else {
                            linNoData.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListingItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
                linNoData.setVisibility(View.VISIBLE);
                linSortBy.setVisibility(View.GONE);
                linFilter.setVisibility(View.GONE);
                parentShimmerLayout.setVisibility(View.GONE);

                if (page_count == 1) {
                    progressCenter.setVisibility(View.GONE);
                } else {
                    progressBottom.setVisibility(View.GONE);
                }
            }
        });
    }

    public void getSortFilter() {
        linSortBy.setVisibility(View.GONE);
        linFilter.setVisibility(View.GONE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<FilterItem> callApi = apiInterface.setFilter();
        callApi.enqueue(new Callback<FilterItem>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<FilterItem> call, @NonNull Response<FilterItem> response) {
                AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
                assert response.body() != null;

                if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                    filterSelectItems.addAll(response.body().getData());
                    sortByList.addAll(response.body().getSortBy());
//                    filterItems.addAll(response.body().getData());
                    linSortBy.setVisibility(View.VISIBLE);
                    linFilter.setVisibility(View.VISIBLE);
                } else {
                }
            }

            @Override
            public void onFailure(@NonNull Call<FilterItem> call, @NonNull Throwable t) {
            }
        });
    }

    public void sortValueGetAndDialogClose(String value,int position) {
        AppLogger.e("value", "-----------" + value);

        for (int i=0;i<sortByList.size();i++){
            if (position == i){
                sortByList.get(i).setSelected(true);
            }else{
                sortByList.get(i).setSelected(false);
            }
        }
        itemArrayList.clear();
        parentShimmerLayout.setVisibility(View.VISIBLE);
        linNoData.setVisibility(View.GONE);
        sort_by.setLength(0);
        sort_by.append(value);
        page_count = 1;
        flag_scroll = false;
        previousTotal = 0; // The total number of items in the dataset after the last load
        loading = true; // True if we are still waiting for the last set of data to load.
        mBottomSheetDialog.dismiss();
        listingRecyclerAdapter = new ListingRecyclerAdapter(ListAct.this, itemArrayList);
        recycleViewListing.setAdapter(listingRecyclerAdapter);
        if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
            AppLogger.e("sort by if", "-----------" + sort_by);
            getCategoryProduct(id, "", String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString());
        } else {
            AppLogger.e("sort by else ", "-----------" + sort_by);
            getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //filter count
        if (!selectFilter.isEmpty()) {
            AppLogger.e("count", "-------------" + selectFilter.size());
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText("" + selectFilter.size());
        } else {
            tvCount.setVisibility(View.GONE);
        }

//        getCount();

        if (filterFlag == 1) {
            tvCount.setVisibility(View.VISIBLE);
            parentShimmerLayout.setVisibility(View.VISIBLE);
            linNoData.setVisibility(View.GONE);
            itemArrayList.clear();
            page_count = 1;
            flag_scroll = false;
            previousTotal = 0; // The total number of items in the dataset after the last load
            loading = true; // True if we are still waiting for the last set of data to load.

            price.setLength(0);
            gold_purity.setLength(0);
            diamond_quality.setLength(0);
            diamond_shape.setLength(0);
            sku.setLength(0);
            availability.setLength(0);
            sort_by.setLength(0);

            for (int i = 0; i < filterSelectItems.size(); i++) {
                if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("price")) {
                    for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                        if (filterSelectItems.get(i).getOptionData().get(j).isSelected()) {
                            price.append(filterSelectItems.get(i).getOptionData().get(j).getValue()).append("|");
                        }
                    }
                } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("gold_purity")) {
                    for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                        if (filterSelectItems.get(i).getOptionData().get(j).isSelected()) {
                            gold_purity.append(filterSelectItems.get(i).getOptionData().get(j).getValue()).append("|");
                        }
                    }
                } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_quality")) {
                    for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                        if (filterSelectItems.get(i).getOptionData().get(j).isSelected()) {
                            diamond_quality.append(filterSelectItems.get(i).getOptionData().get(j).getValue()).append("|");
                        }
                    }
                } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_shape")) {
                    for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                        if (filterSelectItems.get(i).getOptionData().get(j).isSelected()) {
                            diamond_shape.append(filterSelectItems.get(i).getOptionData().get(j).getValue()).append("|");
                        }
                    }
                }
            }

            if (price.length() != 0) {
                price.setLength(price.length() - 1);
            }
            if (gold_purity.length() != 0) {
                gold_purity.setLength(gold_purity.length() - 1);
            }
            if (diamond_quality.length() != 0) {
                diamond_quality.setLength(diamond_quality.length() - 1);
            }
            if (diamond_shape.length() != 0) {
                diamond_shape.setLength(diamond_shape.length() - 1);
            }
            if (sku.length() != 0) {
                sku.setLength(sku.length() - 1);
            }
            if (availability.length() != 0) {
                availability.setLength(availability.length() - 1);
            }
            if (sort_by.length() != 0) {
                sort_by.setLength(sort_by.length() - 1);
            }

//            price.setLength(price.length() - 1);
//            gold_purity.setLength(gold_purity.length() - 1);
//            diamond_quality.setLength(diamond_quality.length() - 1);
//            diamond_shape.setLength(diamond_shape.length() - 1);
//            sku.setLength(sku.length() - 1);
//            availability.setLength(availability.length() - 1);
//            sort_by.setLength(sort_by.length() - 1);


            listingRecyclerAdapter = new ListingRecyclerAdapter(ListAct.this, itemArrayList);
            recycleViewListing.setAdapter(listingRecyclerAdapter);
            if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                getCategoryProduct(id, "", String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString());
            } else {
                getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString());
            }
            AppLogger.e(" full string data", "--------" + price + gold_purity + diamond_quality + diamond_shape + sku + availability);

/*
            for (Object key : mapFilter.keySet()) {
                String value = mapFilter.get(key);
                if ("price".equalsIgnoreCase((String) key)) {
                    price = value;
                    AppLogger.e("price", "--------" + price);
                } else if ("gold_purity".equalsIgnoreCase((String) key)) {
                    gold_purity = value;
                    AppLogger.e("gold_purity", "--------" + gold_purity);
                } else if ("diamond_quality".equalsIgnoreCase((String) key)) {
                    diamond_quality = value;
                    AppLogger.e("diamond_quality", "--------" + diamond_quality);
                } else if ("diamond_shape".equalsIgnoreCase((String) key)) {
                    diamond_shape = value;
                    AppLogger.e("diamond_shape", "--------" + diamond_shape);
                } else if ("sku".equalsIgnoreCase((String) key)) {
                    sku = value;
                    AppLogger.e("sku", "--------" + sku);
                } else if ("availability".equalsIgnoreCase((String) key)) {
                    availability = value;
                    AppLogger.e("availability", "--------" + availability);
                }
                listingRecyclerAdapter = new ListingRecyclerAdapter(ListAct.this, itemArrayList);
                recycleViewListing.setAdapter(listingRecyclerAdapter);
                if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                    getCategoryProduct(id, "", String.valueOf(page_count), price, gold_purity, diamond_quality, diamond_shape, sku, availability, sort_by);
                } else {
                    getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price, gold_purity, diamond_quality, diamond_shape, sku, availability, sort_by);
                }
                AppLogger.e(" full string data", "--------" + price + gold_purity + diamond_quality + diamond_shape + sku + availability);
a
            }*/
        }else {
            tvCount.setVisibility(View.GONE);

            price.setLength(0);
            gold_purity.setLength(0);
            diamond_quality.setLength(0);
            diamond_shape.setLength(0);
            sku.setLength(0);
            availability.setLength(0);
            sort_by.setLength(0);

            if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                getCategoryProduct(id, "", String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString());
            } else {
                getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString());
            }
        }
    }

//    private void getCount() {
//        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
//        Call<JsonObject> callApi = apiInterface.getCartDownloadCount(customerId);
//        callApi.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                assert response.body() != null;
//                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
//                try {
//                    JSONObject jsonObject = new JSONObject(response.body().toString());
//                    if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
//
//                        if (jsonObject.getInt("total_qty") != 0) {
//                            tvListCartCount.setVisibility(View.VISIBLE);
//                            cartCount = jsonObject.getInt("total_qty");
//                            tvListCartCount.setText(String.valueOf(jsonObject.getInt("total_qty")));
//                        } else {
//                            tvListCartCount.setVisibility(View.GONE);
//                        }
//
//                    } else {
//                        tvListCartCount.setVisibility(View.GONE);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                AppLogger.e("error", "------------" + t.getMessage());
//            }
//        });
//    }

    private void searchProduct(String searchTerm, String page) {
        if (page_count == 1) {
//            progressCenter.setVisibility(View.VISIBLE);
        } else {
            progressBottom.setVisibility(View.VISIBLE);
        }
        AppLogger.e("SearchTerm","----------"+searchTerm);
        AppLogger.e("Page","----------"+page);

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<ListingItem> callApi = apiInterface.searchProduct(searchTerm, page);
        callApi.enqueue(new Callback<ListingItem>() {
            @Override
            public void onResponse(@NonNull Call<ListingItem> call, @NonNull Response<ListingItem> response) {
                assert response.body() != null;
                parentShimmerLayout.setVisibility(View.GONE);
                recycleViewListing.setVisibility(View.VISIBLE);
                if (page_count == 1) {
//                    progressCenter.setVisibility(View.GONE);
                } else {
                    progressBottom.setVisibility(View.GONE);
                }
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        itemArrayList.addAll(response.body().getData());
                        listingRecyclerAdapter.notifyDataSetChanged();
                        linNoData.setVisibility(View.GONE);
                    } else {
                        linNoData.setVisibility(View.GONE);
                        if (itemArrayList.isEmpty()) {
                            linNoData.setVisibility(View.VISIBLE);
                            progressCenter.setVisibility(View.GONE);
                            progressBottom.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListingItem> call, @NonNull Throwable t) {
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

    private void addToDownloadProduct(String productsIds, String customerId) {
        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.addToDownloadProduct(productsIds, customerId);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                AppLogger.e("response", "----------" + response.body());
                assert response.body() != null;
                hideProgressDialog();
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                            listingRecyclerAdapter.updateDownloadFlag();
                            CommonUtils.showSuccessToast(ListAct.this,"All products successfully added into download list");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
                hideProgressDialog();
            }
        });
    }
//
//    //Option menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.detail_menu, menu);
//        return false;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_search) {
//            return true;
//        }
//        if (id == R.id.action_cart) {
//            return true;
//        }
//        if(id == R.id.cart_badge){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
