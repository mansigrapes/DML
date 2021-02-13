package com.dealermela.authentication.myaccount.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dealermela.R;
import com.dealermela.authentication.myaccount.activity.ForgotPasswordAct;
import com.dealermela.authentication.myaccount.activity.SignUpAct;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.home.activity.MainActivity;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.SharedPreferences;
import com.dealermela.util.Validator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.dealermela.home.activity.MainActivity.customerId;
import static com.dealermela.home.activity.MainActivity.displayversion;

public class LoginDialog  extends Dialog implements View.OnClickListener {

    private final Activity activity;
    public Dialog d;
    //    EditText edEmail;
//            , edPassword;
    TextInputEditText edEmail,edPassword;
    Button btnLogin;
    private boolean flag = false;
    private SharedPreferences sharedPreferences;
    TextView tvForgotPwd, tvNewAccount;
    private boolean doubleBackToExitPressedOnce = false;

    public LoginDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d = new Dialog(activity);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_login_popup);
//        edEmail = findViewById(R.id.edemail);
        edEmail = findViewById(R.id.edemail1);
//        edPassword = findViewById(R.id.edPassword);
        edPassword = findViewById(R.id.edPassword1);
        tvForgotPwd = findViewById(R.id.tvForgotPwd);
        tvNewAccount = findViewById(R.id.tvNewAccount);

        btnLogin = findViewById(R.id.btnLogin);

        tvForgotPwd.setOnClickListener(this);
        tvNewAccount.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        sharedPreferences = new SharedPreferences(activity);
//        tvForgotPwd.s
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin:

                if(!Validator.checkEmpty(edEmail,"This is required") && !Validator.checkEmpty(edPassword,"This is required")) {

                }else {
                    validateLogin();
                }
                break;
//            case R.id.tvRemPwd:
//                break;
            case R.id.tvForgotPwd:
                activity.startActivity(new Intent(activity, ForgotPasswordAct.class));
                break;
            case R.id.tvNewAccount:
                activity.startActivity(new Intent(activity, SignUpAct.class));
                break;
        }
    }

    private void validateLogin() {
        //noinspection StatementWithEmptyBody
        if (!Validator.checkEmpty(edEmail, "Please enter email.")) {

        } else if (!Validator.checkEmail(edEmail)) {
            edEmail.requestFocus();
            edEmail.setError("Please enter valid email.");
        } else //noinspection StatementWithEmptyBody
            if (!Validator.checkEmpty(edPassword, "Please enter password.")) {

            } else //noinspection StatementWithEmptyBody
                if (!Validator.checkPasswordLength(edPassword)) {

                } else {
                    if (flag) {
                        sharedPreferences.saveRemember("true");
                    } else {
                        sharedPreferences.saveRemember("false");
                    }
                    checkLogin(edEmail.getText().toString(), edPassword.getText().toString(), "ASDSds56445df5g4d5f4gd5fg5f4g5ffd", CommonUtils.getDeviceID(activity));
                }
    }

    private void checkLogin(String email, String password, String token, String deviceId) {
//        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> callApi = apiInterface.Login(email, password, token, deviceId);
        callApi.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                AppLogger.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
//                hideProgressDialog();
                String message=null, status=null;
                if(response.isSuccessful()) {
//                    try {
//                            JSONObject jsonObject=new JSONObject(String.valueOf(response.body()));
                    //                      status=jsonObject.getString("status");
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS))
//                        if(jsonObject.getString("status").equalsIgnoreCase("success"))
                    {
                        ////                    Save data to session
                        Gson gson = new Gson();
                        String json = gson.toJson(response.body());
                        AppLogger.e(AppConstants.RESPONSE, "-----------------" + json);

                        sharedPreferences.saveLoginData(json);
                        sharedPreferences.saveEmail(edEmail.getText().toString().trim());
                        sharedPreferences.savePassword(edPassword.getText().toString().trim());
                        customerId = response.body().getData().getEntityId();
                        sharedPreferences.saveBillingAddress(response.body().getData().getDefaultBillingNew().getFirstname() + " " + response.body().getData().getDefaultBillingNew().getLastname() + ",\n" + response.body().getData().getDefaultBillingNew().getStreet() + ",\n" + response.body().getData().getDefaultBillingNew().getCity() + ", " + response.body().getData().getDefaultBillingNew().getRegion() + ", " + response.body().getData().getDefaultBillingNew().getPostcode() + ",\n" + response.body().getData().getDefaultBillingNew().getCountryId() + "\nT: " + response.body().getData().getDefaultBillingNew().getTelephone());
                        sharedPreferences.saveShipping(response.body().getData().getDefaultShippingNew().getFirstname() + " " + response.body().getData().getDefaultBillingNew().getLastname() + ",\n" + response.body().getData().getDefaultBillingNew().getStreet() + ",\n" + response.body().getData().getDefaultBillingNew().getCity() + ", " + response.body().getData().getDefaultBillingNew().getRegion() + ", " + response.body().getData().getDefaultBillingNew().getPostcode() + ",\n" + response.body().getData().getDefaultBillingNew().getCountryId() + "\nT: " + response.body().getData().getDefaultBillingNew().getTelephone());
                        d.hide();
//                        activity.finish();
                        d.dismiss();
                        activity.startActivity(new Intent(activity, MainActivity.class));
                    } else if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_FAILED)) {
//                            CommonUtils.showErrorToast(LoginAct.this, jsonObject.getString("message"));
//                            AppLogger.e(AppConstants.RESPONSE, "-----------------" + jsonObject.getString("message"));
                        CommonUtils.showErrorToast(activity, response.body().getMessage());
                        AppLogger.e(AppConstants.RESPONSE, "-----------------" + response.body().getMessage());
//                        activity.finish();
////                        CommonUtils.showErrorToast(LoginAct.this, message);
                    } else {
//                            CommonUtils.showErrorToast(LoginAct.this, jsonObject.getString("message"));
//                            AppLogger.e(AppConstants.RESPONSE, "-----------------" + jsonObject.getString("message"));
//                        activity.finish();
                        CommonUtils.showErrorToast(activity, response.body().getMessage());
                        AppLogger.e(AppConstants.RESPONSE, "-----------------" + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                CommonUtils.showErrorToast(activity,"Something want to wrong please try again.");
                AppLogger.e(TAG, "------------" + t.getMessage());
            }
        });
    }

    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
            if (doubleBackToExitPressedOnce) {  //if label is true than exit from the app
                activity.finishAffinity();
//                super.onBackPressed();        //before code
//                return;
            }

            this.doubleBackToExitPressedOnce = true;
            CommonUtils.showWarningToast(activity, activity.getString(R.string.double_back));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

            if(displayversion == 1){
                displayversion = 0;
            }
            AppLogger.e("Flag Of Display version dialog at app close time:","----"+ displayversion);
//        }
    }
}

