package com.dealermela.authentication.myaccount.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.dealermela.R;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;

public class DealershipGuidlineAct extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private Button btnback;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealership_guidline);

        webView = findViewById(R.id.webview1);
        progressBar = findViewById(R.id.progressBar1);
        btnback = findViewById(R.id.btnback1);
        url = getIntent().getStringExtra(AppConstants.NAME);
        AppLogger.e("Intent Url :","----" + url);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+url);

//        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+url);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

//        webView.getSettings().setJavaScriptEnabled(true);
//        String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
//        AppLogger.e("URL----","http://docs.google.com/gview?embedded=true&url="+url);
//        webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+url);

//        webView.loadUrl(url);

//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+url);

//        configureWebView();
    }

//    @SuppressLint("SetJavaScriptEnabled")
//    private void configureWebView(){
//
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+url);
//        progressBar.setVisibility(View.GONE);
//
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return false;
//            }
//
////            @Override
////            public void onPageFinished(WebView view, String url) {
////                AppLogger.e(" ", "page finish loading " + url);
////                progressBar.setVisibility(View.GONE);
//////                btnback.setVisibility(View.VISIBLE);
//////                btnback.setOnClickListener(new View.OnClickListener() {
//////                    @Override
//////                    public void onClick(View v) {
//////                        finish();
//////                    }
//////                });
////            }
//        });
//    }
}