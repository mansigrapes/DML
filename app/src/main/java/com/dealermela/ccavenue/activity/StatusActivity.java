package com.dealermela.ccavenue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.dealermela.R;
import com.dealermela.order.activity.OrderTabActivity;
import com.dealermela.util.AppLogger;

public class StatusActivity extends AppCompatActivity {

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_status);

		Intent mainIntent = getIntent();
		TextView tv4 = (TextView) findViewById(R.id.textView1);
		tv4.setText(mainIntent.getStringExtra("transStatus"));

		if(tv4.getText().toString().equalsIgnoreCase("Transaction Successful!")){
			Intent intent = new Intent(StatusActivity.this, OrderTabActivity.class);
			startActivity(intent);
		}
	}
	
	public void showToast(String msg) {
		Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
		AppLogger.e("msg","----------------"+msg);
	}

} 