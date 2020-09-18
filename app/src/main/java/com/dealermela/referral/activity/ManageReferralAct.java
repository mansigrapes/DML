package com.dealermela.referral.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.referral.adapter.ReferralListRecyclerAdapter;
import com.dealermela.referral.model.ReferralResponse;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class ManageReferralAct extends DealerMelaBaseActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private RecyclerView recycleViewReferralList;
    private Button btnCreateReferral;
    private TextView tvnewNoData;
    private ReferralListRecyclerAdapter referralListRecyclerAdapter;
//    public static ConstraintLayout constraintNoData;
    public static LinearLayout constraintNoData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_manage_referral;
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        bindToolBar("List of Referrals");
        progressBar = findViewById(R.id.progressBar);
        recycleViewReferralList = findViewById(R.id.recycleViewReferralList);
        constraintNoData = findViewById(R.id.constraintlayoutNoData);
        btnCreateReferral = findViewById(R.id.btnCreateReferral);
        tvnewNoData = findViewById(R.id.tvnewNoData);
    }

    @Override
    public void postInitView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ManageReferralAct.this);
        recycleViewReferralList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void addListener() {
        btnCreateReferral.setOnClickListener(this);
        getReferralList();
    }

    @Override
    public void loadData() {
        getReferralList();
    }

    private void getReferralList() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<ReferralResponse> callApi = apiInterface.referralList(customerId);
        callApi.enqueue(new Callback<ReferralResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ReferralResponse> call, @NonNull Response<ReferralResponse> response) {
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

//                    if (!response.body().getData().isEmpty()) {
                    if(response.body().getData() != null)  {
                        constraintNoData.setVisibility(View.GONE);
                        referralListRecyclerAdapter = new ReferralListRecyclerAdapter(ManageReferralAct.this, response.body().getData());
                        recycleViewReferralList.setAdapter(referralListRecyclerAdapter);
                    }else{
                        constraintNoData.setVisibility(View.VISIBLE);
                        tvnewNoData.setText("add your referral");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReferralResponse> call, @NonNull Throwable t) {
                AppLogger.e(TAG, "------------" + t.getMessage());
                progressBar.setVisibility(View.GONE);
                constraintNoData.setVisibility(View.VISIBLE);
                tvnewNoData.setText("add your referral");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateReferral:
                startNewActivity(CreateReferralAct.class);
                break;
        }
    }
}
