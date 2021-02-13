package com.dealermela.home.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.home.model.QrCodeResponse;
import com.dealermela.listing_and_detail.activity.ListAct;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.retrofit.APIClientLaravel;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.NetworkUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRCodeScanningAct extends DealerMelaBaseActivity {
    String id,number;
    LinearLayout linProgress1;
    public static int scan_flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_q_r_code_scanning);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_q_r_code_scanning;
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        linProgress1 = findViewById(R.id.linProgress1);
    }

    @Override
    public void postInitView() {
    }

    @Override
    public void addListener() {
    }

    @Override
    public void loadData() {

        if(NetworkUtils.isNetworkConnected(QRCodeScanningAct.this)) {
            IntentIntegrator integrator = new IntentIntegrator(QRCodeScanningAct.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
//        integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setOrientationLocked(true);
            integrator.setCaptureActivity(CaptureActivityPortrait.class);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
////        Toast.makeText(this, "Result -"+result, Toast.LENGTH_LONG).show();
//        if(resultCode == 0 ){
//            AppLogger.e("resultCode*******", "-----Zero");
//            scan_flag = 3;
//        }else {
//            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//            if(result != null) {
//                if(result.getContents() == null) {
//                    AppLogger.e("Scan*******", "Cancelled scan");
//                    scan_flag = 3;
//                } else {
//                    AppLogger.e("Scan", "Scanned");
////                Toast.makeText(this, "Scanned:--"+result.getContents(), Toast.LENGTH_SHORT).show();
//                    linProgress1.setVisibility(View.VISIBLE);
////                StringBuilder stringBuilder = new StringBuilder();
//
//                    if(!result.getContents().isEmpty())
//                    {
//                        String[] strings = result.getContents().split("/");
////                    Toast.makeText(this, "Spilt result"+ strings, Toast.LENGTH_LONG).show();
//                        if(strings[7].equalsIgnoreCase("certificate_no"))  {
//                            number = strings[8];
////                         Toast.makeText(this, "CertificateNo :- " + strings[8], Toast.LENGTH_SHORT).show();
//                            getProductId(number);
//                        } else{
////                         Toast.makeText(this, "Else Value -" + strings[7], Toast.LENGTH_SHORT).show();
//                            scan_flag = 2;
//                        }
////                        for (int i = 0; i < strings.length; i++) {
////
////                            if(strings[i].equalsIgnoreCase("certificate_no")) {
////                                if (i == 8) {
////                                    number = strings[i];
////                                    Toast.makeText(this, "CertificateNo :- " + strings[i], Toast.LENGTH_LONG).show();
////                                }
////                            }else {
////                                stringBuilder.append(strings[i]);
////                                stringBuilder.append(" ");
////                            }
////                        }
////                        if(!number.isEmpty()) {
////                            getProductId(number);
////                        }else{
////                            scan_flag = 2;
////                        }
//                    }
//                    else {
//                        linProgress1.setVisibility(View.GONE);
//                    }
////                    Toast.makeText(this, "All StringBuilder--"+stringBuilder, Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                // This is important, otherwise the result will not be passed to the fragment
//                super.onActivityResult(requestCode, resultCode, data);
//            }
//        }
//
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        Toast.makeText(this, "Result -"+result, Toast.LENGTH_LONG).show();
        if(resultCode == 0 ){
            AppLogger.e("resultCode*******", "-----Zero");
            scan_flag = 3;
        }else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null) {
                if(result.getContents() == null) {
                    AppLogger.e("Scan*******", "Cancelled scan");
                    scan_flag = 2;
                } else {
                    AppLogger.e("Scan", "Scanned");
//                Toast.makeText(this, "Scanned:--"+result.getContents(), Toast.LENGTH_SHORT).show();
                    linProgress1.setVisibility(View.VISIBLE);
//                StringBuilder stringBuilder = new StringBuilder();

                    if(!result.getContents().isEmpty())
                    {
                        String[] strings = result.getContents().split("/");
//                        Toast.makeText(this, " result"+ result.getContents(), Toast.LENGTH_LONG).show();
//                        if(strings[7].equalsIgnoreCase("certificate_no"))  {
//                            number = strings[8];
////                         Toast.makeText(this, "CertificateNo :- " + strings[8], Toast.LENGTH_SHORT).show();
//                            getProductId(number);
//                        } else{
////                         Toast.makeText(this, "Else Value -" + strings[7], Toast.LENGTH_SHORT).show();
//                            scan_flag = 2;
//                        }
                        for (int i = 0; i < strings.length; i++) {

                            if(strings[i].equalsIgnoreCase("certificate_no")) {
//                                Toast.makeText(this, "Certificate_no-String"+ strings[i], Toast.LENGTH_SHORT).show();
//                                if (i == 8) {
                                    number = strings[i+1];
//                                    Toast.makeText(this, "CertificateNo :- " + strings[i+1], Toast.LENGTH_LONG).show();
//                                }
//                                comment this below code bcz every time when loop calling & for all wrong result it goes to else part & alert box display
//                                if(!number.isEmpty()) {
//                                    getProductId(number);
//                                }else{
//                                    scan_flag = 2;
//                                }
                            }else {
//                                scan_flag = 2;   //Not appllied here bcz when every time loop call it comes in this part & For valid QR it display Alert box
//                                stringBuilder.append(strings[i]);
//                                stringBuilder.append(" ");
                            }
                        }
                        if(number == null || number.isEmpty()) {
                            scan_flag = 2;
                        }else{
                            getProductId(number);
                        }
                    }
                    else {
                        linProgress1.setVisibility(View.GONE);
                    }
//                    Toast.makeText(this, "All StringBuilder--"+stringBuilder, Toast.LENGTH_SHORT).show();
                }
            } else {
                // This is important, otherwise the result will not be passed to the fragment
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void getProductId(String certificate_no){
//        Toast.makeText(this, "Method call successfully " + certificate_no, Toast.LENGTH_SHORT).show();
//        linProgress1.setVisibility(View.GONE);
        ApiInterface apiInterface = APIClientLaravel.getClient().create(ApiInterface.class);
        Call<QrCodeResponse> callApi = apiInterface.getProductId(certificate_no);
        callApi.enqueue(new Callback<QrCodeResponse>() {
            @Override
            public void onResponse(Call<QrCodeResponse> call, Response<QrCodeResponse> response) {
                if(response.isSuccessful()){
//                    Toast.makeText(QRCodeScanningAct.this, "response get "+ response.body().toString(), Toast.LENGTH_LONG).show();
                    linProgress1.setVisibility(View.GONE);
                        if(response.body().getStatus().equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)){
                            id = response.body().getProductId().toString();
//                            Toast.makeText(QRCodeScanningAct.this, "ProductId--"+id, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(QRCodeScanningAct.this, ProductDetailAct.class);
                            intent.putExtra(AppConstants.NAME, id);
                            intent.putExtra(AppConstants.ID,"");
                            scan_flag = 1;
                            startActivity(intent);
                        }else{
                            CommonUtils.showErrorToast(QRCodeScanningAct.this,"Something went wrong Please try again later.");
                        }
                }
            }

            @Override
            public void onFailure(Call<QrCodeResponse> call, Throwable t) {
                linProgress1.setVisibility(View.GONE);
                Toast.makeText(QRCodeScanningAct.this, "Fail API call"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        finish();
        if(scan_flag == 1){
            scan_flag = 0;
            finish();
        }else if(scan_flag == 2)
        {
            scan_flag = 0;
//            Toast.makeText(this, "Something went wrong Please try again later.", Toast.LENGTH_LONG).show();
            linProgress1.setVisibility(View.GONE);
            new AlertDialog.Builder(QRCodeScanningAct.this,R.style.AppCompatAlertDialogStyle)
                    .setTitle("Alert")
                    .setMessage("Invalid QR code.")
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            dialog.dismiss();
                        }
                    })
                    .show();

//            new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            finish();
//                        }
//                    }, AppConstants.SPLASH_TIME_OUT);
//            finish();
        }else if(scan_flag == 3){
            scan_flag = 0;
            finish();
        }
    }
}