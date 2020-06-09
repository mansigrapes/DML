package com.dealermela.authentication.myaccount.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.adapter.AddressRecyclerAdapter;
import com.dealermela.authentication.myaccount.model.AddressManageResponse;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.SharedPreferences;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class ManageAddressAct extends DealerMelaBaseActivity implements View.OnClickListener {

    private TextView tvBName;
    private TextView tvBAddress1;
//    private TextView tvBAddress2;
    private TextView tvBCity;
    private TextView tvBCountry;
    private TextView tvBTelephone;
    private TextView tvSName;
    private TextView tvSAddress1;
//    private TextView tvSAddress2;
    private TextView tvSCity;
    private TextView tvSCountry;
    private TextView tvSTelephone;
    private Button btnChangeBillingAddress, btnChangeShippingAddress, btnAddNewAddress;
    private RecyclerView recycleViewAdditionalAddress;
    private AddressManageResponse addressManageResponse;
    private AddressRecyclerAdapter addressRecyclerAdapter;
    public static TextView tvNoAddress;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_manage_address;
    }

    @Override
    public void init() {
        closeOptionsMenu();
    }

    @Override
    public void initView() {
        bindToolBar("Manage Address");
        tvNoAddress = findViewById(R.id.tvNoAddress);
        tvBName = findViewById(R.id.tvBName);
        tvBAddress1 = findViewById(R.id.tvBAddress1);
//        tvBAddress2 = findViewById(R.id.tvBAddress2);
        tvBCity = findViewById(R.id.tvBCity);
        tvBCountry = findViewById(R.id.tvBCountry);
        tvBTelephone = findViewById(R.id.tvBTelephone);
        tvSName = findViewById(R.id.tvSName);
        tvSAddress1 = findViewById(R.id.tvSAddress1);
//        tvSAddress2 = findViewById(R.id.tvSAddress2);
        tvSCity = findViewById(R.id.tvSCity);
        tvSCountry = findViewById(R.id.tvSCountry);
        tvSTelephone = findViewById(R.id.tvSTelephone);
        btnChangeBillingAddress = findViewById(R.id.btnChangeBillingAddress);
        btnChangeShippingAddress = findViewById(R.id.btnChangeShippingAddress);
        btnAddNewAddress = findViewById(R.id.btnAddNewAddress);
        recycleViewAdditionalAddress = findViewById(R.id.recycleViewAdditionalAddress);
        NestedScrollView nestedScrollView = findViewById(R.id.nestedScrollView);
    }

    @Override
    public void postInitView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ManageAddressAct.this, 1);
        recycleViewAdditionalAddress.setLayoutManager(gridLayoutManager);
        recycleViewAdditionalAddress.setNestedScrollingEnabled(true);
    }

    @Override
    public void addListener() {
        btnAddNewAddress.setOnClickListener(this);
        btnChangeBillingAddress.setOnClickListener(this);
        btnChangeShippingAddress.setOnClickListener(this);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Gson gson = new Gson();
        switch (v.getId()) {
            case R.id.btnAddNewAddress:
                intent = new Intent(ManageAddressAct.this, EditAddressAct.class);
                intent.putExtra("addressStatus", "4");
                startNewActivityWithIntent(intent);
                break;

            case R.id.btnChangeBillingAddress:
                intent = new Intent(ManageAddressAct.this, EditAddressAct.class);
                String defaultBilling = gson.toJson(addressManageResponse.getDefaultBilling());
                intent.putExtra("addressStatus", "1");
                intent.putExtra("defaultBilling", defaultBilling);
                startNewActivityWithIntent(intent);
                break;

            case R.id.btnChangeShippingAddress:
                intent = new Intent(ManageAddressAct.this, EditAddressAct.class);
                String defaultShipping = gson.toJson(addressManageResponse.getDefaultShipping());
                intent.putExtra("addressStatus", "2");
                intent.putExtra("defaultShipping", defaultShipping);
                startNewActivityWithIntent(intent);
                break;
        }
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

                if (response.isSuccessful()) {

                    addressManageResponse = response.body();
                    if (addressManageResponse.getData().size() != 0)
                    {
                        addressRecyclerAdapter = new AddressRecyclerAdapter(ManageAddressAct.this, addressManageResponse.getData());
                        recycleViewAdditionalAddress.setAdapter(addressRecyclerAdapter);
                        tvNoAddress.setVisibility(View.GONE);
                    } else {
                        tvNoAddress.setVisibility(View.VISIBLE);
                    }
                    tvBName.setText(response.body().getDefaultBilling().getFirstname() + " " + response.body().getDefaultBilling().getLastname());
                    tvBAddress1.setText(response.body().getDefaultBilling().getStreet());
//                    tvBAddress2.setText(response.body().getDefaultBilling().getStreet1());
                    tvBCity.setText(response.body().getDefaultBilling().getCity() + "," + response.body().getDefaultBilling().getRegion() + "," + response.body().getDefaultBilling().getPostcode());
                    tvBCountry.setText(response.body().getDefaultBilling().getCountry());
                    tvBTelephone.setText("T: " + response.body().getDefaultBilling().getTelephone());

                    tvSName.setText(response.body().getDefaultShipping().getFirstname() + " " + response.body().getDefaultShipping().getLastname());
                    tvSAddress1.setText(response.body().getDefaultShipping().getStreet());
//                    tvSAddress2.setText(response.body().getDefaultShipping().getStreet1());
                    tvSCity.setText(response.body().getDefaultShipping().getCity() + "," + response.body().getDefaultShipping().getRegion() + "," + response.body().getDefaultShipping().getPostcode());
                    tvSCountry.setText(response.body().getDefaultShipping().getCountry());
                    tvSTelephone.setText("T: " + response.body().getDefaultShipping().getTelephone());

                    SharedPreferences sharedPreferences = new SharedPreferences(ManageAddressAct.this);
                    sharedPreferences.saveBillingAddress(response.body().getDefaultBilling().getFirstname() + " " + response.body().getDefaultBilling().getLastname() + ",\n" + response.body().getDefaultBilling().getStreet() + ",\n" + response.body().getDefaultBilling().getCity() + ", " + response.body().getDefaultBilling().getRegion() + ", " + response.body().getDefaultBilling().getPostcode() + ",\n" + response.body().getDefaultBilling().getCountryId() + "\nT: " + response.body().getDefaultBilling().getTelephone());
                    sharedPreferences.saveShipping(response.body().getDefaultShipping().getFirstname() + " " + response.body().getDefaultShipping().getLastname() + ",\n" + response.body().getDefaultShipping().getStreet() + ",\n" + response.body().getDefaultShipping().getCity() + ", " + response.body().getDefaultShipping().getRegion() + ", " + response.body().getDefaultShipping().getPostcode() + ",\n" + response.body().getDefaultShipping().getCountryId() + "\nT: " + response.body().getDefaultShipping().getTelephone());

                    hideProgressDialog();
                }else
                {
                    tvNoAddress.setVisibility(View.VISIBLE);
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
