package com.dealermela.authentication.myaccount.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.dialog.ChangePasswordDialogClass;
import com.dealermela.authentication.myaccount.model.AddressManageResponse;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.SharedPreferences;
import com.dealermela.util.Validator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class EditProfileAct extends DealerMelaBaseActivity implements View.OnClickListener {

    private TextView tvContactInformation;
    private TextView tvBillingAddress;
    private TextView tvShippingAddress;
    //    Billing Address
    private TextView tvBName, tvBAddress1, tvBAddress2, tvBCity, tvBCountry, tvBTelephone;
    //    Shipping Address
    private TextView tvSName, tvSAddress1, tvSAddress2, tvSCity, tvSCountry, tvSTelephone;
    private Button btnChangePassword, btnManagePassword, btnAddBankDetail, btnManageBankDetail, btnSave, btnCancel;
    private LinearLayout linBank;
    private ScrollView scrollViewEditProfile;
    private LoginResponse loginResponse;
    private AddressManageResponse addressManageResponse;
    private TextInputEditText edBankName, edBankAccNo, edBankAccHolderName, edIfscCode, edBranchName;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvReferralCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_edit_profile;
    }

    @Override
    public void init() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        bindToolBar("Edit Profile");
        tvContactInformation = findViewById(R.id.tvContactInformation);
        tvBillingAddress = findViewById(R.id.tvBillingAddress);
        tvShippingAddress = findViewById(R.id.tvShippingAddress);
        tvName = findViewById(R.id.tvEName);
        tvEmail = findViewById(R.id.tvEEmail);
        tvReferralCode = findViewById(R.id.tvReferralCode);

        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnManagePassword = findViewById(R.id.btnManagePassword);
        btnAddBankDetail = findViewById(R.id.btnAddBankDetail);
        btnManageBankDetail = findViewById(R.id.btnManageBankDetail);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        tvBName = findViewById(R.id.tvBName);
        tvBAddress1 = findViewById(R.id.tvBAddress1);
        tvBAddress2 = findViewById(R.id.tvBAddress2);
        tvBCity = findViewById(R.id.tvBCity);
        tvBCountry = findViewById(R.id.tvBCountry);
        tvBTelephone = findViewById(R.id.tvBTelephone);

        tvSName = findViewById(R.id.tvSName);
        tvSAddress1 = findViewById(R.id.tvSAddress1);
        tvSAddress2 = findViewById(R.id.tvSAddress2);
        tvSCity = findViewById(R.id.tvSCity);
        tvSCountry = findViewById(R.id.tvSCountry);
        tvSTelephone = findViewById(R.id.tvSTelephone);

        scrollViewEditProfile = findViewById(R.id.scrollViewEditProfile);

        edBankName = findViewById(R.id.edBankName);
        edBankAccNo = findViewById(R.id.edBankAccNo);
        edBankAccHolderName = findViewById(R.id.edBankAccHolderName);
        edIfscCode = findViewById(R.id.edIfscCode);
        edBranchName = findViewById(R.id.edBranchName);
        linBank = findViewById(R.id.linBank);
    }

    @Override
    public void postInitView() {
    }

    @Override
    public void addListener() {
        tvContactInformation.setOnClickListener(this);
        tvBillingAddress.setOnClickListener(this);
        tvShippingAddress.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        btnManagePassword.setOnClickListener(this);
        btnAddBankDetail.setOnClickListener(this);
        btnManageBankDetail.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Gson gson = new Gson();
        switch (v.getId()) {
            case R.id.tvContactInformation:
                startNewActivity(EditContactInfoAct.class);
                break;
            case R.id.tvBillingAddress:
                intent = new Intent(EditProfileAct.this, EditAddressAct.class);
                String defaultBilling = gson.toJson(addressManageResponse.getDefaultBilling());
                intent.putExtra("addressStatus", "1");
                intent.putExtra("defaultBilling", defaultBilling);
                startNewActivityWithIntent(intent);
                break;
            case R.id.tvShippingAddress:
                intent = new Intent(EditProfileAct.this, EditAddressAct.class);
                String defaultShipping = gson.toJson(addressManageResponse.getDefaultShipping());
                intent.putExtra("addressStatus", "2");
                intent.putExtra("defaultShipping", defaultShipping);
                startNewActivityWithIntent(intent);
                break;
            case R.id.btnChangePassword:
                ChangePasswordDialogClass changePasswordDialogClass = new ChangePasswordDialogClass(EditProfileAct.this);
                changePasswordDialogClass.show();
                Objects.requireNonNull(changePasswordDialogClass.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                break;
            case R.id.btnManagePassword:
                startNewActivity(ManageAddressAct.class);
                break;
            case R.id.btnAddBankDetail:
                if (linBank.getVisibility() == View.VISIBLE) {
                    linBank.setVisibility(View.GONE);
                } else {
                    linBank.setVisibility(View.VISIBLE);
                    focusOnView();
                }
                break;
            case R.id.btnSave:
                boolean valid = validateBankDetail();
                if (valid) {
                    addBank(customerId, Objects.requireNonNull(edBankName.getText()).toString(), Objects.requireNonNull(edBankAccNo.getText()).toString(), Objects.requireNonNull(edBankAccHolderName.getText()).toString(), Objects.requireNonNull(edIfscCode.getText()).toString(), Objects.requireNonNull(edBranchName.getText()).toString());
                }
                break;
            case R.id.btnCancel:
                linBank.setVisibility(View.GONE);
                edBankName.getText().clear();
                edBankAccNo.getText().clear();
                edBankAccHolderName.getText().clear();
                edIfscCode.getText().clear();
                edBranchName.getText().clear();
                break;

            case R.id.btnManageBankDetail:
                startNewActivity(ManageBankAct.class);
                break;
        }
    }

    private void addBank(String customerId, String bankName, String bankAccountNumber, String bankAccountHolder, String ifscCode, String branchName) {
        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.addBankDetail(customerId, bankName, bankAccountNumber, bankAccountHolder, ifscCode, branchName);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                AppLogger.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                String status = null, message = null;
                hideProgressDialog();
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("status");
                        message = jsonObject.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    assert status != null;
                    if (status.equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        edBankName.setText("");
                        edBankAccNo.setText("");
                        edBankAccHolderName.setText("");
                        edIfscCode.setText("");
                        edBranchName.setText("");
                        ViewDialog viewDialog = new ViewDialog();
                        viewDialog.showDialog(EditProfileAct.this, "Thank you!", message, "OK", "", "1");

                        linBank.setVisibility(View.GONE);

                        /*AlertDialog alertDialog = new AlertDialog.Builder(EditProfileAct.this).create();
                        alertDialog.setTitle("Thank You!");
                        alertDialog.setMessage(message);
                        alertDialog.setCancelable(false);
                        // Alert dialog button
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        edBankName.setText("");
                                        edBankAccNo.setText("");
                                        edBankAccHolderName.setText("");
                                        edIfscCode.setText("");
                                        edBranchName.setText("");
                                        startNewActivity(ManageBankAct.class);
                                    }
                                });
                        alertDialog.show();*/
                    } else {
                        CommonUtils.showErrorToast(EditProfileAct.this, message);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("", "------------" + t.getMessage());
                hideProgressDialog();
            }
        });
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
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
//                        if (!response.body().getData().isEmpty()) {
                            addressManageResponse = response.body();
                            tvBName.setText(response.body().getDefaultBilling().getFirstname() + " " + response.body().getDefaultBilling().getLastname());
                            tvBAddress1.setText(response.body().getDefaultBilling().getStreet());
                            tvBCity.setText(response.body().getDefaultBilling().getCity() + "," + response.body().getDefaultBilling().getRegion() + "," + response.body().getDefaultBilling().getPostcode());
                            tvBCountry.setText(response.body().getDefaultBilling().getCountry());
                            tvBTelephone.setText("T: " + response.body().getDefaultBilling().getTelephone());

                            tvSName.setText(response.body().getDefaultShipping().getFirstname() + " " + response.body().getDefaultShipping().getLastname());
                            tvSAddress1.setText(response.body().getDefaultShipping().getStreet());
                            tvSCity.setText(response.body().getDefaultShipping().getCity() + "," + response.body().getDefaultShipping().getRegion() + "," + response.body().getDefaultShipping().getPostcode());
                            tvSCountry.setText(response.body().getDefaultShipping().getCountry());
                            tvSTelephone.setText("T: " + response.body().getDefaultShipping().getTelephone());
//                        } else {
//
//                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddressManageResponse> call, @NonNull Throwable t) {
                AppLogger.e(TAG, "------------" + t.getMessage());
                hideProgressDialog();
            }

        });
    }

    private void focusOnView() {
        scrollViewEditProfile.post(new Runnable() {
            @Override
            public void run() {
                scrollViewEditProfile.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private boolean validateBankDetail() {
        if (!Validator.checkEmptyInputLayout(edBankName, "Please enter bank name.")) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edBankAccNo, "Please enter bank account number.")) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edBankAccHolderName, "Please enter bank account holder name.")) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edIfscCode, "Please enter bankIFSC Code.")) {
            return false;
        } else return Validator.checkEmptyInputLayout(edBranchName, "Please enter branch name.");
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        getAddress(customerId);
        SharedPreferences sharedPreferences = new SharedPreferences(EditProfileAct.this);
        Gson gson = new Gson();
        loginResponse = gson.fromJson(sharedPreferences.getLoginData(), LoginResponse.class);
        tvName.setText(loginResponse.getData().getFirstname() + " " + loginResponse.getData().getLastname());
        tvEmail.setText(loginResponse.getData().getEmail());

        if(loginResponse.getData().getRefcode() == null){
            tvReferralCode.setText(" N/A");
        }else {
            tvReferralCode.setText(" " + loginResponse.getData().getRefcode());
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
                    startNewActivity(ManageBankAct.class);
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
