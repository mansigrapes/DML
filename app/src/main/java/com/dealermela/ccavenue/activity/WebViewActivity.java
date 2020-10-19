package com.dealermela.ccavenue.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dealermela.R;
import com.dealermela.ccavenue.utility.AvenuesParams;
import com.dealermela.ccavenue.utility.Constants;
import com.dealermela.ccavenue.utility.LoadingDialog;
import com.dealermela.ccavenue.utility.RSAUtility;
import com.dealermela.ccavenue.utility.ServiceUtility;
import com.dealermela.util.AppLogger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {
    Intent mainIntent;
    String encVal;
    String vResponse;
    WebView webview;
    String order_flag;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_webview);
        mainIntent = getIntent();
        webview = (WebView) findViewById(R.id.webview);
//get rsa key method
        AppLogger.e("access code", "------------" + mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE));
        AppLogger.e("ORDER_ID", "------------" + mainIntent.getStringExtra(AvenuesParams.ORDER_ID));
        AppLogger.e("Customer_Id", "------------" + mainIntent.getStringExtra(AvenuesParams.customer_id));

        get_RSA_key(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE), mainIntent.getStringExtra(AvenuesParams.ORDER_ID));
    }

    private class RenderView extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            LoadingDialog.showLoadingDialog(WebViewActivity.this, "Loading...");
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if (!ServiceUtility.chkNull(vResponse).equals("")
                    && ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR") == -1) {
                StringBuffer vEncVal = new StringBuffer("");
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, mainIntent.getStringExtra(AvenuesParams.AMOUNT)));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, mainIntent.getStringExtra(AvenuesParams.CURRENCY)));
                encVal = RSAUtility.encrypt(vEncVal.substring(0, vEncVal.length() - 1), vResponse);  //encrypt amount and currency
            }

            return null;
        }

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            LoadingDialog.cancelLoading();

            @SuppressWarnings("unused")
            class MyJavaScriptInterface {
                @JavascriptInterface
                public void processHTML(String html) {

                    // process the html source code to get final status of transaction

                    AppLogger.e("html","------------"+ html);

                    String status = null;
                    if (html.contains("Failure")) {
                        status = "Transaction Fail!";
                    } else if (html.contains("Success")) {
                        status = "Transaction Successful!";
                    } else if (html.contains("Aborted")) {
                        status = "Transaction Cancelled!";
                    } else {
                        status = "Status Not Known!";
                    }
                    //Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                    intent.putExtra("transStatus", status);
                    intent.putExtra("customerId", mainIntent.getStringExtra(AvenuesParams.customer_id));
                    startActivity(intent);
                    finish();
                }
            }

//            final WebView webview = (WebView) findViewById(R.id.webview);o

            webview.getSettings().setJavaScriptEnabled(true);
            webview.addJavascriptInterface(new MyJavaScriptInterface(),"HTMLOUT");
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(webview, url);
                    LoadingDialog.cancelLoading();
                    AppLogger.e("url","----------"+ url);
                    //comment on 02-10-2020 Bcz not find ccavResponseHandler.jsp file when we get response URL
//                    if (url.indexOf("/ccavResponseHandler.jsp") != -1) {
//                        webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//                        AppLogger.e("ifCondition","------");
//                    }
                    if (url.contains("/ccavMobileResponseHandler.php")) {
                        AppLogger.e("ifCondition","------");
                        webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                    }
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    LoadingDialog.showLoadingDialog(WebViewActivity.this, "Loading...");
                }
            });

            try {
                AppLogger.e(AvenuesParams.ACCESS_CODE, "-----------" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE)));
                AppLogger.e(AvenuesParams.MERCHANT_ID, "-----------" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID)));
                AppLogger.e(AvenuesParams.ORDER_ID, "-----------" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ORDER_ID)));
                AppLogger.e(AvenuesParams.REDIRECT_URL, "-----------" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL)));
                AppLogger.e(AvenuesParams.CANCEL_URL, "-----------" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.CANCEL_URL)));
                AppLogger.e(AvenuesParams.ENC_VAL, "-----------" + URLEncoder.encode(encVal));

                AppLogger.e("post url", "-----------" + AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE), "UTF-8") + "&" + AvenuesParams.MERCHANT_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID), "UTF-8") + "&" + AvenuesParams.ORDER_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ORDER_ID), "UTF-8") + "&" + AvenuesParams.REDIRECT_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL), "UTF-8") + "&" + AvenuesParams.CANCEL_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.CANCEL_URL), "UTF-8") + "&" + AvenuesParams.ENC_VAL + "=" + URLEncoder.encode(encVal, "UTF-8") );
//                String postData = AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE), "UTF-8") + "&" + AvenuesParams.MERCHANT_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID), "UTF-8") + "&" + AvenuesParams.ORDER_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ORDER_ID), "UTF-8") + "&" + AvenuesParams.REDIRECT_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL), "UTF-8") + "&" + AvenuesParams.CANCEL_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.CANCEL_URL), "UTF-8") + "&" + AvenuesParams.ENC_VAL + "=" + URLEncoder.encode(encVal, "UTF-8") + "&billing_name=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_name),"UTF-8") + "&billing_address=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_address),"UTF-8") + "&billing_zip="+ URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_zip),"UTF-8") + "&billing_city=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_city),"UTF-8") + "&billing_state=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_state),"UTF-8") + "&billing_country=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_country),"UTF-8") + "&billing_tel=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_tel),"UTF-8") + "&billing_email=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_email),"UTF-8") + "&" + AvenuesParams.website_order_id + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.website_order_id),"UTF-8") ;
                String postData = AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE), "UTF-8") + "&" + AvenuesParams.MERCHANT_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID), "UTF-8") + "&" + AvenuesParams.ORDER_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ORDER_ID), "UTF-8") + "&" + AvenuesParams.REDIRECT_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL), "UTF-8") + "&" + AvenuesParams.CANCEL_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.CANCEL_URL), "UTF-8") + "&" + AvenuesParams.ENC_VAL + "=" + URLEncoder.encode(encVal, "UTF-8") + "&billing_name=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_name),"UTF-8") + "&billing_address=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_address),"UTF-8") + "&billing_zip="+ URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_zip),"UTF-8") + "&billing_city=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_city),"UTF-8") + "&billing_state=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_state),"UTF-8") + "&billing_country=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_country),"UTF-8") + "&billing_tel=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_tel),"UTF-8") + "&billing_email=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.billing_email),"UTF-8") ;
                webview.postUrl(Constants.TRANS_URL, postData.getBytes());

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void get_RSA_key(final String ac, final String od) {
        LoadingDialog.showLoadingDialog(WebViewActivity.this, "Loading...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, mainIntent.getStringExtra(AvenuesParams.RSA_KEY_URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(WebViewActivity.this,response,Toast.LENGTH_LONG).show();
                        LoadingDialog.cancelLoading();
                        AppLogger.e("response", "--------------" + response);
                        if (response != null && !response.equals("")) {

                            vResponse = response;     ///save retrived rsa key
                            AppLogger.e("rsa key","------"+vResponse);
                            if (vResponse.contains("!ERROR!")) {
                                show_alert(vResponse);
                            } else {
                                new RenderView().execute();   // Calling async task to get display content
                            }

                        } else {
                            show_alert("No response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LoadingDialog.cancelLoading();
                        //Toast.makeText(WebViewActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(AvenuesParams.ACCESS_CODE, ac);
                params.put(AvenuesParams.ORDER_ID, od);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void show_alert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                WebViewActivity.this).create();

        alertDialog.setTitle("Error!!!");
        if (msg.contains("\n"))
            msg = msg.replaceAll("\\\n", "");

        alertDialog.setMessage(msg);

        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }

//   public void onBackPressed(){
//        //Call API for without paying payment and user press back button so that order has not been completed and show as it is in cart page
//       if (webview.canGoBack()) {
//           finish();
//           webview.goBack();
//       } else {
//           super.onBackPressed();
//       }

//       order_flag = "0";
//       ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
//       Call<JsonObject> callApi = apiInterface.placeOrder(customerId,order_flag);
//       callApi.enqueue(new Callback<JsonObject>() {
//           @SuppressLint("SetTextI18n")
//           @Override
//           public void onResponse(@NonNull Call<JsonObject> call, @NonNull retrofit2.Response<JsonObject> response) {
//               AppLogger.e(AppConstants.RESPONSE, "--------------" + response.body());
//               assert response.body() != null;
//               try {
//                   AppLogger.e("Response from API when press back","---- " + response.body().toString());
//                   JSONObject jsonObject = new JSONObject(response.body().toString());
//                   if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_FAIL)) {
//                       AppLogger.e("Response from API when press back","---- " + response.body().toString());
//
//                       new AlertDialog.Builder(WebViewActivity.this,R.style.AppCompatAlertDialogStyle)
//                               .setTitle(CommonUtils.capitalizeString(jsonObject.getString("status")))
//                               .setMessage(jsonObject.getString("message"))
//                               .setCancelable(false)
//                               .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//                                   @Override
//                                   public void onClick(DialogInterface dialog, int which) {
//                                       finish();
//                                       webview.goBack();
//                                   }
//                               })
//                               .show();
//
//                   } else {
//                   }
//
//               } catch (JSONException e) {
//                   e.printStackTrace();
//               }
//           }
//
//           @Override
//           public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//               AppLogger.e("Error Webview back pressed API ","--" + t.getMessage());
//           }
//       });
//    }
}