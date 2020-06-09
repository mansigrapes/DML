package com.dealermela.authentication.myaccount.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.cart.activity.CartAct;
import com.dealermela.cart.adapter.CartAdapter;
import com.dealermela.cart.fragment.ShoppingFrg;
import com.dealermela.cart.model.CartLocalDataItem;
import com.dealermela.dbhelper.DatabaseCartAdapter;
import com.dealermela.home.activity.MainActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.SharedPreferences;
import com.dealermela.util.Validator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;
import static com.dealermela.other.activity.SplashAct.loginFlag;

public class LoginAct extends DealerMelaBaseActivity implements View.OnClickListener {

    public static int cartbackFlag = 0;
    private final String TAG = this.getClass().getSimpleName();
    private EditText edEmail, edPassword;
    private TextView tvRemPwd, tvForgotPwd, tvNewAccount;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;
    private DatabaseCartAdapter databaseCartAdapter;
    private CheckBox checkBox;
    private Cursor c;
    private int count = 0;
    private boolean flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.act_login;
    }

    @Override
    public void init() {
        databaseCartAdapter = new DatabaseCartAdapter(LoginAct.this);
        sharedPreferences = new SharedPreferences(LoginAct.this);
    }

    @Override
    public void initView() {
        ImageView imgLogo = findViewById(R.id.imgLogo);
//        checkBox = findViewById(R.id.checkBox);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
//        tvRemPwd = findViewById(R.id.tvRemPwd);
        tvForgotPwd = findViewById(R.id.tvForgotPwd);
        tvNewAccount = findViewById(R.id.tvNewAccount);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    public void postInitView() {
        if (sharedPreferences.getRemember().equalsIgnoreCase("true")) {
            edEmail.setText(sharedPreferences.getEmail());
            edPassword.setText(sharedPreferences.getPassword());
        } else {
        }
    }

    @Override
    public void addListener() {
        btnLogin.setOnClickListener(this);
//        tvRemPwd.setOnClickListener(this);
        tvForgotPwd.setOnClickListener(this);
        tvNewAccount.setOnClickListener(this);
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (compoundButton.isChecked()) {
//                    flag = true;
//                } else {
//                    flag = false;
//                }
//            }
//        });
    }

    @Override
    public void loadData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                validateLogin();
                break;
//            case R.id.tvRemPwd:
//                break;
            case R.id.tvForgotPwd:
                startNewActivity(ForgotPasswordAct.class);
                break;
            case R.id.tvNewAccount:
                startNewActivity(SignUpAct.class);
                break;
        }
    }

    private void validateLogin() {
        //noinspection StatementWithEmptyBody
        if (!Validator.checkEmpty(edEmail, getString(R.string.login_please_enter_email))) {

        } else if (!Validator.checkEmail(edEmail)) {
            edEmail.requestFocus();
            edEmail.setError(getString(R.string.login_please_enter_valid_email));
        } else //noinspection StatementWithEmptyBody
            if (!Validator.checkEmpty(edPassword, getString(R.string.login_please_enter_password))) {

            } else //noinspection StatementWithEmptyBody
                if (!Validator.checkPasswordLength(edPassword)) {

                } else {
                    if (flag) {
                        sharedPreferences.saveRemember("true");
                    } else {
                        sharedPreferences.saveRemember("false");
                    }
                    checkLogin(edEmail.getText().toString(), edPassword.getText().toString(), "ASDSds56445df5g4d5f4gd5fg5f4g5ffd", CommonUtils.getDeviceID(LoginAct.this));
                }
    }

    private void checkLogin(String email, String password, String token, String deviceId) {
        showProgressDialog(AppConstants.PLEASE_WAIT);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> callApi = apiInterface.Login(email, password, token, deviceId);
        callApi.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                AppLogger.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                hideProgressDialog();
                String message=null, status=null;
//                try {
//                    JSONObject jsonObject=new JSONObject(String.valueOf(response.body()));
//                    status=jsonObject.getString("status");

                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS))
//                    if(status.equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS))
                    {
                        AppLogger.e(AppConstants.RESPONSE, "-----------------" + response.body().getStatus());
                        //                    Save data to session
                        Gson gson = new Gson();
                        String json = gson.toJson(response.body());
                        AppLogger.e(AppConstants.RESPONSE, "-----------------" + json);

                        sharedPreferences.saveLoginData(json);
                        sharedPreferences.saveEmail(edEmail.getText().toString().trim());
                        sharedPreferences.savePassword(edPassword.getText().toString().trim());
//                        String data = jsonObject.getString("data");
////                        customerId = json.getEntityId();
//                        customerId = json.substring(54,63);

//                        JSONArray jsonArray = jsonObject.getJSONArray("data");
//                        customerId = jsonObject.getString("entity_id");

                        customerId = response.body().getData().getEntityId();
                        sharedPreferences.saveBillingAddress(response.body().getData().getDefaultBillingNew().getFirstname() + " " + response.body().getData().getDefaultBillingNew().getLastname() + ",\n" + response.body().getData().getDefaultBillingNew().getStreet() + ",\n" + response.body().getData().getDefaultBillingNew().getCity() + ", " + response.body().getData().getDefaultBillingNew().getRegion() + ", " + response.body().getData().getDefaultBillingNew().getPostcode() + ",\n" + response.body().getData().getDefaultBillingNew().getCountryId() + "\nT: " + response.body().getData().getDefaultBillingNew().getTelephone());
                        sharedPreferences.saveShipping(response.body().getData().getDefaultShippingNew().getFirstname() + " " + response.body().getData().getDefaultBillingNew().getLastname() + ",\n" + response.body().getData().getDefaultBillingNew().getStreet() + ",\n" + response.body().getData().getDefaultBillingNew().getCity() + ", " + response.body().getData().getDefaultBillingNew().getRegion() + ", " + response.body().getData().getDefaultBillingNew().getPostcode() + ",\n" + response.body().getData().getDefaultBillingNew().getCountryId() + "\nT: " + response.body().getData().getDefaultBillingNew().getTelephone());

                        //    sharedPreferences.saveBillingAddress(response.body().getData().getDefaultBilling());
                        //    sharedPreferences.saveShipping(response.body().getData().getDefaultShipping());

                        fillListView();

                    } else if (response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_FAILED)) {

                        CommonUtils.showErrorToast(LoginAct.this, response.body().getMessage());

                        AppLogger.e(AppConstants.RESPONSE, "-----------------" + response.body().getMessage());

//                        CommonUtils.showErrorToast(LoginAct.this, message);
                    } else {
                        AppLogger.e(AppConstants.RESPONSE, "-----------------" + response.body().getStatus());
                    }

//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    AppLogger.e("Error Execption","--"+e.getMessage());
//                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                CommonUtils.showErrorToast(LoginAct.this, "Fail to get any Response");
                AppLogger.e(TAG, "------------" + t.getMessage());
                hideProgressDialog();
            }
        });
    }

    private void addToCart(String productId, String customerId, String optionId, String optionTypeId, String stoneOptionId, String stoneOptionTypeId, String qty, String metalQualityColor, String metalCarat) {

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.addToCart(productId, customerId, optionId, optionTypeId, stoneOptionId, stoneOptionTypeId, qty, metalQualityColor, metalCarat);
        callApi.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
                hideProgressDialog();
                try {
                    assert response.body() != null;
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {

                        CommonUtils.showSuccessToast(LoginAct.this, "Item added in cart.");
//                        cartCount++;
//                        setupBadge();

                        count--;
                        if (count == 0) {
                            hideProgressDialog();
                            databaseCartAdapter.openDatabase();
                            databaseCartAdapter.deleteAllRecord();
                            databaseCartAdapter.closeDatabase();

                            if (loginFlag == 0) {
                                startNewActivity(MainActivity.class);
                                finish();
                            } else {
                                loginFlag = 0;
                                cartbackFlag = 1;
                                startNewActivity(CartAct.class);
                                finish();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideProgressDialog();
            }
        });
    }
    //View all data in local database
    private void fillListView() {
        List<CartLocalDataItem> cartLocalDataItems = new ArrayList<>();
        databaseCartAdapter.openDatabase();
        c = databaseCartAdapter.getAllValues();

        count = c.getCount();

        if (c.getCount() > 0) {

            showProgressDialog(AppConstants.PLEASE_WAIT);
            for (int i = 0; i < c.getCount(); i++) {

                addToCart(c.getString(c.getColumnIndex(DatabaseCartAdapter.PRODUCT_ID)), customerId, c.getString(c.getColumnIndex(DatabaseCartAdapter.RING_OPTION_ID)), c.getString(c.getColumnIndex(DatabaseCartAdapter.RING_OPTION_TYPE_ID)), c.getString(c.getColumnIndex(DatabaseCartAdapter.STONE_OPTION_ID)), c.getString(c.getColumnIndex(DatabaseCartAdapter.STONE_OPTION_TYPE_ID)), c.getString(c.getColumnIndex(DatabaseCartAdapter.QTY)), c.getString(c.getColumnIndex(DatabaseCartAdapter.METAL_QUALITY_COLOR)), c.getString(c.getColumnIndex(DatabaseCartAdapter.METAL_CARAT)));
                c.moveToNext();
              /*  if (i == c.getCount() - 1) {
                    if (loginFlag == 0) {
                        startNewActivity(MainActivity.class);
                        finish();
                    } else {
                        startNewActivity(CartAct.class);
                        finish();
                    }
                } */
            }
            databaseCartAdapter.closeDatabase();
            if (loginFlag == 0) {
                startNewActivity(MainActivity.class);
                finish();
            } else {
                startNewActivity(CartAct.class);
                finish();
            }
        } else {
            AppLogger.e("table", "-----------table is empty");
            if (loginFlag == 0) {
                startNewActivity(MainActivity.class);
                finish();
            } else {
                startNewActivity(CartAct.class);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(loginFlag == 1){
            startNewActivity(CartAct.class);
            finish();
        }
        super.onBackPressed();
    }
}
