package com.dealermela.authentication.myaccount.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.dialog.SuccessDialogClass;
import com.dealermela.authentication.myaccount.model.CountryResponse;
import com.dealermela.authentication.myaccount.model.StateResponse;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.Validator;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpAct extends DealerMelaBaseActivity implements View.OnClickListener {

    private TextInputEditText edFnm, edLnm, edEmail, edContact, edAddress, edState, edCity, edZipCode, edPassword, edConfirmPassword,edCompany;
    private TextInputLayout tilFnm, tilLnm, tilEmail, tilContactNumber, tilAddress, tilState, tilCity, tilZipCode, tilPassword, tilConfirmPassword,tilCompany;
    private Button btnSignUp;
    private Spinner spinnerCountry, spinnerState, spinnerCommunity, spinnerEntity;
    private TextView tvState, tvTermsCondition;
    private List<CountryResponse.Datum> countryArray = new ArrayList<>();
    private List<StateResponse.Datum> stateArray = new ArrayList<>();
    private String countryId = "",stateId="";
    private CheckBox checkBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_sign_up;
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        bindToolBar(getString(R.string.sign_up));
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerCommunity = findViewById(R.id.spinnerCommunity);
        spinnerEntity = findViewById(R.id.spinnerEntity);

        edFnm = findViewById(R.id.edFnm);
        edLnm = findViewById(R.id.edLnm);
        edEmail = findViewById(R.id.edEmail);
        edContact = findViewById(R.id.edContact);
        edAddress = findViewById(R.id.edAddress);
        edState = findViewById(R.id.edState);
        edCity = findViewById(R.id.edCity);
        edZipCode = findViewById(R.id.edZipCode);
        edPassword = findViewById(R.id.edPassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);
        edCompany = findViewById(R.id.edCompany);
        tilCompany = findViewById(R.id.tilCompany);
        tilFnm = findViewById(R.id.tilFnm);
        tilLnm = findViewById(R.id.tilLnm);
        tilEmail = findViewById(R.id.tilEmail);
        tilContactNumber = findViewById(R.id.tilContactNumber);
        tilAddress = findViewById(R.id.tilAddress);
        tilState = findViewById(R.id.tilState);
        tilCity = findViewById(R.id.tilCity);
        tilZipCode = findViewById(R.id.tilZipCode);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        tvState = findViewById(R.id.tvState);
        tvTermsCondition = findViewById(R.id.tvTermsCondition);

        checkBox = findViewById(R.id.checkBox);
    }

    @Override
    public void postInitView() {
        List<String> spinnerArray = new ArrayList<>();

        if (!countryArray.isEmpty()) {
            for (int i = 0; i <= countryArray.size() - 1; i++) {
                spinnerArray.add(countryArray.get(i).getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCountry.setAdapter(adapter);
        }
    }

    @Override
    public void addListener() {
        btnSignUp.setOnClickListener(this);
        tvTermsCondition.setOnClickListener(this);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                AppLogger.e("country", "---------" + countryArray.get(position).getName());
                countryId = countryArray.get(position).getCountryId();
                if (countryArray.get(position).getCountryId().equalsIgnoreCase("IN")) {
                    getStateList(countryArray.get(position).getCountryId());
                    tilState.setVisibility(View.GONE);
                    edState.setText("");
                    spinnerState.setVisibility(View.VISIBLE);
                    tvState.setVisibility(View.VISIBLE);
                } else {
                    tilState.setVisibility(View.VISIBLE);
                    spinnerState.setVisibility(View.GONE);
                    tvState.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                stateId = stateArray.get(position).getRegionId();
                AppLogger.e("state id","-----------"+stateId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCommunity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerEntity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                if(spinnerEntity.getSelectedItem().toString().equalsIgnoreCase("company")){
                    tilCompany.setVisibility(View.VISIBLE);
                }else{
                    tilCompany.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void loadData() {
        getCountryList();
    }

    private void getCountryList() {
        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<CountryResponse> callApi = apiInterface.getCountry();
        callApi.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CountryResponse> call, @NonNull Response<CountryResponse> response) {
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                hideProgressDialog();

                countryArray = response.body().getData();
                postInitView();
                if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                    Log.e(AppConstants.RESPONSE, "-----------------" + response.body().getStatus());
                } else if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_FAILED)) {
                    Log.e(AppConstants.RESPONSE, "-----------------" + response.body().getStatus());
                }

            }

            @Override
            public void onFailure(@NonNull Call<CountryResponse> call, @NonNull Throwable t) {
                AppLogger.e(TAG, "------------" + t.getMessage());
                hideProgressDialog();
            }

        });
    }

    private void getStateList(String countryId) {
        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<StateResponse> callApi = apiInterface.getState(countryId);
        callApi.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(@NonNull Call<StateResponse> call, @NonNull Response<StateResponse> response) {
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                hideProgressDialog();
                if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                    Log.e(AppConstants.RESPONSE, "-----------------" + response.body().getStatus());
                    stateArray = response.body().getData();

                    loadState();
                } else if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_FAILED)) {
                    Log.e(AppConstants.RESPONSE, "-----------------" + response.body().getStatus());
                }

            }

            @Override
            public void onFailure(@NonNull Call<StateResponse> call, @NonNull Throwable t) {
                AppLogger.e(TAG, "------------" + t.getMessage());
                hideProgressDialog();
            }

        });
    }

    private void signUp(String firstName, String lastName, String email, String contactNumber, String community, String street, String countryId, String region, String city, String postcode, String entityCustomer, String password, String confirmPassword) {

        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.signUp(firstName, lastName, email, contactNumber, community, street, countryId, region, city, postcode, entityCustomer, password, confirmPassword);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                hideProgressDialog();
                String status = null, message = null;
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("status");
                        ;
                        message = jsonObject.getString("message");

                        if (status.equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                            SuccessDialogClass successDialogClass = new SuccessDialogClass(SignUpAct.this, message);
                            successDialogClass.setCancelable(false);
                            successDialogClass.show();
                            Objects.requireNonNull(successDialogClass.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                            Objects.requireNonNull(successDialogClass.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        } else {
                            new AlertDialog.Builder(SignUpAct.this,R.style.AppCompatAlertDialogStyle)
                                    .setTitle(CommonUtils.capitalizeString(status))
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                boolean valid = validateSignUp();
                if (valid) {
                    if (checkBox.isChecked()) {
                        if (countryId.equalsIgnoreCase("IN")) {
//                            if (spinnerState.getCount()==0){
//                                CommonUtils.showToast(SignUpAct.this,"Please select state.");
//                            }else {
                            if (spinnerState.getSelectedItem() == "Select State") {
                                CommonUtils.showInfoToast(SignUpAct.this, "Please select state.");
                            } else {

                                AppLogger.e("state id","---------"+stateId);

                                if (spinnerCommunity.getSelectedItem() == "Dealer") {
                                    signUp(Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edEmail.getText()).toString(), Objects.requireNonNull(edContact.getText()).toString(), spinnerCommunity.getSelectedItem().toString(), Objects.requireNonNull(edAddress.getText()).toString(), countryId, stateId, Objects.requireNonNull(edCity.getText()).toString(), Objects.requireNonNull(edZipCode.getText()).toString(), spinnerEntity.getSelectedItem().toString(), Objects.requireNonNull(edPassword.getText()).toString(), Objects.requireNonNull(edConfirmPassword.getText()).toString());
                                } else {
                                    signUp(Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edEmail.getText()).toString(), Objects.requireNonNull(edContact.getText()).toString(), spinnerCommunity.getSelectedItem().toString(), Objects.requireNonNull(edAddress.getText()).toString(), countryId, stateId, Objects.requireNonNull(edCity.getText()).toString(), Objects.requireNonNull(edZipCode.getText()).toString(), spinnerEntity.getSelectedItem().toString(), Objects.requireNonNull(edPassword.getText()).toString(), Objects.requireNonNull(edConfirmPassword.getText()).toString());
                                }


                            }

//                            }
                        } else {
                            if (TextUtils.isEmpty(Objects.requireNonNull(edState.getText()).toString())) {
                                edState.setError("Please enter state name.");
                                edState.requestFocus();
                            } else {
                                signUp(Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edEmail.getText()).toString(), Objects.requireNonNull(edContact.getText()).toString(), spinnerCommunity.getSelectedItem().toString(), Objects.requireNonNull(edAddress.getText()).toString(), countryId, edState.getText().toString(), Objects.requireNonNull(edCity.getText()).toString(), Objects.requireNonNull(edZipCode.getText()).toString(), spinnerEntity.getSelectedItem().toString(), Objects.requireNonNull(edPassword.getText()).toString(), Objects.requireNonNull(edConfirmPassword.getText()).toString());
                            }
                        }
                    } else {
                        CommonUtils.showInfoToast(SignUpAct.this, "Please check the terms & condition. ");
                    }

                }
                break;

            case R.id.tvTermsCondition:
                startNewActivity(TermsConditionAct.class);
                break;
        }
    }

    private boolean validateSignUp() {
        if (!Validator.checkEmptyInputEditText(edFnm, tilFnm, getString(R.string.sign_up_please_enter_fnm))) {
            return false;
        } else if (!Validator.checkEmptyInputEditText(edLnm, tilLnm, getString(R.string.sign_up_please_enter_last_name))) {
            return false;
        } else if (!Validator.checkEmptyInputEditText(edEmail, tilEmail, getString(R.string.sign_up_please_enter_email))) {
            return false;
        } else if (!Validator.checkEmail(edEmail)) {
            edEmail.requestFocus();
            edEmail.setError(getString(R.string.login_please_enter_valid_email));
            return false;
        } else if (!Validator.checkEmptyInputEditText(edContact, tilContactNumber, getString(R.string.sign_up_please_enter_contact_no))) {
            return false;
        } else if (!Validator.checkEmptyInputEditText(edAddress, tilAddress, getString(R.string.sign_up_please_enter_address))) {
            return false;
        } else if (!Validator.checkEmptyInputEditText(edCity, tilCity, getString(R.string.sign_up_please_enter_city))) {
            return false;
        } else if (!Validator.checkEmptyInputEditText(edZipCode, tilZipCode, getString(R.string.sign_up_please_enter_zip_code))) {
            return false;
        } else if (!Validator.checkEmptyInputEditText(edPassword, tilPassword, getString(R.string.sign_up_please_enter_password))) {
            return false;
        } else if (!Validator.checkPasswordLength(edPassword)) {
            return false;
        } else if (!Validator.checkEmptyInputEditText(edConfirmPassword, tilConfirmPassword, getString(R.string.sign_up_please_enter_confirm_password))) {
            return false;
        } else if (!Objects.requireNonNull(edPassword.getText()).toString().equals(Objects.requireNonNull(edConfirmPassword.getText()).toString())) {
            //are equal
            edConfirmPassword.setError("Confirm password is invalid.");
            edConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void loadState() {
        List<String> spinnerArray = new ArrayList<>();

        if (!stateArray.isEmpty()) {
            for (int i = 0; i <= stateArray.size() - 1; i++) {
               /* if (i == 0) {
                    spinnerArray.add("Select State");
                }*/
                spinnerArray.add(stateArray.get(i).getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerState.setAdapter(adapter);

        }
    }

}
