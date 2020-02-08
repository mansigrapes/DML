package com.dealermela.cart.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.activity.EditAddressAct;
import com.dealermela.authentication.myaccount.activity.ManageAddressAct;
import com.dealermela.authentication.myaccount.adapter.AddressRecyclerAdapter;
import com.dealermela.authentication.myaccount.model.AddressManageResponse;
import com.dealermela.cart.adapter.SelectAddressAdapter;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class SelectAddressAct extends DealerMelaBaseActivity implements View.OnClickListener {

    private RecyclerView recycleViewAddress;
    private Button btnAddNewAddress;
    public String addressFlag = "";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_select_address;
    }

    @Override
    public void init() {
        addressFlag = getIntent().getStringExtra(AppConstants.NAME);
    }

    @Override
    public void initView() {
        bindToolBar("Select Address");
        recycleViewAddress = findViewById(R.id.recycleViewAddress);
        btnAddNewAddress = findViewById(R.id.btnAddNewAddress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectAddressAct.this);
        recycleViewAddress.setLayoutManager(linearLayoutManager);


    }

    @Override
    public void postInitView() {

    }

    @Override
    public void addListener() {
        btnAddNewAddress.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    private void getAddress(String customerId) {
        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<AddressManageResponse> callApi = apiInterface.getAllAddress(customerId);
        callApi.enqueue(new Callback<AddressManageResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<AddressManageResponse> call, @NonNull Response<AddressManageResponse> response) {
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                hideProgressDialog();
                if (response.isSuccessful()) {
                    SelectAddressAdapter selectAddressAdapter = new SelectAddressAdapter(SelectAddressAct.this, response.body().getData());
                    recycleViewAddress.setAdapter(selectAddressAdapter);
                    recycleViewAddress.scrollToPosition(response.body().getData().size()-1);

                }
            }

            @Override
            public void onFailure(@NonNull Call<AddressManageResponse> call, @NonNull Throwable t) {
                AppLogger.e(TAG, "------------" + t.getMessage());
                hideProgressDialog();
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddNewAddress:
                Intent intent = new Intent(SelectAddressAct.this, EditAddressAct.class);
                intent.putExtra("addressStatus", "5");
                startNewActivityWithIntent(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress(customerId);
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
