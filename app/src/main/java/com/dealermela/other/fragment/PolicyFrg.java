package com.dealermela.other.fragment;


import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseFragment;
import com.dealermela.R;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

/*enum Policies {
    Shipping,
    Return,
    Privacy,
    Exchange,
    Termination
}*/

public class PolicyFrg extends DealerMelaBaseFragment {


    private View rootView;
    private String policiesName = "";
    private TextView tvPolicies;
    private ProgressBar progressBar;

    public PolicyFrg() {
        // Required empty public constructor
    }


    @Override
    public View myFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        assert getArguments() != null;
        policiesName = getArguments().getString(AppConstants.NAME);
        rootView = inflater.inflate(R.layout.frg_policy, parent, false);
        return rootView;
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        tvPolicies=rootView.findViewById(R.id.tvPolicies);
        progressBar=rootView.findViewById(R.id.progressBar);
    }

    @Override
    public void postInitView() {

    }

    @Override
    public void addListener() {

    }

    @Override
    public void loadData() {

        getPolicy(customerId, policiesName);

        /*Policies policies = Policies.valueOf(policiesName);


        switch (policies) {

            case Shipping:
                break;
            case Return:
                break;
            case Privacy:
                break;
            case Termination:
                break;
            case Exchange:
                break;

        }*/
    }

    private void getPolicy(String customerId, String policy) {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.getPolicy(customerId,policy);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                assert response.body() != null;
                progressBar.setVisibility(View.GONE);
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;

                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(String.valueOf(response.body()));

                    String data = jsonObject.getString("data");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tvPolicies.setText(Html.fromHtml(data));
                    } else {
                        tvPolicies.setText(Html.fromHtml(data));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }

        });
    }
}
