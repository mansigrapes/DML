package com.dealermela.cart.activity;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.cart.adapter.PaymentSelectAdapter;
import com.dealermela.cart.adapter.ShippingChargeAdapter;
import com.dealermela.cart.fragment.PaymentFrg;
import com.dealermela.cart.model.SelectPaymentItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class ShippingChargeAct extends DealerMelaBaseActivity implements View.OnClickListener {

    private RecyclerView recycleViewShippingCharge;
    private Button btnContinue;
    private String paymentType;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_shiiping_charge;
    }

    @Override
    public void init() {

        ArrayList<SelectPaymentItem.ShippingCharge> myList = (ArrayList<SelectPaymentItem.ShippingCharge>) getIntent().getSerializableExtra(AppConstants.NAME);

        paymentType = getIntent().getStringExtra(AppConstants.PAYMENT_TYPE);

//        AppLogger.e("data", "-----------" + myList.get(0).getPrice());
        AppLogger.e("paymentType", "-----------" + paymentType);

        recycleViewShippingCharge = findViewById(R.id.recycleViewShippingCharge);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShippingChargeAct.this);
        recycleViewShippingCharge.setLayoutManager(linearLayoutManager);

        ShippingChargeAdapter shippingChargeAdapter = new ShippingChargeAdapter(ShippingChargeAct.this, myList);
        recycleViewShippingCharge.setAdapter(shippingChargeAdapter);

    }

    @Override
    public void initView() {
        bindToolBar("Shipping");
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setEnabled(false);
        btnContinue.setVisibility(View.INVISIBLE);
    }

    @Override
    public void postInitView() {

    }

    @Override
    public void addListener() {
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    public void getSelectShipping(String code, String price) {

        paymentSave(paymentType, code, price);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinue:
                startNewActivity(OrderSummaryAct.class);
                break;
        }
    }

    private void paymentSave(String payment_method, String shipping_method, String shipping_price) {
        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.savePayment(customerId, payment_method, shipping_method, shipping_price);
        callApi.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
                hideProgressDialog();
                assert response.body() != null;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        btnContinue.setEnabled(true);
                        btnContinue.setVisibility(View.VISIBLE);
                    } else {
                        btnContinue.setEnabled(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
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
