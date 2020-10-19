package com.dealermela.authentication.myaccount.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.dealermela.R;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.Validator;
import com.google.gson.JsonObject;
import com.ligl.android.widget.iosdialog.IOSDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class ChangePasswordDialogClass extends Dialog implements View.OnClickListener {

    private final Activity activity;
    public Dialog d;
    private TextInputEditText edCurrentPassword, edNewPassword, edConfirmPassword;

    public ChangePasswordDialogClass(Activity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d= new Dialog(activity);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_password);
        edCurrentPassword = findViewById(R.id.edCurrentPassword);
        edNewPassword = findViewById(R.id.edNewPassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);
        Button btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangePassword:
                boolean valid = validateChangePassword();
                if (valid) {
                    changePassword(customerId, Objects.requireNonNull(edCurrentPassword.getText()).toString(), Objects.requireNonNull(edNewPassword.getText()).toString());
                }
                break;
            default:
                break;
        }
    }

    private void changePassword(String customerId, String currentPassword, String newPassword) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Change Password");
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.changePassword(customerId, currentPassword, newPassword);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                AppLogger.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                progressDialog.dismiss();
                String status = null, message = null;

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
                        new IOSDialog.Builder(activity)
                                .setTitle("Thank You!")
                                .setMessage(message)
                                .setCancelable(false)
                                .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        dialog.dismiss();
                                        dismiss();
//                                        SharedPreferences sharedPreferences = new SharedPreferences(activity);
//                                        sharedPreferences.saveLoginData("");
//                                        activity.startActivity(new Intent(activity, LoginAct.class));
//                                        activity.finishAffinity();
                                    }
                                })
                                .show();

                       /* AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
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
                                        SharedPreferences sharedPreferences=new SharedPreferences(activity);
                                        sharedPreferences.saveLoginData("");
                                        activity.startActivity(new Intent(activity, LoginAct.class));
                                        activity.finishAffinity();
                                        // use dismiss to cancel alert dialog
//                                    finish();
                                    }
                                });
                        alertDialog.show();*/

                    } else {
                        CommonUtils.showErrorToast(activity, message);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("", "------------" + t.getMessage());
            }
        });
    }

    private boolean validateChangePassword() {
        if (!Validator.checkEmptyInputLayout(edCurrentPassword, "Please enter current password.")) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edNewPassword, "Please enter new password.")) {
            return false;
        } else if (!Validator.checkPasswordLength(edNewPassword)) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edConfirmPassword, "Please enter confirm password.")) {
            return false;
        } else if (!Objects.requireNonNull(edNewPassword.getText()).toString().equals(Objects.requireNonNull(edConfirmPassword.getText()).toString())) {
            //are equal
            edConfirmPassword.setError("Not matched with new password.");
            edConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }
}
