package com.dealermela.ccavenue.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dealermela.R;
import com.dealermela.ccavenue.utility.AvenuesParams;
import com.dealermela.ccavenue.utility.ServiceUtility;


public class InitialScreenActivity extends AppCompatActivity {
	
	private EditText accessCode, merchantId, currency, amount, orderId, rsaKeyUrl, redirectUrl, cancelUrl;

	private void init(){
		accessCode = findViewById(R.id.accessCode);
		merchantId = findViewById(R.id.merchantId);
		orderId  = findViewById(R.id.orderId);
		currency = findViewById(R.id.currency);
		amount = findViewById(R.id.amount);
		rsaKeyUrl = findViewById(R.id.rsaUrl);
		redirectUrl = findViewById(R.id.redirectUrl);
		cancelUrl = findViewById(R.id.cancelUrl);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initial_screen);
		init();
	}

	public void onClick(View view) {
		//Mandatory parameters. Other parameters can be added if required.
		String vAccessCode = ServiceUtility.chkNull(accessCode.getText()).toString().trim();
		String vMerchantId = ServiceUtility.chkNull(merchantId.getText()).toString().trim();
		String vCurrency = ServiceUtility.chkNull(currency.getText()).toString().trim();
		String vAmount = ServiceUtility.chkNull(amount.getText()).toString().trim();

		if(!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")){
			Intent intent = new Intent(this,WebViewActivity.class);

			intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(accessCode.getText()).toString().trim());
			intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(merchantId.getText()).toString().trim());
			intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(orderId.getText()).toString().trim());
			intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(currency.getText()).toString().trim());
			intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(amount.getText()).toString().trim());
			intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull(redirectUrl.getText()).toString().trim());
			intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull(cancelUrl.getText()).toString().trim());
			intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull(rsaKeyUrl.getText()).toString().trim());


			startActivity(intent);
		}else{
			showToast("All parameters are mandatory.");
		}
	}
	
	public void showToast(String msg) {
		Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
	}


	@Override
	protected void onStart() {
		super.onStart();
		//generating new order number for every transaction
		Integer randomNum = ServiceUtility.randInt(0, 9999999);
		orderId.setText(randomNum.toString());
	}

}