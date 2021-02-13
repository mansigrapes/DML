package com.dealermela.authentication.myaccount.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.adapter.BankRecyclerAdapter;
import com.dealermela.authentication.myaccount.model.BankResponse;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class ManageBankAct extends DealerMelaBaseActivity {
    private RecyclerView recycleViewBankList;
    private BankRecyclerAdapter bankRecyclerAdapter;
    public static LinearLayout constraintNoData;
    private TextView tvNoData,tvnewNoData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_manage_bank;
    }

    @Override
    public void init() {
        closeOptionsMenu();
    }

    @Override
    public void initView() {
        bindToolBar("Bank Details");
        recycleViewBankList = findViewById(R.id.recycleViewBankList);
        constraintNoData = findViewById(R.id.constraintlayoutNoData);
        tvNoData = findViewById(R.id.tvNoData);
        tvnewNoData = findViewById(R.id.tvnewNoData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ManageBankAct.this);
        recycleViewBankList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void postInitView() {
    }

    @Override
    public void addListener() {
    }

    @Override
    public void loadData() {
        if(NetworkUtils.isNetworkConnected(ManageBankAct.this)) {
            getBankList();
        }
    }

    private void getBankList() {
        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<BankResponse> callApi = apiInterface.listBankDetail(customerId);
        callApi.enqueue(new Callback<BankResponse>() {
            @Override
            public void onResponse(@NonNull Call<BankResponse> call, @NonNull Response<BankResponse> response) {
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getData()!=null) {
                        bankRecyclerAdapter = new BankRecyclerAdapter(ManageBankAct.this, response.body().getData());
                        recycleViewBankList.setAdapter(bankRecyclerAdapter);
                    }else{
                        constraintNoData.setVisibility(View.VISIBLE);
                        tvnewNoData.setText("add your bank details");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BankResponse> call, @NonNull Throwable t) {
                AppLogger.e(TAG, "------------" + t.getMessage());
                hideProgressDialog();
            }
        });
    }

    //Option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_cart) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
