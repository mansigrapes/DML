package com.dealermela.listing_and_detail.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;

import com.dealermela.retrofit.ApiInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.dialog.MaintenanceDialogClass;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.listing_and_detail.adapter.ListingRecyclerAdapter;
import com.dealermela.listing_and_detail.adapter.SortByListRecyclerAdapter;
import com.dealermela.listing_and_detail.model.FilterItem;
import com.dealermela.listing_and_detail.model.ListingItem;
import com.dealermela.retrofit.APIClient;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.download.adapter.DownloadProductAdapter.downloadlistback;
import static com.dealermela.home.activity.MainActivity.GroupId;
import static com.dealermela.home.activity.MainActivity.customerId;

import static com.dealermela.listing_and_detail.activity.FilterAct.filterFlag;
import static com.dealermela.listing_and_detail.activity.FilterAct.mapFilter;
import static com.dealermela.listing_and_detail.activity.FilterAct.pagecountflag;
import static com.dealermela.listing_and_detail.activity.FilterAct.skuFilterString;
import static com.dealermela.listing_and_detail.adapter.SortByListRecyclerAdapter.sortby_latest_flag;
import static com.dealermela.other.activity.NewSearchAct.searchbackflag;
//import static com.dealermela.other.activity.NewSearchAct.searchbackflag;

public class ListAct extends DealerMelaBaseActivity implements View.OnClickListener {
    private RecyclerView recycleViewListing;
    private LinearLayout linSortBy, linFilter;
    private ListingRecyclerAdapter listingRecyclerAdapter;
//    private List<ListingItem.Datum> itemArrayList;
    public static List<ListingItem.Datum> itemArrayList;
    public static ArrayList<FilterItem.Datum> filterSelectItems = new ArrayList<>();
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
    private TextView tvCount,tvListCartCount,tvSortbyDot,tvFilter,tvSortby;
    private List<FilterItem.SortBy> sortByList = new ArrayList<>();
    private BottomSheetDialog mBottomSheetDialog;
    private SharedPreferences sharedPreferences;
    private ImageView sortIcon,filterIcon;

//    private String price = "", gold_purity = "", diamond_quality = "", diamond_shape = "", sku = "", availability = "", sort_by = "";

    private StringBuilder price = new StringBuilder();
    private StringBuilder gold_purity = new StringBuilder();
    private StringBuilder diamond_quality = new StringBuilder();
    private StringBuilder diamond_shape = new StringBuilder();
    private StringBuilder sku = new StringBuilder();

    private StringBuilder availability = new StringBuilder();
    private StringBuilder sort_by = new StringBuilder();
    private StringBuilder subcategory = new StringBuilder();

    private int latestPosition;
    public static String id,Subcategoryid,categoryid;
    private String  name, group_id;
    private SwipeRefreshLayout swipeRefreshData;
    private ShimmerFrameLayout parentShimmerLayout;
    private FloatingActionButton fabDownload;

    private Button cancelandclear;
    private int totalproduct;
    private double value = 0;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_list;
    }

    @Override
    public void init() {

        if(getIntent().getStringExtra(AppConstants.ID) != null) {
            id = getIntent().getStringExtra(AppConstants.ID);
        }

        if(getIntent().getStringExtra(AppConstants.SubCategory_ID) != null) {
            Subcategoryid = getIntent().getStringExtra(AppConstants.SubCategory_ID);
        }else {
            Subcategoryid = "";
        }

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

        if(id.equalsIgnoreCase(Subcategoryid)){
//             String[] mainName = name.split(" ");
//            bindToolBar(CommonUtils.capitalizeString(mainName[2]));
            bindToolBar(CommonUtils.capitalizeString(getIntent().getStringExtra(AppConstants.MAIN_NAME)));
        }else {
            bindToolBar(CommonUtils.capitalizeString(name));
        }

        sortIcon = findViewById(R.id.sortIcon);
        filterIcon = findViewById(R.id.filterIcon);
        tvFilter = findViewById(R.id.tvFilter);
        tvSortby = findViewById(R.id.tvSortby);
        tvListCartCount = findViewById(R.id.cart_badge);
        linSortBy = findViewById(R.id.linSortBy);
        linFilter = findViewById(R.id.linFilterOrder);
        progressCenter = findViewById(R.id.progressCenter);
        progressBottom = findViewById(R.id.progressBottom);
        tvCount = findViewById(R.id.tvCount);
        tvSortbyDot = findViewById(R.id.tvSortByDot);

        gridLayoutManager = new GridLayoutManager(ListAct.this, 2);
        recycleViewListing = findViewById(R.id.recycleViewListing);
        recycleViewListing.setLayoutManager(gridLayoutManager);
        linNoData = findViewById(R.id.searchNoData);
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
        setupBadge();
    }

    @Override
    public void addListener() {
        linSortBy.setOnClickListener(this);
        linFilter.setOnClickListener(this);
//        cancelandclear.setOnClickListener(this);

        setupBadge();

//        if(filterFlag == 1){
//            AppLogger.e("When return back from detail page if any filter is applied ","value"+ filterFlag);
//        }else{
//            AppLogger.e("FilterFlag","--------" + filterFlag);
//        }

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
                    CommonUtils.showWarningToast(ListAct.this, "Loaded product is already added in list. Scroll down and add another products");
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

    /*    recycleViewListing.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                                                       linNoData.setVisibility(View.GONE);
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
                                                               if(id.equalsIgnoreCase("search")){
                                                                   if(itemArrayList.size() > 1){
                                                                       fabDownload.show();
                                                                   }else {
                                                                       fabDownload.hide();
                                                                   }
                                                               }
                                                               if(filterFlag == 1){
                                                                   if(itemArrayList.size() == 1){
                                                                       fabDownload.hide();
                                                                   }else if(itemArrayList.isEmpty()){
                                                                       fabDownload.hide();
                                                                   }
                                                               }
                                                           }
                                                       }else {
                                                           if(id.equalsIgnoreCase("search")){
                                                               fabDownload.hide();
                                                           }
                                                       }
                                                       super.onScrollStateChanged(recyclerView, newState);
                                                   }
                                               }
        );  */

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
//                                                                   page_count++;

                                                                   if (id.equalsIgnoreCase("search")) {
                                                                       value = (double) totalproduct / 12 ;
                                                                       AppLogger.e("SearchTotalPage","-------"+value);
                                                                       if(value > page_count){
                                                                           page_count++;
                                                                           linSortBy.setVisibility(View.GONE);
                                                                           linFilter.setVisibility(View.GONE);
                                                                           linNoData.setVisibility(View.GONE);
                                                                           searchProduct(name, String.valueOf(page_count));
                                                                       }else {
                                                                           linSortBy.setVisibility(View.GONE);
                                                                           linFilter.setVisibility(View.GONE);
                                                                           linNoData.setVisibility(View.GONE);
//                                                                           searchProduct(name, String.valueOf(page_count));
                                                                       }

                                                                   } else {
                                                                       page_count++;
                                                                       if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                                                                           getCategoryProduct(id, "", String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
                                                                       } else {
                                                                           getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
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
                                                               if(id.equalsIgnoreCase("search")){
                                                                   if(itemArrayList.size() > 1){
                                                                       fabDownload.show();
                                                                   }else {
                                                                       fabDownload.hide();
                                                                   }
                                                               }
                                                               if(filterFlag == 1){
                                                                   if(itemArrayList.size() == 1){
                                                                       fabDownload.hide();
                                                                   }else if(itemArrayList.isEmpty()){
                                                                       fabDownload.hide();
                                                                   }
                                                               }
                                                           }
                                                       }else {
                                                           if(id.equalsIgnoreCase("search")){
                                                               fabDownload.hide();
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

        if(NetworkUtils.isNetworkConnected(ListAct.this)) {

            listingRecyclerAdapter = new ListingRecyclerAdapter(ListAct.this, itemArrayList);
            recycleViewListing.setAdapter(listingRecyclerAdapter);

            if (id.equalsIgnoreCase("search")) {
                linSortBy.setVisibility(View.GONE);
                linFilter.setVisibility(View.GONE);
                linNoData.setVisibility(View.GONE);
                fabDownload.hide();
                //Add following 3 flag for scrolling the listing when we scroll the page Otherwise not scrolled.
                flag_scroll = false;  //Previous this flag set to false and when search time result display two times in single page
                previousTotal = 0; // The total number of items in the dataset after the last load
                loading = true; // True if we are still waiting for the last set of data to load.
                searchProduct(name, String.valueOf(page_count));
                AppLogger.e("NameInSearch", "------" + name);
                AppLogger.e("Page", "---------" + String.valueOf(page_count));
            } else {
                // Apply this condition for add subcategory Filter option Only at Main Category products Not in subcategory listing
                if (Subcategoryid.isEmpty()) {
                    //Pass MainCategoryId for subcategory Filter option
                    getSortFilter(id);
                } else {
                    //If on subcategorylisting No Filter option for subcategory
                    if (Subcategoryid.equalsIgnoreCase(id)) {
                        getSortFilter(id);
                    } else {
                        categoryid = "";
                        getSortFilter(categoryid);
                    }
                }
//            getSortFilter();
//            getCount();  //changed on 10/06 before call this method in onResume Function
                sort_by.append("sort_by_latest");
                if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                    getCategoryProduct(id, "", String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
                } else {
                    getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
                }
                setupBadge();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linSortBy:

                View sheetView = getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
                mBottomSheetDialog.setContentView(sheetView);
                cancelandclear = mBottomSheetDialog.findViewById(R.id.cancelandclear);
                cancelandclear.setOnClickListener(this);

                final RecyclerView recycleViewSortBy = sheetView.findViewById(R.id.recycleViewSortBy);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListAct.this);
                recycleViewSortBy.setLayoutManager(linearLayoutManager);

                SortByListRecyclerAdapter sortByListRecyclerAdapter = new SortByListRecyclerAdapter(ListAct.this, sortByList);
                recycleViewSortBy.setAdapter(sortByListRecyclerAdapter);

//                cancelandclear.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        AppLogger.e("Button called","----");
//                        mBottomSheetDialog.dismiss();
////                        SortByListRecyclerAdapter sortByListRecyclerAdapter = new SortByListRecyclerAdapter(ListAct.this, sortByList);
////                        recycleViewSortBy.setAdapter(sortByListRecyclerAdapter);
//                    }
//                });

                mBottomSheetDialog.show();

                break;

            case R.id.linFilterOrder:
                Intent intent = new Intent(ListAct.this, FilterAct.class);
//                filterSelectItems = filterItems;
//                Gson gson = new Gson();
//                String data = gson.toJson(filterItems);
//                intent.putExtra(AppConstants.NAME, data);
                intent.putExtra(AppConstants.NAME,id);
                intent.putExtra(AppConstants.SubCategory_ID,Subcategoryid);
                startActivity(intent);
                break;

            case R.id.cancelandclear:
                sortbyDialog();
//                SortByListRecyclerAdapter sortByListRecyclerAdapter = new SortByListRecyclerAdapter(ListAct.this, sortByList);
//                recycleViewSortBy.setAdapter(sortByListRecyclerAdapter);
                break;
        }
    }

    private void sortbyDialog() {
        String value = "";
        AppLogger.e("Button called","----");

        if(sort_by.toString().equalsIgnoreCase("sort_by_latest")){
            tvSortbyDot.setVisibility(View.GONE);
        }else {
            for (int i=0;i<sortByList.size();i++)
            {
                if(sortByList.get(i).getValue().equalsIgnoreCase("sort_by_latest")){
                    sortByList.get(i).setSelected(true);
                }else {
                    sortByList.get(i).setSelected(false);
                }
            }
            tvSortbyDot.setVisibility(View.GONE);
        }

        parentShimmerLayout.setVisibility(View.VISIBLE);
        linNoData.setVisibility(View.GONE);
        sort_by.setLength(0);
//        sort_by.append(value);
        sort_by.append("sort_by_latest");
        itemArrayList.clear();
        page_count = 1;
        flag_scroll = false;
        previousTotal = 0; // The total number of items in the dataset after the last load
        loading = true; // True if we are still waiting for the last set of data to load.

//        tvSortbyDot.setVisibility(View.GONE);  //Display dot When sorting is applied

        listingRecyclerAdapter = new ListingRecyclerAdapter(ListAct.this, itemArrayList);
        recycleViewListing.setAdapter(listingRecyclerAdapter);
        if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
            AppLogger.e("sort by if", "-----------" + sort_by);
            getCategoryProduct(id, "", String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
        } else {
            AppLogger.e("sort by else ", "-----------" + sort_by);
            getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
        }
        mBottomSheetDialog.dismiss();
    }

    private void getCategoryProduct(final String categoryId, String groupId, String page, String price, String gold_purity, String diamonod_quality, String diamond_shape, String sku, String availability, String sort_by, String subcategories) {
        if (page_count == 1) {
//            progressCenter.setVisibility(View.VISIBLE);
        } else {
            progressBottom.setVisibility(View.VISIBLE);
        }
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<ListingItem> callApi = apiInterface.getCategoryProduct(customerId, categoryId, groupId, page, price, gold_purity, diamonod_quality, diamond_shape, sku, availability, sort_by, subcategories, Subcategoryid);
        callApi.enqueue(new Callback<ListingItem>() {
            @SuppressLint("ResourceType")
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
                            linSortBy.setEnabled(true);
                            tvFilter.setTextAppearance(ListAct.this, R.attr.inActive_filer_color);
                            tvSortby.setTextAppearance(ListAct.this, R.attr.inActive_filer_color);
                            tvSortby.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                            tvFilter.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                            sortIcon.setColorFilter(getResources().getColor(R.color.filter_icon_color));
                            filterIcon.setColorFilter(getResources().getColor(R.color.filter_icon_color));
                            parentShimmerLayout.setVisibility(View.GONE);
                            recycleViewListing.setVisibility(View.VISIBLE);
                            itemArrayList.addAll(response.body().getData());
                            listingRecyclerAdapter.notifyDataSetChanged();

                            if (!sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                                if(itemArrayList.size() == 1){    //Apply this condition bcz in only 1 product in Filter result it give bulkdownload icon so For Now when user apply filter & if only 1 result found than hide BulkDownload option
                                    fabDownload.hide();
                                }
                            }
                        } else {
                            if (itemArrayList.isEmpty()) {
                                linNoData.setVisibility(View.VISIBLE);
                                linSortBy.setEnabled(false);
                                linFilter.setEnabled(false);
                                tvFilter.setTextColor(getResources().getColor(R.color.in_active_item_color));
                                tvSortby.setTextColor(getResources().getColor(R.color.in_active_item_color));
                                sortIcon.setColorFilter(getResources().getColor(R.color.in_active_item_color));
                                filterIcon.setColorFilter(getResources().getColor(R.color.in_active_item_color));
                                parentShimmerLayout.setVisibility(View.GONE);
                                fabDownload.hide();
                                if (filterFlag == 1) {
                                    linFilter.setEnabled(true);
                                    linSortBy.setEnabled(false);
                                    tvFilter.setTextColor(getResources().getColor(R.color.black));
                                    tvFilter.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
//                                    tvFilter.setTextAppearance(ListAct.this, R.attr.inActive_filer_color);
                                    sortIcon.setColorFilter(getResources().getColor(R.color.in_active_item_color));
                                    filterIcon.setColorFilter(getResources().getColor(R.color.filter_icon_color));
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
                linSortBy.setEnabled(false);
                linFilter.setEnabled(false);
                tvFilter.setTextColor(getResources().getColor(R.color.in_active_item_color));
                tvSortby.setTextColor(getResources().getColor(R.color.in_active_item_color));
                sortIcon.setColorFilter(getResources().getColor(R.color.in_active_item_color));
                filterIcon.setColorFilter(getResources().getColor(R.color.in_active_item_color));
                parentShimmerLayout.setVisibility(View.GONE);

                if (page_count == 1) {
                    progressCenter.setVisibility(View.GONE);
                } else {
                    progressBottom.setVisibility(View.GONE);
                }

                MaintenanceDialogClass dialogClass = new MaintenanceDialogClass(ListAct.this);
                dialogClass.show();
                Objects.requireNonNull(dialogClass.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Objects.requireNonNull(dialogClass.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });
    }

    public void getSortFilter(String categoryid) {
        linSortBy.setVisibility(View.GONE);
        linFilter.setVisibility(View.GONE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<FilterItem> callApi = apiInterface.setFilter(categoryid);
        callApi.enqueue(new Callback<FilterItem>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<FilterItem> call, @NonNull Response<FilterItem> response) {
                AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
                assert response.body() != null;

                if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                    if(filterSelectItems.isEmpty()){
                        if(id.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || id.equalsIgnoreCase(AppConstants.NECKLACES_SETS_ID)) {
////                           filterSelectItems.add(0,response.body().getData().get(4));
////                             filterSelectItems.addAll(response.body().getData(4));
                            filterSelectItems.addAll(response.body().getData());

                            filterSelectItems.remove(0);
                            filterSelectItems.remove(0);
                            filterSelectItems.remove(0);
                            filterSelectItems.remove(0);
//                            filterSelectItems.remove(0);
                        }else {
                            filterSelectItems.addAll(response.body().getData());
                        }
//                        filterSelectItems.addAll(response.body().getData());
                    }else {
                        if(id.equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID) || id.equalsIgnoreCase(AppConstants.NECKLACES_SETS_ID)) {
////                           filterSelectItems.add(0,response.body().getData().get(4));
////                             filterSelectItems.addAll(response.body().getData(4));
                            filterSelectItems.clear();
                            filterSelectItems.addAll(response.body().getData());

                            filterSelectItems.remove(0);
                            filterSelectItems.remove(0);
                            filterSelectItems.remove(0);
                            filterSelectItems.remove(0);
//                            filterSelectItems.remove(0);
                        }else {
                            filterSelectItems.clear();
                            filterSelectItems.addAll(response.body().getData());
                        }
                    }
                    sortByList.addAll(response.body().getSortBy());
                    for(int j=0;j<sortByList.size();j++){
                        if(sortByList.get(j).getValue().equalsIgnoreCase("sort_by_latest")){
                            latestPosition = j;
                        }
                    }
                    sortValueGetAndDialogClose("sort_by_latest",latestPosition);  //Bydefault when page load latest option selected in sortby popup

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

        if(mBottomSheetDialog.isShowing()){
            mBottomSheetDialog.dismiss();
        }

        sort_by.setLength(0);
        sort_by.append(value);

        if(sort_by.toString().equalsIgnoreCase("sort_by_latest")){
            tvSortbyDot.setVisibility(View.GONE);  //When page load Bydefault sortbylatest display selected but dont display dot
            if(sortby_latest_flag == 1){
                sortby_latest_flag = 0;
                itemArrayList.clear();
                parentShimmerLayout.setVisibility(View.VISIBLE);
                linNoData.setVisibility(View.GONE);
                page_count = 1;
                flag_scroll = false;
                previousTotal = 0; // The total number of items in the dataset after the last load
                loading = true; // True if we are still waiting for the last set of data to load.
                listingRecyclerAdapter = new ListingRecyclerAdapter(ListAct.this, itemArrayList);
                recycleViewListing.setAdapter(listingRecyclerAdapter);
                if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                    AppLogger.e("sort by if", "-----------" + sort_by);
                    getCategoryProduct(id, "", String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
                } else {
                    AppLogger.e("sort by else ", "-----------" + sort_by);
                    getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
                }
            }else {
            }
        }else {
            itemArrayList.clear();
            parentShimmerLayout.setVisibility(View.VISIBLE);
            linNoData.setVisibility(View.GONE);
            page_count = 1;
            flag_scroll = false;
            previousTotal = 0; // The total number of items in the dataset after the last load
            loading = true; // True if we are still waiting for the last set of data to load.
            tvSortbyDot.setVisibility(View.VISIBLE);  //Only Display dot When sorting is applied
            listingRecyclerAdapter = new ListingRecyclerAdapter(ListAct.this, itemArrayList);
            recycleViewListing.setAdapter(listingRecyclerAdapter);
            if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                AppLogger.e("sort by if", "-----------" + sort_by);
                getCategoryProduct(id, "", String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
            } else {
                AppLogger.e("sort by else ", "-----------" + sort_by);
                getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //filter count
//        if (!selectFilter.isEmpty()) {
//            AppLogger.e("count", "-------------" + selectFilter.size());
//            tvCount.setVisibility(View.VISIBLE);
//            tvCount.setText("" + selectFilter.size());
//        } else {
//            tvCount.setVisibility(View.GONE);
//        }
//        setupBadge();

       if(NetworkUtils.isNetworkConnected(ListAct.this)) {

           invalidateOptionsMenu();  // Adding this for update Cart Counting on back pressed from Detail / Cart Screen

//        flag_scroll = false;
//        previousTotal = 0; // The total number of items in the dataset after the last load
//        loading = true; // True if we are still waiting for the last set of data to load.

//           if(id.equalsIgnoreCase("search")){
//                if (getIntent().getStringExtra(AppConstants.ID) != null) {
//                   id = getIntent().getStringExtra(AppConstants.ID);
//               }
//           }

           if (filterFlag == 1 ) {

               if (getIntent().getStringExtra(AppConstants.ID) != null) {
                   id = getIntent().getStringExtra(AppConstants.ID);
               }

               //Want to change bcz when applied filter then goes to search & there on resume method first this api calls so give no data msg then after search API calls which should not happens

               if(!id.equalsIgnoreCase("search")) {

                   tvCount.setVisibility(View.VISIBLE);
                   parentShimmerLayout.setVisibility(View.VISIBLE);
                   linNoData.setVisibility(View.GONE);
//            itemArrayList.clear();
//            page_count = 1;
                   flag_scroll = false;
                   previousTotal = 0; // The total number of items in the dataset after the last load
                   loading = true; // True if we are still waiting for the last set of data to load.

                   price.setLength(0);
                   gold_purity.setLength(0);
                   diamond_quality.setLength(0);
                   diamond_shape.setLength(0);
                   sku.setLength(0);
                   availability.setLength(0);
                   subcategory.setLength(0);
//            sort_by.setLength(0);

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
                       } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("sku")) {
//                    sku =
//                    if (mapFilter.containsKey(paramKey)) {
//                        String key = mapFilter.get(paramKey);
//                        sku.append(key);
//                    }

                           if (!skuFilterString.isEmpty()) {
                               sku.append(skuFilterString);
                           }
                       } else if (filterSelectItems.get(i).getOptionName().equalsIgnoreCase("categories")) {
                           for (int j = 0; j < filterSelectItems.get(i).getOptionData().size(); j++) {
                               if (filterSelectItems.get(i).getOptionData().get(j).isSelected()) {
//                            subcategory.append(filterSelectItems.get(i).getOptionData().get(j).getValue()).append("|");
                                   subcategory.append(filterSelectItems.get(i).getOptionData().get(j).getValue()).append(",");
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
//            if (sku.length() != 0) {
//                sku.setLength(sku.length() - 1);
//            }
                   if (availability.length() != 0) {
                       availability.setLength(availability.length() - 1);
                   }
                   if (subcategory.length() != 0) {
                       subcategory.setLength(subcategory.length() - 1);
                   }
//            if (sort_by.length() != 0) {
//                sort_by.setLength(sort_by.length() - 1);
//            }

//            price.setLength(price.length() - 1);
//            gold_purity.setLength(gold_purity.length() - 1);
//            diamond_quality.setLength(diamond_quality.length() - 1);
//            diamond_shape.setLength(diamond_shape.length() - 1);
//            sku.setLength(sku.length() - 1);
//            availability.setLength(availability.length() - 1);
//            sort_by.setLength(sort_by.length() - 1);

                   if (pagecountflag == 1) {     //When filter is applied & go to detail page & back to list page ->>page should not getting refreshed  only refresh data on scrolling
//                pagecountflag = 0;
                       itemArrayList.clear();
                       page_count = 1;

                       listingRecyclerAdapter = new ListingRecyclerAdapter(ListAct.this, itemArrayList);
                       recycleViewListing.setAdapter(listingRecyclerAdapter);
                       if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                           getCategoryProduct(id, "", String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
                       } else {
                           getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
                       }
                       AppLogger.e(" full string data", "--------" + price + gold_purity + diamond_quality + diamond_shape + sku + availability);
                   } else {
                       parentShimmerLayout.setVisibility(View.GONE);
                   }
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

            }*/
               }
           } else if (filterFlag == 2) {
               tvCount.setVisibility(View.GONE);
               linNoData.setVisibility(View.GONE);
               recycleViewListing.setVisibility(View.GONE);
               parentShimmerLayout.setVisibility(View.VISIBLE);
               price.setLength(0);
               gold_purity.setLength(0);
               diamond_quality.setLength(0);
               diamond_shape.setLength(0);
               sku.setLength(0);
               availability.setLength(0);
               subcategory.setLength(0);
//            sort_by.setLength(0);
               itemArrayList.clear();
               mapFilter.clear();
               skuFilterString = "";
               filterFlag = 0;
               page_count = 1;

               //Add for checking Earrings category after Reset pages not load more than 2
               flag_scroll = false;
               previousTotal = 0; // The total number of items in the dataset after the last load
               loading = true; // True if we are still waiting for the last set of data to load.

//            if(pagecountflag == 1){     //When reset filter & go to detail page & back to list page ->> page  getting refreshed
//                pagecountflag = 0;
//                page_count = 1;
//            }

               if (sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                   getCategoryProduct(id, "", String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
               } else {
                   getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
               }
           }
         /*
//        else if (searchbackflag == 1) {
//            //clear id intial as search when user search for products
//            id = "";
//            if(getIntent().getStringExtra(AppConstants.ID) != null) {
//                id = getIntent().getStringExtra(AppConstants.ID);
//            }
//            AppLogger.e("after search reopen listing of main category","-----" + id );
//            //Add for checking Earrings category after search something from listpage & come back again that earring list
//            flag_scroll = false;
//            previousTotal = 0; // The total number of items in the dataset after the last load
//            loading = true; // True if we are still waiting for the last set of data to load.
//        }*/

//           else  {
//               if(!id.equalsIgnoreCase("search")) {
//                   flag_scroll = false;
//                   previousTotal = 0; // The total number of items in the dataset after the last load
//                   loading = true; // True if we are still waiting for the last set of data to load.
//                   page_count = 1;
//
//                   getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
//               }else {
//                   if(searchbackflag == 1){
//                       if (getIntent().getStringExtra(AppConstants.ID) != null) {
//                           id = getIntent().getStringExtra(AppConstants.ID);
//                       }
//                       flag_scroll = false;
//                       previousTotal = 0; // The total number of items in the dataset after the last load
//                       loading = true; // True if we are still waiting for the last set of data to load.
//                       page_count = 1;
//
//                       getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
//                   }
//               }
//           }
           else if(searchbackflag == 1){
                if(!id.equalsIgnoreCase("search")) {
                   flag_scroll = false;
                   previousTotal = 0; // The total number of items in the dataset after the last load
                   loading = true; // True if we are still waiting for the last set of data to load.
//                   page_count = 1;

//                   getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
               }else {
                    if (getIntent().getStringExtra(AppConstants.ID) != null) {
                        id = getIntent().getStringExtra(AppConstants.ID);
                    }
                    AppLogger.e("Id after search & come back to main listing ","-------" + id);
                    flag_scroll = false;
                    previousTotal = 0; // The total number of items in the dataset after the last load
                    loading = true; // True if we are still waiting for the last set of data to load.
//                    page_count = 1;

//                    getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
                }
           } else if(downloadlistback == 1){
               downloadlistback = 0 ;
               getCategoryProduct(id, loginResponse.getData().getGroupId(), String.valueOf(page_count), price.toString(), gold_purity.toString(), diamond_quality.toString(), diamond_shape.toString(), sku.toString(), availability.toString(), sort_by.toString(), subcategory.toString());
           }
       }
    }

/*    private void searchProduct(String searchTerm, String page) {
        if (page_count == 1) {
//            progressCenter.setVisibility(View.VISIBLE);
        } else {
            progressBottom.setVisibility(View.VISIBLE);
        }
        AppLogger.e("SearchTerm","----------"+searchTerm);
        AppLogger.e("Page","----------"+page);
        linNoData.setVisibility(View.GONE);  //Add this here bcz API take time to load so NO data placeholder not be display
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<ListingItem> callApi = apiInterface.searchProduct(customerId,searchTerm, page);
        callApi.enqueue(new Callback<ListingItem>() {
            @Override
            public void onResponse(@NonNull Call<ListingItem> call, @NonNull Response<ListingItem> response) {
                assert response.body() != null;
                if (page_count == 1) {
                    progressCenter.setVisibility(View.GONE);
                    progressBottom.setVisibility(View.GONE);
                } else {
                    progressBottom.setVisibility(View.GONE);
                }
                parentShimmerLayout.setVisibility(View.VISIBLE);
                linNoData.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        linNoData.setVisibility(View.GONE);
                        parentShimmerLayout.setVisibility(View.GONE);
                        recycleViewListing.setVisibility(View.VISIBLE);
                        itemArrayList.addAll(response.body().getData());
                        listingRecyclerAdapter.notifyDataSetChanged();
                        if (!sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                            if(itemArrayList.size() > 1){    //Apply this condition bcz in only 1 product in search result it give bulkdownload icon so For Now when user search product & if more than 2 result found than show BulkDownload option
                                fabDownload.show();
                            }
                        }
                        AppLogger.e("now got Search result ","-- success ");
                    } else {
                        linNoData.setVisibility(View.GONE);
                        if (itemArrayList.isEmpty()) {
                            AppLogger.e("Search result fail ","------");
                            linNoData.setVisibility(View.VISIBLE);
                            parentShimmerLayout.setVisibility(View.GONE);
                            progressCenter.setVisibility(View.GONE);
                            progressBottom.setVisibility(View.GONE);
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
                if (page_count == 1) {
                    progressCenter.setVisibility(View.GONE);
                } else {
                    progressBottom.setVisibility(View.GONE);
                }
            }
        });
    }  */

    private void searchProduct(String searchTerm, String page) {
        if (page_count == 1) {
//            progressCenter.setVisibility(View.VISIBLE);
        } else {
            progressBottom.setVisibility(View.VISIBLE);
        }
        AppLogger.e("SearchTerm","----------"+ searchTerm);
        AppLogger.e("Page","----------"+ page);
        linNoData.setVisibility(View.GONE);  //Add this here bcz API take time to load so NO data placeholder not be display
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<ListingItem> callApi = apiInterface.searchNewProduct(customerId,GroupId,searchTerm, page);
        callApi.enqueue(new Callback<ListingItem>() {
            @Override
            public void onResponse(@NonNull Call<ListingItem> call, @NonNull Response<ListingItem> response) {
                assert response.body() != null;
                if (page_count == 1) {
                    progressCenter.setVisibility(View.GONE);
                    progressBottom.setVisibility(View.GONE);
                } else {
                    progressBottom.setVisibility(View.GONE);
                }
                parentShimmerLayout.setVisibility(View.VISIBLE);
                linNoData.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        linNoData.setVisibility(View.GONE);
                        parentShimmerLayout.setVisibility(View.GONE);
                        recycleViewListing.setVisibility(View.VISIBLE);
                        totalproduct = response.body().getTotal();
                        AppLogger.e("SearchAPI","TotalResult---"+ response.body().getTotal());
                        itemArrayList.addAll(response.body().getData());
                        listingRecyclerAdapter.notifyDataSetChanged();
                        if (!sharedPreferences.getLoginData().equalsIgnoreCase("")) {
                            if(itemArrayList.size() > 1){    //Apply this condition bcz in only 1 product in search result it give bulkdownload icon so For Now when user search product & if more than 2 result found than show BulkDownload option
                                fabDownload.show();
                            }
                        }
                        AppLogger.e("now got Search result ","-- success ");
                    } else {
                        linNoData.setVisibility(View.GONE);
                        if (itemArrayList.isEmpty()) {
                            AppLogger.e("Search result fail ","------");
                            linNoData.setVisibility(View.VISIBLE);
                            parentShimmerLayout.setVisibility(View.GONE);
                            progressCenter.setVisibility(View.GONE);
                            progressBottom.setVisibility(View.GONE);
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

    //Option menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.mainwithcart, menu);
//
//        final MenuItem menuItem = menu.findItem(R.id.action_cart);
//
//        View actionView = MenuItemCompat.getActionView(menuItem);
//        textCartItemCount = actionView.findViewById(R.id.cart_badge);
//
//        DealerMelaBaseActivity.setupBadge();
//
//        actionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onOptionsItemSelected(menuItem);
//            }
//        });
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            case R.id.action_cart: {
//                // Do something
//                startNewActivity(CartAct.class);
//                return true;
//            }
//            case R.id.action_search: {
//                startNewActivity(SearchAct.class);
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
