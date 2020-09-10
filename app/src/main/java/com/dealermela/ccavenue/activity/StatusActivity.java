package com.dealermela.ccavenue.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.dealermela.R;
import com.dealermela.authentication.myaccount.dialog.MaintenanceDialogClass;
import com.dealermela.order.activity.OrderTabActivity;
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

public class StatusActivity extends AppCompatActivity {
	String customer_id,order_flag;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_status);

		Intent mainIntent = getIntent();
		TextView tv4 = (TextView) findViewById(R.id.textView1);
		tv4.setText(mainIntent.getStringExtra("transStatus"));
		customer_id = mainIntent.getStringExtra("customerId");

		if(tv4.getText().toString().equalsIgnoreCase("Transaction Successful!")){
			ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
			Call<JsonObject> callApi = apiInterface.placeOrder(customer_id,order_flag);
			callApi.enqueue(new Callback<JsonObject>() {
				@SuppressLint("SetTextI18n")
				@Override
				public void onResponse( Call<JsonObject> call,  Response<JsonObject> response) {
					AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
					assert response.body() != null;
					if(response.body() != null) {
						try {
							JSONObject jsonObject = new JSONObject(response.body().toString());
							if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
								Intent intent = new Intent(StatusActivity.this, OrderTabActivity.class);
								startActivity(intent);
							}
						} catch (JSONException e) {
                                e.printStackTrace();
						}
					}else {
					}
				}

				@Override
				public void onFailure( Call<JsonObject> call, Throwable t) {
					AppLogger.e("Error Status Activity","----" + t.getMessage());
				}
			});

		}
	}
	
	public void showToast(String msg) {
		Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
		AppLogger.e("msg","----------------"+msg);
	}

} 