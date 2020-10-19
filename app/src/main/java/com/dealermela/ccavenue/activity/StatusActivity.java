package com.dealermela.ccavenue.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.dealermela.R;

import static com.dealermela.cart.activity.OrderSummaryAct.Orderflag;
import com.dealermela.home.activity.MainActivity;
import com.dealermela.order.activity.OrderTabActivity;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.google.gson.JsonObject;
import com.ligl.android.widget.iosdialog.IOSDialog;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusActivity extends AppCompatActivity {
	String customer_id,order_flag,receivedstatus,Orderid;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_status);

		TextView tv4 = (TextView) findViewById(R.id.textView1);

		Intent mainIntent = getIntent();
		receivedstatus = mainIntent.getStringExtra("transStatus");
		customer_id = mainIntent.getStringExtra("customerId");

//		tv4.setText(mainIntent.getStringExtra("transStatus"));

		if(receivedstatus.equalsIgnoreCase("Transaction Successful!")){
//			showProgressDialog(AppConstants.PLEASE_WAIT);
			order_flag = "Complete";
			ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
			Call<JsonObject> callApi = apiInterface.placeOrder(customer_id,order_flag);
			callApi.enqueue(new Callback<JsonObject>() {
				@SuppressLint("SetTextI18n")
				@Override
				public void onResponse( Call<JsonObject> call,  Response<JsonObject> response) {
					AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
					assert response.body() != null;
					if(response.body() != null) {
						JSONObject jsonObject = null;
						try {
							 jsonObject = new JSONObject(response.body().toString());
							if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
//								Orderid = jsonObject.getString("order_number");
								String msg = "Thank you for shopping with us.Your transaction is successful. We will be shipping your order to you soon." +  "Your Order No. - ";
								AppLogger.e("Success Status","-----");
								new IOSDialog.Builder(StatusActivity.this)
										.setTitle("Success")
										.setMessage("Thank you for shopping with us.Your transaction is successful. We will be shipping your order to you soon.")
										.setCancelable(false)
										.setPositiveButton(StatusActivity.this.getString(R.string.ok), new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												Orderflag = 1;
												Intent intent = new Intent(StatusActivity.this, OrderTabActivity.class);
												startActivity(intent);
												finish();
											}
										}).show();
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

		}else if(receivedstatus.equalsIgnoreCase("Transaction Cancelled!")){

			order_flag = "Canceled";

			ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
			Call<JsonObject> callApi = apiInterface.placeOrder(customer_id,order_flag);
			callApi.enqueue(new Callback<JsonObject>() {
				@SuppressLint("SetTextI18n")
				@Override
				public void onResponse( Call<JsonObject> call,  Response<JsonObject> response) {
					AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
					assert response.body() != null;
					if(response.isSuccessful()) {
						JSONObject jsonObject = null;
						try {
							jsonObject = new JSONObject(response.body().toString());
							if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {

							}else if(jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_FAIL) || jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_FAILED)){
								AppLogger.e("Fail Status","-----");
								String msg = "";
								new IOSDialog.Builder(StatusActivity.this)
										.setTitle("Transaction Cancelled!")
										.setMessage(jsonObject.getString("message"))
										.setCancelable(false)
										.setPositiveButton(StatusActivity.this.getString(R.string.ok), new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												Intent intent = new Intent(StatusActivity.this, MainActivity.class);
												startActivity(intent);
												finish();
											}
										}).show();
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

		} else if(receivedstatus.equalsIgnoreCase("Transaction Fail!")){
			order_flag = "Canceled";

			ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
			Call<JsonObject> callApi = apiInterface.placeOrder(customer_id,order_flag);
			callApi.enqueue(new Callback<JsonObject>() {
				@SuppressLint("SetTextI18n")
				@Override
				public void onResponse( Call<JsonObject> call,  Response<JsonObject> response) {
					AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
					assert response.body() != null;
					if(response.body() != null) {
						JSONObject jsonObject = null;
						try {
							jsonObject = new JSONObject(response.body().toString());
							if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {

							}else if(jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_FAIL) || jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_FAILED)){
								AppLogger.e("Fail Status","-----");
								String msg = " ";
								new IOSDialog.Builder(StatusActivity.this)
										.setTitle("Transaction Fail!")
										.setMessage(jsonObject.getString("message"))
										.setCancelable(false)
										.setPositiveButton(StatusActivity.this.getString(R.string.ok), new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												Intent intent = new Intent(StatusActivity.this, MainActivity.class);
												startActivity(intent);
												finish();
											}
										}).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}else {
						new IOSDialog.Builder(StatusActivity.this)
								.setTitle("Transaction Fail!")
								.setMessage("Something went wrong. Please try again.")
								.setCancelable(false)
								.setPositiveButton(StatusActivity.this.getString(R.string.ok), new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Intent intent = new Intent(StatusActivity.this, MainActivity.class);
										startActivity(intent);
										finish();
									}
								}).show();
					}
				}
				@Override
				public void onFailure( Call<JsonObject> call, Throwable t) {
					AppLogger.e("Error Status Activity","----" + t.getMessage());
				}
			});

		} else {
			new IOSDialog.Builder(StatusActivity.this)
					.setTitle("Transaction Cancel!")
					.setMessage("Something went wrong! please try again.")
					.setCancelable(false)
					.setPositiveButton(StatusActivity.this.getString(R.string.ok), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(StatusActivity.this, MainActivity.class);
							startActivity(intent);
							finish();
						}
					}).show();
		}
	}
	
	public void showToast(String msg) {
		Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
		AppLogger.e("msg","----------------"+msg);
	}
} 