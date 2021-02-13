package com.dealermela.inventary.activity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.interfaces.RecyclerViewClickListener;
import com.dealermela.inventary.adapter.InventoryQuotationAdapter;
import com.dealermela.inventary.model.InventoryQuotationItem;
import com.dealermela.retrofit.APIClientLaravel;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.NetworkUtils;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class QuotationListAct extends DealerMelaBaseActivity {
    //page count
    private int page_count = 1;
    private RecyclerView recycleViewQuotationList;
    private ArrayList<InventoryQuotationItem.Datum> quotationList = new ArrayList<>();
    private InventoryQuotationAdapter inventoryQuotationAdapter;
    boolean isLoading = false;
    private ProgressBar progressBar;
    private ConstraintLayout constraintNoData;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_quotation_list;
    }

    @Override
    public void init() {
        bindToolBar("Payment List");
    }

    @Override
    public void initView() {
        constraintNoData = findViewById(R.id.constraintNoData);
        recycleViewQuotationList = findViewById(R.id.recycleViewPaymentList);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void postInitView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuotationListAct.this);
        recycleViewQuotationList.setLayoutManager(linearLayoutManager);
        inventoryQuotationAdapter = new InventoryQuotationAdapter(QuotationListAct.this, quotationList, new RecyclerViewClickListener() {
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
        recycleViewQuotationList.setAdapter(inventoryQuotationAdapter);
    }

    @Override
    public void addListener() {
        recycleViewQuotationList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == quotationList.size() - 1) {
                        //bottom of list!
                        page_count++;
                        getQuotationList();
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public void loadData() {
        if(NetworkUtils.isNetworkConnected(QuotationListAct.this)) {
            getQuotationList();
        }
    }

    private void getQuotationList() {
        if (page_count != 1) {
            quotationList.add(null);
            inventoryQuotationAdapter.notifyItemInserted(quotationList.size() - 1);
        }
        ApiInterface apiInterface = APIClientLaravel.getClient().create(ApiInterface.class);
        AppLogger.e("customerId", "-------" + customerId);
        Call<InventoryQuotationItem> callApi = apiInterface.getQuotationList(String.valueOf(page_count), customerId);
        callApi.enqueue(new Callback<InventoryQuotationItem>() {
            @Override
            public void onResponse(@NonNull Call<InventoryQuotationItem> call, @NonNull Response<InventoryQuotationItem> response) {
                AppLogger.e("response", "----------" + Objects.requireNonNull(response.body()).getStatus());
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        if (page_count != 1) {
                            quotationList.remove(quotationList.size() - 1);
                            int scrollPosition = quotationList.size();
                            inventoryQuotationAdapter.notifyItemRemoved(scrollPosition);
                        }
                        quotationList.addAll(response.body().getData());
                        inventoryQuotationAdapter.notifyDataSetChanged();
                        isLoading = false;
                        progressBar.setVisibility(View.INVISIBLE);
                        constraintNoData.setVisibility(View.GONE);
                    } else if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_FAIL)) {
                        if (page_count != 1) {
                            quotationList.remove(quotationList.size() - 1);
                            int scrollPosition = quotationList.size();
                            inventoryQuotationAdapter.notifyItemRemoved(scrollPosition);
                        } else {
                            constraintNoData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (!quotationList.isEmpty()) {
                            constraintNoData.setVisibility(View.VISIBLE);
                        }

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<InventoryQuotationItem> call, @NonNull Throwable t) {
                AppLogger.e("error", t.getMessage());
            }
        });
    }
}
