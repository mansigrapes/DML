package com.dealermela.inventary.activity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.interfaces.RecyclerViewClickListener;
import com.dealermela.inventary.adapter.InventoryPaymentAdapter;
import com.dealermela.inventary.model.InventoryPaymentItem;
import com.dealermela.retrofit.APIClientLaravel;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class PaymentListAct extends DealerMelaBaseActivity {
    //page count
    private int page_count = 1;
    private RecyclerView recycleViewPaymentList;
    private ArrayList<InventoryPaymentItem.Datum> paymentList = new ArrayList<>();
    private InventoryPaymentAdapter inventoryPaymentAdapter;
    boolean isLoading = false;
    private ProgressBar progressBar;
    private ConstraintLayout constraintNoData;
    private TextView tvTotalPaid,tvTotalRemaining;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_payment_list;
    }

    @Override
    public void init() {
        bindToolBar("Payment List");
    }

    @Override
    public void initView() {
        tvTotalRemaining = findViewById(R.id.tvTotalRemaining);
        tvTotalPaid = findViewById(R.id.tvTotalPaid);
        constraintNoData = findViewById(R.id.constraintNoData);
        recycleViewPaymentList = findViewById(R.id.recycleViewPaymentList);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void postInitView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PaymentListAct.this);
        recycleViewPaymentList.setLayoutManager(linearLayoutManager);
        inventoryPaymentAdapter = new InventoryPaymentAdapter(PaymentListAct.this, paymentList, new RecyclerViewClickListener() {
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
        recycleViewPaymentList.setAdapter(inventoryPaymentAdapter);
    }

    @Override
    public void addListener() {
        recycleViewPaymentList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == paymentList.size() - 1) {
                        //bottom of list!
                        page_count++;
                        getPaymentList();
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public void loadData() {
        getPaymentList();
    }

    private void getPaymentList() {
        if (page_count != 1) {
            paymentList.add(null);
            inventoryPaymentAdapter.notifyItemInserted(paymentList.size() - 1);
        }
        ApiInterface apiInterface = APIClientLaravel.getClient().create(ApiInterface.class);
        AppLogger.e("customerId", "-------" + customerId);
        Call<InventoryPaymentItem> callApi = apiInterface.getPaymentList(String.valueOf(page_count), customerId);
        callApi.enqueue(new Callback<InventoryPaymentItem>() {
            @Override
            public void onResponse(@NonNull Call<InventoryPaymentItem> call, @NonNull Response<InventoryPaymentItem> response) {
                AppLogger.e("response", "----------" + Objects.requireNonNull(response.body()).getStatus());
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        if (page_count == 1){
                            tvTotalPaid.setText(CommonUtils.priceFormat(response.body().getTotalPaid()));
                            tvTotalRemaining.setText(CommonUtils.priceFormat(response.body().getTotalRemaining()));
                        }
                        if (page_count != 1) {
                            paymentList.remove(paymentList.size() - 1);
                            int scrollPosition = paymentList.size();
                            inventoryPaymentAdapter.notifyItemRemoved(scrollPosition);
                        }
                        paymentList.addAll(response.body().getData());
                        inventoryPaymentAdapter.notifyDataSetChanged();
                        isLoading = false;
                        progressBar.setVisibility(View.INVISIBLE);
                        constraintNoData.setVisibility(View.GONE);
                    } else if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_FAIL)) {
                        if (page_count != 1) {
                            paymentList.remove(paymentList.size() - 1);
                            int scrollPosition = paymentList.size();
                            inventoryPaymentAdapter.notifyItemRemoved(scrollPosition);
                        }else{
                            constraintNoData.setVisibility(View.VISIBLE);
                        }
                    }else {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (!paymentList.isEmpty()) {
                            constraintNoData.setVisibility(View.VISIBLE);
                        }

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<InventoryPaymentItem> call, @NonNull Throwable t) {
                AppLogger.e("error", t.getMessage());
            }
        });
    }
}
