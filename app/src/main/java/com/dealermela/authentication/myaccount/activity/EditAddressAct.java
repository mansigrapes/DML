package com.dealermela.authentication.myaccount.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.model.AddressManageResponse;
import com.dealermela.authentication.myaccount.model.CountryResponse;
import com.dealermela.authentication.myaccount.model.StateResponse;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
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

public class EditAddressAct extends DealerMelaBaseActivity implements View.OnClickListener {

    private TextInputLayout tilState;
    private TextInputEditText edFnm, edLnm, edTelephone, edAddress1, edAddress2, edZipCode, edState, edCity;
    private Button btnSave;
    private Spinner spinnerState, spinnerCountry;
    private View viewState;
    private List<CountryResponse.Datum> countryArray = new ArrayList<>();
    private List<StateResponse.Datum> stateArray = new ArrayList<>();
    private String countryId = "";
    private String regionId = "";

    private AddressManageResponse.DefaultBilling defaultBilling;
    private AddressManageResponse.DefaultShipping defaultShipping;
    private AddressManageResponse.Datum additionalAddress;

    private String status = "";
    private CheckBox checkBoxDefaultBilling, checkBoxShippingBilling;
    private ThemePreferences themePreferences;
    private TextView lblState;

    String billingFlag;
    String shippingFlag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_edit_address;
    }

    @Override
    public void init() {
        closeOptionsMenu();
        themePreferences = new ThemePreferences(EditAddressAct.this);
        Intent intent = getIntent();
        status = intent.getStringExtra("addressStatus");
        if (status.equalsIgnoreCase("1")) {
            bindToolBar("Edit Address");
            Gson gson = new Gson();
            String target = intent.getStringExtra("defaultBilling");
            defaultBilling = gson.fromJson(target, AddressManageResponse.DefaultBilling.class);
            AppLogger.e("fnm", defaultBilling.getFirstname());

        } else if (intent.getStringExtra("addressStatus").equalsIgnoreCase("2")) {
            bindToolBar("Edit Address");
            Gson gson = new Gson();
            String target = intent.getStringExtra("defaultShipping");
            defaultShipping = gson.fromJson(target, AddressManageResponse.DefaultShipping.class);
            AppLogger.e("fnm", defaultShipping.getFirstname());
        } else if (intent.getStringExtra("addressStatus").equalsIgnoreCase("3")) {
            bindToolBar("Edit Address");
            Gson gson = new Gson();
            String target = intent.getStringExtra("defaultShipping");
            additionalAddress = gson.fromJson(target, AddressManageResponse.Datum.class);
        } else if (intent.getStringExtra("addressStatus").equalsIgnoreCase("4")) {
            bindToolBar("Add Address");
        } else if (intent.getStringExtra("addressStatus").equalsIgnoreCase("5")) {
            bindToolBar("Add Address");
        }
    }

    @Override
    public void initView() {

        lblState = findViewById(R.id.lblState);
        edFnm = findViewById(R.id.edFnm);
        edLnm = findViewById(R.id.edLnm);
        edTelephone = findViewById(R.id.edTelephone);
        edAddress1 = findViewById(R.id.edAddress1);
//        edAddress2 = findViewById(R.id.edAddress2);
        edZipCode = findViewById(R.id.edZipCode);
        edState = findViewById(R.id.edState);
        edCity = findViewById(R.id.edCity);

        btnSave = findViewById(R.id.btnSave);
        viewState = findViewById(R.id.viewState);

        spinnerState = findViewById(R.id.spinnerState);
        spinnerCountry = findViewById(R.id.spinnerCountry);

        tilState = findViewById(R.id.tilState);
        checkBoxDefaultBilling = findViewById(R.id.checkBoxDefaultBilling);
        checkBoxShippingBilling = findViewById(R.id.checkBoxShippingBilling);
    }

    @Override
    public void postInitView() {
        String compareValue = "";
        if (status.equalsIgnoreCase("1")) {
            edFnm.setText(defaultBilling.getFirstname());
            edLnm.setText(defaultBilling.getLastname());
            edTelephone.setText(defaultBilling.getTelephone());
            edAddress1.setText(defaultBilling.getStreet());
//            edAddress2.setText(defaultBilling.getStreet1());
            edZipCode.setText(defaultBilling.getPostcode());
            edCity.setText(defaultBilling.getCity());
            edState.setText(defaultBilling.getRegion());
            compareValue = defaultBilling.getCountryId();

            checkBoxShippingBilling.setVisibility(View.GONE);
            checkBoxDefaultBilling.setVisibility(View.GONE);

        } else if (status.equalsIgnoreCase("2")) {
            edFnm.setText(defaultShipping.getFirstname());
            edLnm.setText(defaultShipping.getLastname());
            edTelephone.setText(defaultShipping.getTelephone());
            edAddress1.setText(defaultShipping.getStreet());
//            edAddress2.setText(defaultShipping.getStreet1());
            edState.setText(defaultShipping.getRegion());
            edZipCode.setText(defaultShipping.getPostcode());
            edCity.setText(defaultShipping.getCity());
            compareValue = defaultShipping.getCountryId();

            checkBoxShippingBilling.setVisibility(View.GONE);
            checkBoxDefaultBilling.setVisibility(View.GONE);

        } else if (status.equalsIgnoreCase("3")) {
            edFnm.setText(additionalAddress.getFirstname());
            edLnm.setText(additionalAddress.getLastname());
            edTelephone.setText(additionalAddress.getTelephone());
            edAddress1.setText(additionalAddress.getStreet());
//            edAddress2.setText(additionalAddress.getStreet1());
            edState.setText(additionalAddress.getRegion());
            edZipCode.setText(additionalAddress.getPostcode());
            edCity.setText(additionalAddress.getCity());
            compareValue = additionalAddress.getCountryId();

        } else if (status.equalsIgnoreCase("5")) {
            checkBoxShippingBilling.setVisibility(View.GONE);
            checkBoxDefaultBilling.setVisibility(View.GONE);
        }

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

            if (compareValue != "") {
                int spinnerPosition = adapter1.getPosition(compareValue);
                spinnerCountry.setSelection(spinnerPosition);
            }
        } else {
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
                    lblState.setVisibility(View.VISIBLE);
                    spinnerState.setVisibility(View.VISIBLE);
                    getStateList(countryArray.get(position).getCountryId());
                    tilState.setVisibility(View.GONE);
                    edState.setText("");
                    spinnerState.setVisibility(View.VISIBLE);
                    viewState.setVisibility(View.VISIBLE);
                } else {
                    lblState.setVisibility(View.GONE);
                    spinnerState.setVisibility(View.GONE);
                    tilState.setVisibility(View.VISIBLE);
//                    edState.setText(defaultBilling.getRegion());
                    spinnerState.setVisibility(View.GONE);
                    viewState.setVisibility(View.GONE);
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
                regionId = stateArray.get(position).getRegionId();
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

    private void addAddress(String customerId, String addressBillingFlag, String addressShippingFlag, String firstName, String lastName, String street1, String street2, String city, String regionId, String region, String postcode, String countryId, String telephone) {

        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.addNewAddress(customerId, addressBillingFlag, addressShippingFlag, firstName, lastName, street1, street2, city, regionId, region, postcode, countryId, telephone);
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ViewDialog viewDialog = new ViewDialog();
                    viewDialog.showDialog(EditAddressAct.this, "Thank you!", message, "OK", "", "1");
                }
            }
            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e(TAG, "------------" + t.getMessage());
                hideProgressDialog();
            }
        });
    }

    private void editBillingAddress(String customerId, String addressId, String firstName, String lastName, String street1, String street2, String city, String regionId, String region, String postcode, String countryId, String telephone) {

        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.editDefaultBilling(customerId, addressId, firstName, lastName, street1, street2, city, regionId, region, postcode, countryId, telephone);
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
                        ViewDialog viewDialog = new ViewDialog();
                        message = jsonObject.getString("message");
                        if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                            viewDialog.showDialog(EditAddressAct.this, "Thank you!", message, "OK", "", "1");
                        } else if(jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_FAILED)){
//                            CommonUtils.showErrorToast(EditAddressAct.this, jsonObject.getString("status"));
                            CommonUtils.showErrorToast(EditAddressAct.this, message);
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

    private void editShippingAddress(String customerId, String addressId, String firstName, String lastName, String street1, String street2, String city, String regionId, String region, String postcode, String countryId, String telephone) {

        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.editDefaultShipping(customerId, addressId, firstName, lastName, street1, street2, city, regionId, region, postcode, countryId, telephone);
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
                        if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                            ViewDialog viewDialog = new ViewDialog();
                            viewDialog.showDialog(EditAddressAct.this, "Thank you!", message, "OK", "", "1");
                        } else if(jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_FAILED)){
//                            CommonUtils.showErrorToast(EditAddressAct.this, jsonObject.getString("status"));
                            CommonUtils.showErrorToast(EditAddressAct.this, message);
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

    private void editAdditionalAddress(String customerId, String addressId, String addressBillingFlag, String addressShippingFlag, String firstName, String lastName, String street1, String street2, String city, String regionId, String region, String postcode, String countryId, String telephone) {
        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.editAdditionalBilling(customerId, addressId, addressBillingFlag, addressShippingFlag, firstName, lastName, street1, street2, city, regionId, region, postcode, countryId, telephone);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                hideProgressDialog();
                String status_success = null, message = null;
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status_success = jsonObject.getString("status");
                        message = jsonObject.getString("message");
                        if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                            ViewDialog viewDialog = new ViewDialog();
                            viewDialog.showDialog(EditAddressAct.this, "Thank you!", message, "OK", "", "1");
                        } else if(jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_FAILED)) {
//                            CommonUtils.showErrorToast(EditAddressAct.this, jsonObject.getString("status"));
                            CommonUtils.showErrorToast(EditAddressAct.this, message);
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
            lblState.setVisibility(View.VISIBLE);
            spinnerState.setVisibility(View.VISIBLE);
//            spinnerArray.add(0,"Select State");

//            for (int i = 0; i <= stateArray.size() - 1; i++) {   // when select state is added at 0 position
            for (int i = 0; i <= stateArray.size() - 1; i++) {
                spinnerArray.add(stateArray.get(i).getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerState.setAdapter(adapter);
            String compareValue = "";
            if (status.equalsIgnoreCase("1")) {
                compareValue = defaultBilling.getRegion();
            } else if (status.equalsIgnoreCase("2")) {
                compareValue = defaultShipping.getRegion();
            } else if (status.equalsIgnoreCase("3")) {
                compareValue = additionalAddress.getRegion();
            }
            if (compareValue != null) {
                int spinnerPosition = adapter.getPosition(compareValue);
                spinnerState.setSelection(spinnerPosition);
            }
        } else {
            lblState.setVisibility(View.GONE);
            spinnerState.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                boolean valid = validateAddress();
                if (valid) {
                    if (checkBoxDefaultBilling.isChecked()) {
                        billingFlag = "1";
                    } else {
                        billingFlag = "0";
                    }

                    if (checkBoxShippingBilling.isChecked()) {
                        shippingFlag = "1";
                    } else {
                        shippingFlag = "0";
                    }

                    if (status.equalsIgnoreCase("1")) {
                        if (countryId.equalsIgnoreCase("IN")) {
//                            editBillingAddress(customerId, defaultBilling.getEntityId(), Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edAddress1.getText()).toString(), Objects.requireNonNull(edAddress2.getText()).toString(), Objects.requireNonNull(edCity.getText()).toString(), regionId, spinnerState.getSelectedItem().toString(), Objects.requireNonNull(edZipCode.getText()).toString(), countryId, Objects.requireNonNull(edTelephone.getText()).toString());
                            if (spinnerState.getSelectedItem() == "Select State") {
                                CommonUtils.showInfoToast(EditAddressAct.this, "Please select state.");
                            } else {
                                editBillingAddress(customerId, defaultBilling.getEntityId(), Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edAddress1.getText()).toString(), "", Objects.requireNonNull(edCity.getText()).toString(), regionId, spinnerState.getSelectedItem().toString(), Objects.requireNonNull(edZipCode.getText()).toString(), countryId, Objects.requireNonNull(edTelephone.getText()).toString());
                            }
                        } else {
                            if (TextUtils.isEmpty(Objects.requireNonNull(edState.getText()).toString())) {
                                edState.setError("Please enter state name.");
                                edState.requestFocus();
                            } else {
                                editBillingAddress(customerId, defaultBilling.getEntityId(), Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edAddress1.getText()).toString(), "", Objects.requireNonNull(edCity.getText()).toString(), regionId, Objects.requireNonNull(edState.getText()).toString(), Objects.requireNonNull(edZipCode.getText()).toString(), countryId, Objects.requireNonNull(edTelephone.getText()).toString());
                            }
                        }
                    } else if (status.equalsIgnoreCase("2")) {
                        if (countryId.equalsIgnoreCase("IN")) {
                            if (spinnerState.getSelectedItem() == "Select State") {
                                CommonUtils.showInfoToast(EditAddressAct.this, "Please select state.");
                            } else {
                                editShippingAddress(customerId, defaultShipping.getEntityId(), Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edAddress1.getText()).toString(), "", Objects.requireNonNull(edCity.getText()).toString(), regionId, spinnerState.getSelectedItem().toString(), Objects.requireNonNull(edZipCode.getText()).toString(), countryId, Objects.requireNonNull(edTelephone.getText()).toString());
                            }
                        } else {
                            if (TextUtils.isEmpty(Objects.requireNonNull(edState.getText()).toString())) {
                                edState.setError("Please enter state name.");
                                edState.requestFocus();
                            } else {
                                editShippingAddress(customerId, defaultShipping.getEntityId(), Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edAddress1.getText()).toString(), "", Objects.requireNonNull(edCity.getText()).toString(), regionId, Objects.requireNonNull(edState.getText()).toString(), Objects.requireNonNull(edZipCode.getText()).toString(), countryId, Objects.requireNonNull(edTelephone.getText()).toString());
                            }
                        }
                    } else if (status.equalsIgnoreCase("3")) {
                        if (countryId.equalsIgnoreCase("IN")) {
                            if (spinnerState.getSelectedItem() == "Select State") {
                                CommonUtils.showInfoToast(EditAddressAct.this, "Please select state.");
                            } else {
                                editAdditionalAddress(customerId, additionalAddress.getEntityId(), billingFlag, shippingFlag, Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edAddress1.getText()).toString(), "", Objects.requireNonNull(edCity.getText()).toString(), regionId, spinnerState.getSelectedItem().toString(), Objects.requireNonNull(edZipCode.getText()).toString(), countryId, Objects.requireNonNull(edTelephone.getText()).toString());
                            }
                        } else {
                            if (TextUtils.isEmpty(Objects.requireNonNull(edState.getText()).toString())) {
                                edState.setError("Please enter state name.");
                                edState.requestFocus();
                            } else {
                                editAdditionalAddress(customerId, additionalAddress.getEntityId(), billingFlag, shippingFlag, Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edAddress1.getText()).toString(), "", Objects.requireNonNull(edCity.getText()).toString(), regionId, Objects.requireNonNull(edState.getText()).toString(), Objects.requireNonNull(edZipCode.getText()).toString(), countryId, Objects.requireNonNull(edTelephone.getText()).toString());
                            }
                        }
                    } else if (status.equalsIgnoreCase("4")) {
                        if (countryId.equalsIgnoreCase("IN")) {
                            if (spinnerState.getSelectedItem() == "Select State") {
                                CommonUtils.showInfoToast(EditAddressAct.this, "Please select state.");
                            } else {
                                addAddress(customerId, billingFlag, shippingFlag, Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edAddress1.getText()).toString(), "", Objects.requireNonNull(edCity.getText()).toString(), regionId, spinnerState.getSelectedItem().toString(), Objects.requireNonNull(edZipCode.getText()).toString(), countryId, Objects.requireNonNull(edTelephone.getText()).toString());
                            }
                        } else {
                            if (TextUtils.isEmpty(Objects.requireNonNull(edState.getText()).toString())) {
                                edState.setError("Please enter state name.");
                                edState.requestFocus();
                            } else {
                                addAddress(customerId, billingFlag, shippingFlag, Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edAddress1.getText()).toString(), "", Objects.requireNonNull(edCity.getText()).toString(), regionId, Objects.requireNonNull(edState.getText()).toString(), Objects.requireNonNull(edZipCode.getText()).toString(), countryId, Objects.requireNonNull(edTelephone.getText()).toString());
                            }
                        }
                    } else if (status.equalsIgnoreCase("5")) {
                        if (countryId.equalsIgnoreCase("IN")) {
                            if (spinnerState.getSelectedItem() == "Select State") {
                                CommonUtils.showInfoToast(EditAddressAct.this, "Please select state.");
                            } else {
                                addAddress(customerId, billingFlag, shippingFlag, Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edAddress1.getText()).toString(), "", Objects.requireNonNull(edCity.getText()).toString(), regionId, spinnerState.getSelectedItem().toString(), Objects.requireNonNull(edZipCode.getText()).toString(), countryId, Objects.requireNonNull(edTelephone.getText()).toString());
                            }
                        } else {
                            if (TextUtils.isEmpty(Objects.requireNonNull(edState.getText()).toString())) {
                                edState.setError("Please enter state name.");
                                edState.requestFocus();
                            } else {
                                addAddress(customerId, billingFlag, shippingFlag, Objects.requireNonNull(edFnm.getText()).toString(), Objects.requireNonNull(edLnm.getText()).toString(), Objects.requireNonNull(edAddress1.getText()).toString(), "", Objects.requireNonNull(edCity.getText()).toString(), regionId, Objects.requireNonNull(edState.getText()).toString(), Objects.requireNonNull(edZipCode.getText()).toString(), countryId, Objects.requireNonNull(edTelephone.getText()).toString());
                            }
                        }
                    }
                }
                break;
        }
    }

    private boolean validateAddress() {
        if (!Validator.checkEmptyInputLayout(edFnm, getString(R.string.sign_up_please_enter_fnm))) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edLnm, getString(R.string.sign_up_please_enter_last_name))) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edTelephone, "Please enter telephone")) {
            return false;
        } else if(!Validator.isValidPhoneNumber(Objects.requireNonNull(edTelephone.getText()).toString())){
            edTelephone.requestFocus();
            edTelephone.setError(getString(R.string.login_please_enter_valid_mobile));
            return false;
        } else if (!Validator.checkEmptyInputLayout(edAddress1, getString(R.string.sign_up_please_enter_address))) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edZipCode, getString(R.string.sign_up_please_enter_zip_code))) {
            return false;
        } else if(!Validator.isValidZip(Objects.requireNonNull(edZipCode.getText()).toString())){
            edZipCode.requestFocus();
            edZipCode.setError(getString(R.string.login_please_enter_valid_zip));
            return false;
        }
        else
            return Validator.checkEmptyInputLayout(edCity, getString(R.string.sign_up_please_enter_city));
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
            Button btnNo = dialog.findViewById(R.id.btnNo);
            tvTitle.setText(title);
            tvMsg.setText(msg);
            btnYes.setText(btnYesText);
            btnNo.setText(btnNoText);
            if (isVisible.equalsIgnoreCase("1")) {
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
