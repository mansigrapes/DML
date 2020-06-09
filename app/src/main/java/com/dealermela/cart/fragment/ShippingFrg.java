package com.dealermela.cart.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseFragment;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.activity.ManageAddressAct;
import com.dealermela.authentication.myaccount.adapter.AddressRecyclerAdapter;
import com.dealermela.authentication.myaccount.model.AddressManageResponse;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.cart.activity.SelectAddressAct;
import com.dealermela.home.activity.MainActivity;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.SharedPreferences;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingFrg extends DealerMelaBaseFragment implements View.OnClickListener {
    private View rootView;
    private Button btnSaveContinue;
    private TextView tvDefaultBillingAddress, tvDefaultShippingAddress;
    private LoginResponse loginResponse;
    private TextView tvChangeShipping, tvChangeBilling;
    private SharedPreferences sharedPreferences;

    public ShippingFrg() {
        // Required empty public constructor
    }

    @Override
    public View myFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frg_shipping, parent, false);
        return rootView;
    }

    @Override
    public void init() {
        sharedPreferences = new SharedPreferences(getActivity());
        Gson gson = new Gson();
        loginResponse = gson.fromJson(sharedPreferences.getLoginData(), LoginResponse.class);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        btnSaveContinue = rootView.findViewById(R.id.btnSaveContinue);
        tvDefaultBillingAddress = rootView.findViewById(R.id.tvDefaultBillingAddress);
        tvDefaultShippingAddress = rootView.findViewById(R.id.tvDefaultShippingAddress);
        tvChangeShipping = rootView.findViewById(R.id.tvChangeShipping);
        tvChangeBilling = rootView.findViewById(R.id.tvChangeBilling);
    }

    @Override
    public void postInitView() {
    }

    @Override
    public void addListener() {
        btnSaveContinue.setOnClickListener(this);
        tvChangeShipping.setOnClickListener(this);
        tvChangeBilling.setOnClickListener(this);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaveContinue:
                Fragment fragment = new PaymentFrg();
                FragmentManager fm = getFragmentManager();
                assert fm != null;
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameCart, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

            case R.id.tvChangeBilling:
                Intent intent = new Intent(getActivity(), SelectAddressAct.class);
                intent.putExtra(AppConstants.NAME, "Billing");
                startNewActivityWithIntent(intent);
                break;

            case R.id.tvChangeShipping:
                Intent intent2 = new Intent(getActivity(), SelectAddressAct.class);
                intent2.putExtra(AppConstants.NAME, "Shipping");
                startNewActivityWithIntent(intent2);
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        tvDefaultBillingAddress.setText(sharedPreferences.getBillingAddress());
        tvDefaultShippingAddress.setText(sharedPreferences.getShippingAddress());
    }
}
