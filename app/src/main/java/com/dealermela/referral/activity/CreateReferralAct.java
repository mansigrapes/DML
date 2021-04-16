package com.dealermela.referral.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.dealermela.retrofit.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.retrofit.APIClient;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.SharedPreferences;
import com.dealermela.util.Validator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ligl.android.widget.iosdialog.IOSDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateReferralAct extends DealerMelaBaseActivity implements View.OnClickListener {

    private TextInputEditText edFnm, edLnm, edEmail, edTelephone;
    private Button btnManageReferral, btnAddReferral;
    private LoginResponse loginResponse;
    private Spinner spinnerDiscount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_create_referral;
    }

    @Override
    public void init() {
        SharedPreferences sharedPreferences = new SharedPreferences(CreateReferralAct.this);
        Gson gson = new Gson();
        loginResponse = gson.fromJson(sharedPreferences.getLoginData(), LoginResponse.class);
    }

    @Override
    public void initView() {
        bindToolBar("Create Referral");
        edFnm = findViewById(R.id.edFnm);
        edLnm = findViewById(R.id.edLnm);
        edEmail = findViewById(R.id.edEmail);
        edTelephone = findViewById(R.id.edTelephone);
        btnManageReferral = findViewById(R.id.btnManageReferral);
        btnAddReferral = findViewById(R.id.btnAddReferral);
        spinnerDiscount = findViewById(R.id.spinnerDiscount);
    }

    @Override
    public void postInitView() {
        AppLogger.e("commission", "----------" + loginResponse.getData().getReferral_comission());

        if (loginResponse.getCustomerRole().equalsIgnoreCase("Dealer")) {
            int length = 10;
            List<String> spinnerArray = new ArrayList<>();
            for (int i = 0; i <= length; i++) {
                if (i == 0) {
                    spinnerArray.add("Select Discount in %");
                    spinnerArray.add(String.valueOf(i));
                } else {
                    spinnerArray.add(String.valueOf(i));
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDiscount.setAdapter(adapter);

        } else if (loginResponse.getCustomerRole().equalsIgnoreCase("DML Group")) {
            int length = 10;
            List<String> spinnerArray = new ArrayList<>();
            for (int i = 0; i <= length; i++) {
                if (i == 0) {
                    spinnerArray.add("Select Discount in %");
                    spinnerArray.add(String.valueOf(i));
                } else {
                    spinnerArray.add(String.valueOf(i));
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDiscount.setAdapter(adapter);
        } else if (loginResponse.getCustomerRole().equalsIgnoreCase("Referral")) {
            int length = Integer.parseInt(loginResponse.getData().getReferral_comission());
            List<String> spinnerArray = new ArrayList<>();
            for (int i = 0; i <= length; i++) {
                if (i == 0) {
                    spinnerArray.add("Select Discount in %");
                    spinnerArray.add(String.valueOf(i));
                } else {
                    spinnerArray.add(String.valueOf(i));
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDiscount.setAdapter(adapter);

        } else if (loginResponse.getCustomerRole().equalsIgnoreCase("Seller")) {

        } else if (loginResponse.getCustomerRole().equalsIgnoreCase("Vendor Group")) {

        } else if (loginResponse.getCustomerRole().equalsIgnoreCase("Wholesale")) {

        }
    }

    @Override
    public void addListener() {
        btnManageReferral.setOnClickListener(this);
        btnAddReferral.setOnClickListener(this);
    }

    @Override
    public void loadData() {
    }

    private boolean validateCreateReferral() {
        if (!Validator.checkEmptyInputLayout(edFnm, getString(R.string.sign_up_please_enter_fnm))) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edLnm, getString(R.string.sign_up_please_enter_last_name))) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edEmail, getString(R.string.sign_up_please_enter_email))) {
            return false;
        } else if (!Validator.checkEmail(edEmail)) {
            edEmail.requestFocus();
            edEmail.setError(getString(R.string.login_please_enter_valid_email));
            return false;
        }else if(!Validator.isValidPhoneNumber(Objects.requireNonNull(edTelephone.getText().toString()))){
            edTelephone.requestFocus();
            edTelephone.setError(getString(R.string.login_please_enter_valid_mobile));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnManageReferral:
                startNewActivity(ManageReferralAct.class);
                break;

            case R.id.btnAddReferral:
                String password = CommonUtils.getRandomString(15);
                boolean valid = validateCreateReferral();
                if (valid) {
                    if (spinnerDiscount.getSelectedItem() == "Select Discount in %") {
                        CommonUtils.showWarningToast(CreateReferralAct.this, "Please select discount.");
                    } else {
                        addReferral("0", Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edEmail.getText()).toString(), "5", password, spinnerDiscount.getSelectedItem().toString(), loginResponse.getData().getEntityId(), Objects.requireNonNull(edTelephone.getText()).toString(), "customer", "0");
                    }
                }
                break;
        }
    }

    private void addReferral(String franchiseeStatus, String firstName, String lastName, String email, String groupId, String password, String referralCommission, String parentFranchiseId, String telephone, String referralType, String isFranchisee) {

        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.addReferral(franchiseeStatus, firstName, lastName, email, groupId, password, referralCommission, parentFranchiseId, telephone, referralType, isFranchisee);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                hideProgressDialog();
                String message = null;
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        message = jsonObject.getString("message");

                        if(jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)){
                            new IOSDialog.Builder(CreateReferralAct.this)
                                    .setTitle("Thank You!")
                                    .setMessage(message)
                                    .setCancelable(false)
                                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            startNewActivity(ManageReferralAct.class);
                                            finish();
                                        }
                                    })
                                    .show();
                        } else if(jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_FAILED))
                        {
                            new AlertDialog.Builder(CreateReferralAct.this,R.style.AppCompatAlertDialogStyle)
                                    .setTitle(CommonUtils.capitalizeString(jsonObject.getString("status")))
                                    .setMessage(message)
                                    .setCancelable(false)
                                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    /*AlertDialog alertDialog = new AlertDialog.Builder(CreateReferralAct.this).create();
                    alertDialog.setTitle("Thank You!");
                    alertDialog.setMessage(message);
                    alertDialog.setCancelable(false);
                    // Alert dialog button
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Alert dialog action goes here
                                    // onClick button code here
                                    dialog.dismiss();
                                    finish();// use dismiss to cancel alert dialog
                                }
                            });
                    alertDialog.show();*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e(TAG, "------------" + t.getMessage());
                hideProgressDialog();
            }
        });
    }
}
