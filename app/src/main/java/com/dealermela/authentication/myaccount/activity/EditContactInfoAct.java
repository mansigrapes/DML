package com.dealermela.authentication.myaccount.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.model.CountryResponse;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.authentication.myaccount.model.StateResponse;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.SharedPreferences;
import com.dealermela.util.ThemePreferences;
import com.dealermela.util.Validator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class EditContactInfoAct extends DealerMelaBaseActivity implements View.OnClickListener {

    private TextInputLayout tilState;
    private TextInputEditText edFnm, edLnm, edEmail, edContact, edAddress, edState, edCity, edZipCode, edPanCard, edGstIn;
    private Button btnSave;
    private Spinner spinnerCountry, spinnerState;
    private List<CountryResponse.Datum> countryArray = new ArrayList<>();
    private List<StateResponse.Datum> stateArray = new ArrayList<>();
    private String countryId = "";
    private View view_state;
    private LoginResponse loginResponse;
    private SharedPreferences sharedPreferences;
    private ThemePreferences themePreferences;
    private TextView lblState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_edit_contact_info;
    }

    @Override
    public void init() {
        themePreferences = new ThemePreferences(EditContactInfoAct.this);
        sharedPreferences = new SharedPreferences(EditContactInfoAct.this);
        Gson gson = new Gson();
        loginResponse = gson.fromJson(sharedPreferences.getLoginData(), LoginResponse.class);
    }

    @Override
    public void initView() {
        bindToolBar("Edit Contact");

        lblState = findViewById(R.id.lblState);
        view_state = findViewById(R.id.view_state);
        tilState = findViewById(R.id.tilState);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerState = findViewById(R.id.spinnerState);
        edFnm = findViewById(R.id.edFnm);
        edLnm = findViewById(R.id.edLnm);
        edEmail = findViewById(R.id.edEmail);
        edContact = findViewById(R.id.edContact);
        edAddress = findViewById(R.id.edAddress);
        edState = findViewById(R.id.edState);
        edCity = findViewById(R.id.edCity);
        edZipCode = findViewById(R.id.edZipCode);
        edPanCard = findViewById(R.id.edPanCard);
        edGstIn = findViewById(R.id.edGstIn);
        btnSave = findViewById(R.id.btnSave);
    }

    @Override
    public void postInitView() {

        edFnm.setText(loginResponse.getData().getFirstname());
        edLnm.setText(loginResponse.getData().getLastname());
        edEmail.setText(loginResponse.getData().getEmail());
//        edContact.setText(loginResponse.getData().getContactNumber());
        edContact.setText(loginResponse.getData().getTelephone());
        edAddress.setText(loginResponse.getData().getStreet());
        edCity.setText(loginResponse.getData().getCity());
        edZipCode.setText(loginResponse.getData().getPostcode());
        edPanCard.setText(loginResponse.getData().getPancardno());
        edGstIn.setText(loginResponse.getData().getGstin());

        String compareValue = loginResponse.getData().getCountryId();
        List<String> spinnerArray = new ArrayList<>();
        List<String> countryA = new ArrayList<>();

        if (!countryArray.isEmpty()) {
            for (int i = 0; i <= countryArray.size() - 1; i++) {
                spinnerArray.add(countryArray.get(i).getName());
                countryA.add(countryArray.get(i).getCountryId());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, countryA);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCountry.setSelection(99);
            spinnerCountry.setEnabled(false);
            spinnerCountry.setAdapter(adapter);

            if (compareValue != null) {
                int spinnerPosition = adapter1.getPosition(compareValue);
                spinnerCountry.setSelection(spinnerPosition);
            }
        }
    }

    @Override
    public void addListener() {
        btnSave.setOnClickListener(this);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (themePreferences.getTheme().equalsIgnoreCase("black")) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                } else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                }
                spinnerCountry.setSelection(99);
                AppLogger.e("item", "---------" + countryArray.get(position).getName());
                countryId = countryArray.get(position).getCountryId();
                if (countryArray.get(position).getCountryId().equalsIgnoreCase("IN")) {
                    getStateList(countryArray.get(position).getCountryId());
                    tilState.setVisibility(View.GONE);
                    edState.setText("");
                    lblState.setVisibility(View.VISIBLE);
                    spinnerState.setVisibility(View.VISIBLE);
                    view_state.setVisibility(View.VISIBLE);
                } else {
                    tilState.setVisibility(View.VISIBLE);
                    edState.setText(loginResponse.getData().getRegion());
                    lblState.setVisibility(View.GONE);
                    spinnerState.setVisibility(View.GONE);
                    view_state.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (themePreferences.getTheme().equalsIgnoreCase("black")) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                } else {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSave:
                boolean valid = validateContactInfo();
                if (valid) {
                    if (countryId.equalsIgnoreCase("IN")) {
                        if (spinnerState.getSelectedItem() == "Select State") {
                            CommonUtils.showInfoToast(EditContactInfoAct.this, "Please select state.");
                        } else {
                            editContactInfo("abc", customerId, Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edEmail.getText()).toString(), Objects.requireNonNull(edContact.getText()).toString(), Objects.requireNonNull(edAddress.getText()).toString(), countryId, spinnerState.getSelectedItem().toString(), Objects.requireNonNull(edCity.getText()).toString(), Objects.requireNonNull(edZipCode.getText()).toString(), Objects.requireNonNull(edPanCard.getText()).toString(), Objects.requireNonNull(edGstIn.getText()).toString());
                        }

                    }else{
                        if (TextUtils.isEmpty(Objects.requireNonNull(edState.getText()).toString())) {
                            edState.setError("Please enter state name.");
                            edState.requestFocus();
                        } else {
                            editContactInfo("abc", customerId, Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edEmail.getText()).toString(), Objects.requireNonNull(edContact.getText()).toString(), Objects.requireNonNull(edAddress.getText()).toString(), countryId, edState.getText().toString(), Objects.requireNonNull(edCity.getText()).toString(), Objects.requireNonNull(edZipCode.getText()).toString(), Objects.requireNonNull(edPanCard.getText()).toString(), Objects.requireNonNull(edGstIn.getText()).toString());
                        }
                    }
                }
            break;
        }
    }

    private boolean validateContactInfo() {
        if (!Validator.checkEmptyInputLayout(edFnm, getString(R.string.sign_up_please_enter_fnm))) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edLnm, getString(R.string.sign_up_please_enter_last_name))) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edEmail, getString(R.string.sign_up_please_enter_email))) {
            return false;
        }else if(!Validator.checkEmail(edEmail)){
            edEmail.requestFocus();
            edEmail.setError(getString(R.string.login_please_enter_valid_email));
            return false;
        } else if (!Validator.checkEmptyInputLayout(edContact, getString(R.string.sign_up_please_enter_contact_no))) {
            return false;
        } else if(!Validator.isValidPhoneNumber(Objects.requireNonNull(edContact.getText().toString()))){
            edContact.requestFocus();
            edContact.setError(getString(R.string.login_please_enter_valid_mobile));
            return false;
        }else if (!Validator.checkEmptyInputLayout(edAddress, getString(R.string.sign_up_please_enter_address))) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edCity, getString(R.string.sign_up_please_enter_city))) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edZipCode, getString(R.string.sign_up_please_enter_zip_code))) {
            return false;
        } else if (!Validator.isValidZip(Objects.requireNonNull(edZipCode.getText().toString()))){
            edZipCode.requestFocus();
            edZipCode.setError(getString(R.string.login_please_enter_valid_zip));
            return false;
        }
        /*else if (!Validator.checkEmptyInputLayout(edPanCard, getString(R.string.please_enter_pan_card_no))) {
            return false;
        } else
            return Validator.checkEmptyInputLayout(edGstIn, getString(R.string.please_enter_gstin_no));*/
        return true;
    }

    private void editContactInfo(String token, String customerId, String firstName, String lastName, String email, String telephone, String street, String countryId, String region, String city, String postCode, String panCardNo, String gstIn) {

        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.editContactInfo(token, customerId, firstName, lastName, email, telephone, street, countryId, region, city, postCode, panCardNo, gstIn);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                hideProgressDialog();
                String status = null;
                String message = null;
                if (response.isSuccessful())
                {
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        message = jsonObject.getString("message");
                        status = jsonObject.getString("status");

                        if(status.equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)){
        //                 Save data to session
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body());
                            AppLogger.e("login json","----------------"+json);
                            sharedPreferences.saveLoginData(json);
                            finish();
                            CommonUtils.showSuccessToast(EditContactInfoAct.this,message);
                        }else if(status.equalsIgnoreCase(AppConstants.STATUS_CODE_FAILED)){
                            CommonUtils.showErrorToast(EditContactInfoAct.this,message);
    //                        finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                String status, message = null;
               /* if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("status");
                        message = jsonObject.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AlertDialog alertDialog = new AlertDialog.Builder(EditContactInfoAct.this).create();
                    alertDialog.setTitle("Thank You!");
                    alertDialog.setMessage(message);
                    // Alert dialog button
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Alert dialog action goes here
                                    // onClick button code here
                                    dialog.dismiss();// use dismiss to cancel alert dialog
                                }
                            });
                    alertDialog.show();
                }*/
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e(TAG, "------------" + t.getMessage());
                hideProgressDialog();
            }
        });
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

    private void loadState() {
        List<String> spinnerArray = new ArrayList<>();

        if (!stateArray.isEmpty()) {
//            spinnerArray.add(0,"Select State");
            for (int i = 0; i <= stateArray.size() - 1; i++) {
                spinnerArray.add(stateArray.get(i).getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerState.setAdapter(adapter);
            String compareValue = loginResponse.getData().getRegion();
            if (compareValue != null) {
                int spinnerPosition = adapter.getPosition(compareValue);
                spinnerState.setSelection(spinnerPosition);
            }
        }
    }

    public class ViewDialog {

        void showDialog(final Activity activity, String title, String msg, String btnYesText, String btnNoText, String isVisible) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_alert);
            Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView tvTitle = dialog.findViewById(R.id.tvTitle);
            TextView tvMsg = dialog.findViewById(R.id.tvMsg);
            Button btnYes = dialog.findViewById(R.id.btnYes);
            Button btnNo =  dialog.findViewById(R.id.btnNo);
            tvTitle.setText(title);
            tvMsg.setText(msg);
            btnYes.setText(btnYesText);
            btnNo.setText(btnNoText);
            if (isVisible.equalsIgnoreCase("1")){
                btnNo.setVisibility(View.GONE);
            }

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    activity.finish();
                }
            });

            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            dialog.show();
        }
    }
}
