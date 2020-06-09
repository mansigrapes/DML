package com.dealermela.inventary.activity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.interfaces.RecyclerViewClickListener;
import com.dealermela.inventary.adapter.InventoryAdapter;
import com.dealermela.inventary.model.InventoryActionItem;
import com.dealermela.inventary.model.InventoryFilterItem;
import com.dealermela.inventary.model.InventoryItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.APIClientLaravel;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;
import static com.dealermela.inventary.activity.InventoryFilterAct.filterFlag;

public class InventoryListAct extends DealerMelaBaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    //page count
    private int page_count = 1;
    //    private boolean flag_scroll = false;
//    private int previousTotal = 0; // The total number of items in the dataset after the last load
//    private boolean loading = true; // True if we are still waiting for the last set of data to load.
//    private final int visibleThreshold = 0; // The minimum amount of items to have below your current scroll position before loading more.
//    private int firstVisibleItem;
//    private int visibleItemCount;
//    private int totalItemCount;
    private RecyclerView recycleViewInventory;
    private ConstraintLayout constraintP;
    private LinearLayout linnodata,linearLayout10;
    private ArrayList<InventoryItem.Datum> list = new ArrayList<>();
    public static List<InventoryFilterItem.Datum> InvfilterSelectItems = new ArrayList<>();
    private InventoryAdapter inventoryListAdapter;
    //    private GridLayoutManager gridLayoutManager;
    boolean isLoading = false;
    private ProgressBar progressBar;
    private Spinner spinnerInventoryAction;
    private String[] inventoryActionArray = {"Select action", "with price and with logo", "with price and without logo", "without price and with logo", "without price and without logo"};
    private String[] inventoryActionValue = {"", "wpwl", "wpwol", "wopwl", "wopwol"};
    private Button btnDownload, btnTryProduct;
    private ArrayList<String> selectedItem = new ArrayList<>();

    private List<InventoryFilterItem.SortBy> sortByList = new ArrayList<>();

    private FloatingActionButton fabFilter;
    private LinearLayout linNoData;
    private StringBuilder price = new StringBuilder();
    private StringBuilder gold_purity = new StringBuilder();
    private StringBuilder diamond_quality = new StringBuilder();
    private StringBuilder diamond_shape = new StringBuilder();

    private long downloadID;

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                CommonUtils.showSuccessToast(InventoryListAct.this, "Download Completed");
                refreshAdapter();
            }
        }
    };

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_inventary_list;
    }

    @Override
    public void init() {
        bindToolBar("Inventory");
        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void initView() {
        constraintP = findViewById(R.id.constraintP);
        linnodata = findViewById(R.id.linNoData);
        linearLayout10 = findViewById(R.id.linearLayout10);
        recycleViewInventory = findViewById(R.id.recycleViewInventory);
        spinnerInventoryAction = findViewById(R.id.spinnerInventoryAction);
        btnDownload = findViewById(R.id.btnDownload);
        btnTryProduct = findViewById(R.id.btnTryProduct);
        progressBar = findViewById(R.id.progressBar);
        fabFilter = findViewById(R.id.fabFilter);
    }

    @Override
    public void postInitView() {
//        gridLayoutManager = new GridLayoutManager(InventoryListAct.this, 1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InventoryListAct.this);
        recycleViewInventory.setLayoutManager(linearLayoutManager);
        inventoryListAdapter = new InventoryAdapter(InventoryListAct.this, list, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }

            @Override
            public void onItemCheck(View view, int position) {
                selectedItem.add(list.get(position).getEntityId());
                CommonUtils.showInfoToastShort(InventoryListAct.this, selectedItem.size() + " product selected");
            }

            @Override
            public void onItemUnCheck(View view, int position) {
                selectedItem.remove(list.get(position).getEntityId());
                CommonUtils.showInfoToastShort(InventoryListAct.this, selectedItem.size() + " product selected");
            }

        });
        recycleViewInventory.setAdapter(inventoryListAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }

    @Override
    public void addListener() {
//        FloatingActionButton fabFilter = findViewById(R.id.fabFilter);
        fabFilter.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        btnTryProduct.setOnClickListener(this);
        spinnerInventoryAction.setOnItemSelectedListener(this);
        /*
        recycleViewInventory.addOnItemTouchListener(new RecyclerTouchListener(InventoryListAct.this, recycleViewInventory, new RecyclerTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {
                CommonUtils.showInfoToast(InventoryListAct.this,"left swipe"+position);
            }

            @Override
            public void onRightSwipe(View view, int position) {
                CommonUtils.showInfoToast(InventoryListAct.this,"right swipe"+position);
            }

            @Override
            public void onClick(View view, int position) {
                CommonUtils.showInfoToast(InventoryListAct.this,"click"+position);
            }

            @Override
            public void onLongClick(View view, int position) {
                CommonUtils.showInfoToast(InventoryListAct.this,"long click"+position);
            }
        }));*/

        recycleViewInventory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == list.size() - 1) {
                        //bottom of list!
                        page_count++;
                        getManageInventory(String.valueOf(page_count),price.toString(),gold_purity.toString(),diamond_quality.toString(),diamond_shape.toString());
                        isLoading = true;
                    }
                }
                                                         /*
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
                                                                 if (NetworkUtils.isNetworkConnected(InventoryListAct.this)) {
                                                                     Log.e("total count", "--------------------" + page_count);
                                                                     page_count++;
                                                                     getManageInventory();

                                                                 } else {
                                                                     //internet not connected
                                                                     AppLogger.e("connection", "-------internet connection is off");
                                                                 }
                                                                 loading = true;
                                                             }

                                                         }

                                                         */
            }
        });
    }

    @Override
    public void loadData() {
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, inventoryActionArray);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerInventoryAction.setAdapter(aa);
        getManageInventory(String.valueOf(page_count),price.toString(),gold_purity.toString(),diamond_quality.toString(),diamond_shape.toString());
        getSortFilter();
    }

    private void getManageInventory(String page,String price,String gold_purity,String diamond_quality,String diamond_shape) {
        if (page_count != 1) {
            list.add(null);
            inventoryListAdapter.notifyItemInserted(list.size() - 1);
        }
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<InventoryItem> callApi = apiInterface.getManageInventory(page,price,gold_purity,diamond_quality,diamond_shape);
        callApi.enqueue(new Callback<InventoryItem>() {
            @Override
            public void onResponse(@NonNull Call<InventoryItem> call, @NonNull Response<InventoryItem> response) {
                assert response.body() != null;
                AppLogger.e("response", "-----------" + response.body().toString());
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {

                        if (page_count != 1) {
                            list.remove(list.size() - 1);
                            int scrollPosition = list.size();
                            inventoryListAdapter.notifyItemRemoved(scrollPosition);
                        }
                        list.addAll(response.body().getData());
                        inventoryListAdapter.notifyDataSetChanged();
                        isLoading = false;
                        progressBar.setVisibility(View.INVISIBLE);

                    } else {
                        if(list.isEmpty()){
                            linnodata.setVisibility(View.VISIBLE);
                            constraintP.setVisibility(View.GONE);
                            btnTryProduct.setVisibility(View.GONE);
                            fabFilter.hide();
                            linearLayout10.setVisibility(View.GONE);
                        }else
                        {
                            linnodata.setVisibility(View.GONE);
                        }
                    }
                } else {
                    if(list.isEmpty()){
                        linnodata.setVisibility(View.VISIBLE);
                        constraintP.setVisibility(View.GONE);
                        btnTryProduct.setVisibility(View.GONE);
                        fabFilter.hide();
                        linearLayout10.setVisibility(View.GONE);
                    }else
                    {
                        linnodata.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<InventoryItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
                linnodata.setVisibility(View.VISIBLE);
                constraintP.setVisibility(View.GONE);
                btnTryProduct.setVisibility(View.GONE);
                linearLayout10.setVisibility(View.GONE);
//                if (page_count == 1) {
//                    progressBar.setVisibility(View.GONE);
//                } else {
//                    progressBar.setVisibility(View.GONE);
//                }
            }
        });
    }

    private void tryProduct(String productIds) {
        showProgressDialog(getString(R.string.please_wait));
        ApiInterface apiInterface = APIClientLaravel.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.storeTryProduct(productIds, customerId);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                AppLogger.e("response", "------------" + response.body().toString());
                if (response.isSuccessful()) {
                    hideProgressDialog();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                            CommonUtils.showSuccessToast(InventoryListAct.this, jsonObject.getString("message"));
                            refreshAdapter();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
            }
        });
    }

    private void inventoryAction(String productsIds, String action) {
        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClientLaravel.getClient().create(ApiInterface.class);
        Call<InventoryActionItem> callApi = apiInterface.inventoryAction(productsIds, action);
        callApi.enqueue(new Callback<InventoryActionItem>() {
            @Override
            public void onResponse(@NonNull Call<InventoryActionItem> call, @NonNull Response<InventoryActionItem> response) {
                hideProgressDialog();
                AppLogger.e("response", "--------" + Objects.requireNonNull(response.body()).getStatus());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        CommonUtils.showInfoToast(InventoryListAct.this, "Downloading pdf..");
                        downloadFile(response.body().getPdf());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<InventoryActionItem> call, @NonNull Throwable t) {
                hideProgressDialog();
            }
        });
    }

    private void downloadFile(String url) {
        File file = new File(getExternalFilesDir(null), "DML Inventory");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                .setTitle("DML_image_pdf_" + new Date().getTime())// Title of the Download Notification
                .setDescription("Downloading")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
//                .setRequiresCharging(false)// Set if charging is required to begin the download
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void getSortFilter() {
        //  fabFilter.setVisibility(View.GONE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<InventoryFilterItem> callApi = apiInterface.setInvFilter();
        callApi.enqueue(new Callback<InventoryFilterItem>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<InventoryFilterItem> call, @NonNull Response<InventoryFilterItem> response) {
                AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
                assert response.body() != null;

                if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                    InvfilterSelectItems.addAll(response.body().getData());
                    sortByList.addAll(response.body().getSortBy());
                    //linSortBy.setVisibility(View.VISIBLE);
                    //  linFilter.setVisibility(View.VISIBLE);
                } else {
                    linNoData.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<InventoryFilterItem> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(filterFlag == 1){
            list.clear();
            price.setLength(0);
            gold_purity.setLength(0);
            diamond_quality.setLength(0);
            diamond_shape.setLength(0);

            for(int i = 0; i<InvfilterSelectItems.size(); i++){
                if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("price")){
                    for(int j=0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++){
                        if(InvfilterSelectItems.get(i).getOptionData().get(j).isSelected()){
                            price.append(InvfilterSelectItems.get(i).getOptionData().get(j).getValue()).append("|");
                        }
                    }
                } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("gold_purity")){
                    for(int j=0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++){
                        if(InvfilterSelectItems.get(i).getOptionData().get(j).isSelected()){
                            gold_purity.append(InvfilterSelectItems.get(i).getOptionData().get(j).getValue()).append("|");
                        }
                    }
                } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_quality")){
                    for(int j=0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++){
                        if(InvfilterSelectItems.get(i).getOptionData().get(j).isSelected()){
                            diamond_quality.append(InvfilterSelectItems.get(i).getOptionData().get(j).getValue()).append("|");
                        }
                    }
                } else if(InvfilterSelectItems.get(i).getOptionName().equalsIgnoreCase("diamond_shape")){
                    for(int j=0; j < InvfilterSelectItems.get(i).getOptionData().size(); j++){
                        if(InvfilterSelectItems.get(i).getOptionData().get(j).isSelected()){
                            diamond_shape.append(InvfilterSelectItems.get(i).getOptionData().get(j).getValue()).append("|");
                        }
                    }
                }
            }

            if(price.length() != 0){
                price.setLength(price.length() - 1);
            }
            if(gold_purity.length() != 0){
                gold_purity.setLength(gold_purity.length() - 1);
            }
            if(diamond_quality.length() != 0){
                diamond_quality.setLength(diamond_quality.length() - 1);
            }
            if(diamond_shape.length() != 0){
                diamond_shape.setLength(diamond_shape.length() -1);
            }

            inventoryListAdapter = new InventoryAdapter(InventoryListAct.this, list, new RecyclerViewClickListener() {
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

            recycleViewInventory.setAdapter(inventoryListAdapter);
            getManageInventory(String.valueOf(page_count),price.toString(),gold_purity.toString(),diamond_quality.toString(),diamond_shape.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDownload:
                if (selectedItem.isEmpty()) {
                    CommonUtils.showErrorToast(InventoryListAct.this, "Please select item. ");
                } else {
                    if (spinnerInventoryAction.getSelectedItem() == inventoryActionArray[0]) {
                        CommonUtils.showErrorToast(InventoryListAct.this, "Please select action.");
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (String s : selectedItem) {
                            sb.append(s);
                            sb.append(",");
                        }
                        AppLogger.e("product id", "--------" + sb);
                        sb.deleteCharAt(sb.length() - 1);
                        AppLogger.e("product id", "--------" + sb);
                        String action = spinnerInventoryAction.getSelectedItem().toString();
                        for (int i = 0; i < inventoryActionArray.length; i++) {
                            if (action.equalsIgnoreCase(inventoryActionArray[i])) {
                                action = inventoryActionValue[i];
                                break;
                            }
                        }
                        AppLogger.e("action", "--------" + action);
                        inventoryAction(String.valueOf(sb), action);
                    }
                }
                break;

            case R.id.btnTryProduct:
                if (selectedItem.isEmpty()) {
                    CommonUtils.showErrorToast(InventoryListAct.this, "Please select item. ");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (String s : selectedItem) {
                        sb.append(s);
                        sb.append(",");
                    }
                    AppLogger.e("product id", "--------" + sb);
                    sb.deleteCharAt(sb.length() - 1);
                    AppLogger.e("product id", "--------" + sb);
                    String action = spinnerInventoryAction.getSelectedItem().toString();
                    for (int i = 0; i < inventoryActionArray.length; i++) {
                        if (action.equalsIgnoreCase(inventoryActionArray[i])) {
                            action = inventoryActionValue[i];
                            break;
                        }
                    }
                    AppLogger.e("action", "--------" + action);
                    tryProduct(sb.toString());
                }
                break;

            case R.id.fabFilter:
                Intent intent=new Intent(InventoryListAct.this,InventoryFilterAct.class);
                startActivity(intent);
                break;
        }
    }

    private void refreshAdapter() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelected(false);
        }
        inventoryListAdapter.notifyDataSetChanged();
        selectedItem.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inventory_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.actionListInvoice:
                startNewActivity(InvoiceListAct.class);
                break;
            case R.id.actionListPayment:
                startNewActivity(PaymentListAct.class);
                break;
            case R.id.actionListProduct:
                startNewActivity(ProductListAct.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
