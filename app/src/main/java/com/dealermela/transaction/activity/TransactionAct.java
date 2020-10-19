package com.dealermela.transaction.activity;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.retrofit.APIClientLaravel;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.transaction.adapter.TransactionListAdapter;
import com.dealermela.transaction.model.TransactionItem;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class TransactionAct extends DealerMelaBaseActivity {

    private RecyclerView recycleViewTransaction;
    //page count
    private int page_count = 1;
    private boolean flag_scroll = false;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private final int visibleThreshold = 0; // The minimum amount of items to have below your current scroll position before loading more.
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;

    private GridLayoutManager gridLayout;
    private ProgressBar progressBarBottom, progressBarCenter;
    private LinearLayout linNoData;
    private List<TransactionItem.Datum> dataList;
    private NestedScrollView nestedScrollView;
    private TextView tvTotalDebit, tvTotalDeposit, tvTotalCredit, tvFinalTotalDebit;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_transaction;
    }

    @Override
    public void init() {
        dataList = new ArrayList<>();
    }

    @Override
    public void initView() {
        bindToolBar("Transaction");
        recycleViewTransaction = findViewById(R.id.recycleViewTransaction);
        progressBarBottom = findViewById(R.id.progressBarBottom);
        progressBarCenter = findViewById(R.id.progressBarCenter);
        linNoData = findViewById(R.id.newlinNoData);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        tvTotalDebit = findViewById(R.id.tvTotalDebit);
        tvTotalDeposit = findViewById(R.id.tvTotalDeposit);
//        tvTotalCredit = findViewById(R.id.tvTotalCredit);
//        tvFinalTotalDebit = findViewById(R.id.tvFinalTotalDebit);
        progressBarBottom.setVisibility(View.GONE);
    }

    @Override
    public void postInitView() {
//Set layout diamond adapter
        gridLayout = new GridLayoutManager(TransactionAct.this, 1);
        gridLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recycleViewTransaction.setLayoutManager(gridLayout);
//        ViewCompat.setNestedScrollingEnabled(recycleViewTransaction, true);
//        nestedScrollView.fullScroll(View.FOCUS_UP);
//        nestedScrollView.scrollTo(0, 0);
//        recycleViewTransaction.setNestedScrollingEnabled(true);
    }

    @Override
    public void addListener() {
        recycleViewTransaction.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                                       @Override
                                                       public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                                           super.onScrolled(recyclerView, dx, dy);

                                                           visibleItemCount = recyclerView.getChildCount();
                                                           totalItemCount = gridLayout.getItemCount();
                                                           firstVisibleItem = gridLayout.findFirstVisibleItemPosition();

                                                           if (flag_scroll) {
                                                               AppLogger.e("flag-Scroll", flag_scroll + "");
                                                           } else {
                                                               if (loading) {
                                                                   AppLogger.e("flag-Loading", loading + "");
                                                                   if (totalItemCount > previousTotal) {
                                                                       loading = false;
                                                                       previousTotal = totalItemCount;
                                                                       //Log.e("flag-IF", (totalItemCount > previousTotal) + "");
                                                                   }
                                                               }
                                                               if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                                                                   // End has been reached
                                                                   // Do something
                                                                   AppLogger.e("flag-Loading_second_if", loading + "");
                                                                   if (NetworkUtils.isNetworkConnected(TransactionAct.this)) {
                                                                       AppLogger.e("total count", "--------------------" + page_count);
                                                                       AppLogger.e("page_count", "--------------------" + page_count);
                                                                       page_count++;
                                                                       getTransactionList(customerId, String.valueOf(page_count));
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
        TransactionListAdapter transactionListAdapter = new TransactionListAdapter(TransactionAct.this, dataList);
        recycleViewTransaction.setAdapter(transactionListAdapter);
        getTransactionList(customerId, String.valueOf(page_count));
    }

    private void getTransactionList(String customerId, String page) {
        if (page_count == 1) {
            progressBarCenter.setVisibility(View.VISIBLE);
        } else {
            progressBarBottom.setVisibility(View.VISIBLE);
        }
        ApiInterface apiInterface = APIClientLaravel.getClient().create(ApiInterface.class);
        Call<TransactionItem> callApi = apiInterface.transactionList(customerId, page);
        callApi.enqueue(new Callback<TransactionItem>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<TransactionItem> call, @NonNull Response<TransactionItem> response) {
                assert response.body() != null;
                AppLogger.e("response", "-------------" + response.body());
//                AppLogger.e("response", "-------------" + response.body().getStatus());
                if (page_count == 1) {
                    progressBarCenter.setVisibility(View.GONE);
                    if(response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                            tvTotalDebit.setText(AppConstants.RS + CommonUtils.rupeeFormat(String.valueOf(response.body().getTotalPaid())));
                            tvTotalDeposit.setText(AppConstants.RS + CommonUtils.rupeeFormat(String.valueOf(response.body().getTotalRemaing())));
//                        tvTotalCredit.setText(AppConstants.RS + CommonUtils.rupeeFormat(String.valueOf(response.body().getTotalCredit())));
//                        tvFinalTotalDebit.setText(AppConstants.RS + CommonUtils.rupeeFormat(String.valueOf(response.body().getFinalTotalamount())));
                        } else {
                            linNoData.setVisibility(View.VISIBLE);
                        }
                    }else{
                        linNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    progressBarBottom.setVisibility(View.GONE);
                }
                if (response.isSuccessful()) {

                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        dataList.addAll(response.body().getData());
//                        downloadProductAdapter.notifyDataSetChanged();
                    } else {
                        if (dataList.isEmpty()) {
//                            linNoData.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TransactionItem> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
                linNoData.setVisibility(View.VISIBLE);
                if (page_count == 1) {
                    progressBarCenter.setVisibility(View.GONE);
                } else {
                    progressBarBottom.setVisibility(View.GONE);
                }
            }
        });
    }
}
