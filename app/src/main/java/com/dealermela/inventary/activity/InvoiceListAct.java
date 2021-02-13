package com.dealermela.inventary.activity;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.interfaces.RecyclerViewClickListener;
import com.dealermela.inventary.adapter.InventoryInvoiceAdapter;
import com.dealermela.inventary.model.InventoryInvoiceItem;
import com.dealermela.retrofit.APIClientLaravel;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.NetworkUtils;
import com.dealermela.util.ThemePreferences;

import java.util.ArrayList;
import java.util.Objects;

import static com.dealermela.home.activity.MainActivity.customerId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceListAct extends DealerMelaBaseActivity {
    //page count
    private int page_count = 1;
    private RecyclerView recycleViewInvoiceList;
    private ArrayList<InventoryInvoiceItem.Datum> invoiceList = new ArrayList<>();
    private InventoryInvoiceAdapter inventoryInvoiceAdapter;
    boolean isLoading = false;
    private ProgressBar progressBar;
    private ConstraintLayout constraintNoData;
    private androidx.appcompat.widget.SearchView searchViewInvoice;
    private String action = "complete";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_invoice_list;
    }

    @Override
    public void init() {
        bindToolBar("Invoice List");
    }

    @Override
    public void initView() {
        searchViewInvoice = findViewById(R.id.searchViewInvoice);
        constraintNoData = findViewById(R.id.constraintNoData);
        recycleViewInvoiceList = findViewById(R.id.recycleViewInvoiceList);
        progressBar = findViewById(R.id.progressBar);
        ThemePreferences themePreferences = new ThemePreferences(InvoiceListAct.this);
        TextView searchText = searchViewInvoice.findViewById(R.id.search_src_text);
        ImageView searchIcon = searchViewInvoice.findViewById(R.id.search_mag_icon);
        if (themePreferences.getTheme().equalsIgnoreCase("black")) {
            searchText.setTextColor(getResources().getColor(R.color.white));
            searchIcon.setImageDrawable(ContextCompat.getDrawable(InvoiceListAct.this, R.drawable.ic_search_new));
            searchIcon.setColorFilter(ContextCompat.getColor(InvoiceListAct.this, R.color.white), PorterDuff.Mode.SRC_IN);
        } else {
            searchText.setTextColor(getResources().getColor(R.color.black));
            searchIcon.setImageDrawable(ContextCompat.getDrawable(InvoiceListAct.this, R.drawable.ic_search_new));
            searchIcon.setColorFilter(ContextCompat.getColor(InvoiceListAct.this, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
        searchText.setTypeface(myCustomFont);

    }

    @Override
    public void postInitView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InvoiceListAct.this);
        recycleViewInvoiceList.setLayoutManager(linearLayoutManager);
        inventoryInvoiceAdapter = new InventoryInvoiceAdapter(InvoiceListAct.this, invoiceList, new RecyclerViewClickListener() {
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
        recycleViewInvoiceList.setAdapter(inventoryInvoiceAdapter);
    }

    @Override
    public void addListener() {
        searchViewInvoice.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                AppLogger.e("text submit", "" + s);
                if (s.equalsIgnoreCase("")) {
                    page_count = 1;
                    invoiceList.clear();
                    getInvoiceList(action, s);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                AppLogger.e("text change", "" + s);
                page_count = 1;
                invoiceList.clear();
                getInvoiceList(action, s);
                return false;
            }
        });

        searchViewInvoice.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                page_count = 1;
                invoiceList.clear();
                getInvoiceList(action, "");
                return false;
            }
        });

//        searchViewInvoice.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                AppLogger.e("text submit",""+s);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                AppLogger.e("text change",""+s);
//                return true;
//            }
//        });


        recycleViewInvoiceList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == invoiceList.size() - 1) {
                        //bottom of list!
                        page_count++;
                        getInvoiceList(action, "");
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public void loadData() {
        if(NetworkUtils.isNetworkConnected(InvoiceListAct.this)) {
            getInvoiceList(action, "");
        }
    }

    private void getInvoiceList(String invoiceStatus, String searchValue) {
        if (page_count != 1) {
            invoiceList.add(null);
            inventoryInvoiceAdapter.notifyItemInserted(invoiceList.size() - 1);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        ApiInterface apiInterface = APIClientLaravel.getClient().create(ApiInterface.class);
        AppLogger.e("customerId", "-------" + customerId);
        Call<InventoryInvoiceItem> callApi = apiInterface.getInvoiseList(String.valueOf(page_count), invoiceStatus, searchValue, customerId);
        callApi.enqueue(new Callback<InventoryInvoiceItem>() {
            @Override
            public void onResponse(@NonNull Call<InventoryInvoiceItem> call, @NonNull Response<InventoryInvoiceItem> response) {
                AppLogger.e("response", "----------" + Objects.requireNonNull(response.body()).getStatus());
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        if (page_count != 1) {
                            invoiceList.remove(invoiceList.size() - 1);
                            int scrollPosition = invoiceList.size();
                            inventoryInvoiceAdapter.notifyItemRemoved(scrollPosition);
                        }
                        invoiceList.addAll(response.body().getData());
                        inventoryInvoiceAdapter.notifyDataSetChanged();
                        isLoading = false;
                        progressBar.setVisibility(View.GONE);
                        constraintNoData.setVisibility(View.GONE);
                        recycleViewInvoiceList.setVisibility(View.VISIBLE);
                    } else if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_FAIL)) {
                        progressBar.setVisibility(View.GONE);
                        if (page_count != 1) {
                            invoiceList.remove(invoiceList.size() - 1);
                            int scrollPosition = invoiceList.size();
                            inventoryInvoiceAdapter.notifyItemRemoved(scrollPosition);
                        } else {
                            recycleViewInvoiceList.setVisibility(View.GONE);
                            constraintNoData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        if (!invoiceList.isEmpty()) {
                            constraintNoData.setVisibility(View.VISIBLE);
                            recycleViewInvoiceList.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<InventoryInvoiceItem> call, @NonNull Throwable t) {
                AppLogger.e("error", t.getMessage());
            }
        });
    }
}
