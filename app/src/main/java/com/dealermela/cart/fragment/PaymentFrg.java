package com.dealermela.cart.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseFragment;
import com.dealermela.R;
import com.dealermela.cart.activity.ShippingChargeAct;
import com.dealermela.cart.adapter.PaymentSelectAdapter;
import com.dealermela.cart.model.SelectPaymentItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class PaymentFrg extends DealerMelaBaseFragment implements View.OnClickListener {

    private View rootView;
    private RecyclerView recycleViewPayment;
    private Button btnContinue;
    private List<SelectPaymentItem.ShippingCharge> shippingCharges = new ArrayList<>();
    private ProgressBar progressBarPayment;
    private String paymentTypes;

    public PaymentFrg() {
        // Required empty public constructor
    }

    @Override
    public View myFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frg_payment, parent, false);
        return rootView;
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        // get the listview
        recycleViewPayment = rootView.findViewById(R.id.recycleViewPayment);
        btnContinue = rootView.findViewById(R.id.btnContinue);
        progressBarPayment = rootView.findViewById(R.id.progressBarPayment);
        btnContinue.setVisibility(View.INVISIBLE);
    }

    @Override
    public void postInitView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleViewPayment.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void addListener() {
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        selectPaymentMethod();
    }

    public void selectPaymentMethod() {
        progressBarPayment.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<SelectPaymentItem> callApi = apiInterface.getSelectPayment(customerId);
        callApi.enqueue(new Callback<SelectPaymentItem>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<SelectPaymentItem> call, @NonNull Response<SelectPaymentItem> response) {
                AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());

                progressBarPayment.setVisibility(View.GONE);
                assert response.body() != null;

                if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                    btnContinue.setVisibility(View.VISIBLE);
                    btnContinue.setEnabled(true);

//                    if (response.body().getShippingCharges() == null || response.body().getShippingCharges().isEmpty()) {
////                        CommonUtils.showToast(getActivity(),"Shipping charges is empty");
//
//                        Snackbar snack = Snackbar.make(rootView, "Shipping charges is empty, you can not continue this order.", Snackbar.LENGTH_LONG);
//                        View view = snack.getView();
//                        TextView tv = (TextView) view.findViewById(R.id.snackbar_text);
//                        tv.setTextColor(Color.YELLOW);
//                        snack.show();
//
//                        btnContinue.setEnabled(false);
//
//
//                    } else {
//                        shippingCharges.addAll(response.body().getShippingCharges());
//                    }
                    if(response.body().getShippingCharges() != null || response.body().getShippingCharges().isEmpty()) {
                        shippingCharges.addAll(response.body().getShippingCharges());
                    }else {

                    }
                    PaymentSelectAdapter paymentSelectAdapter = new PaymentSelectAdapter(getActivity(), response.body().getDate(), PaymentFrg.this);
                    recycleViewPayment.setAdapter(paymentSelectAdapter);

                } else {
                }
            }

            @Override
            public void onFailure(@NonNull Call<SelectPaymentItem> call, @NonNull Throwable t) {
                progressBarPayment.setVisibility(View.GONE);
                AppLogger.e("Error PaymentMeyhod API :","---" + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinue:
                Intent intent = new Intent(getActivity(), ShippingChargeAct.class);
                intent.putExtra(AppConstants.NAME, (Serializable) shippingCharges);
                intent.putExtra(AppConstants.PAYMENT_TYPE, paymentTypes);
                startNewActivityWithIntent(intent);
                break;
        }
    }

    public void getSelectPayment(String paymentType) {
        AppLogger.e("paymentType", "-----------" + paymentType);
        paymentTypes = paymentType;
    }
}
