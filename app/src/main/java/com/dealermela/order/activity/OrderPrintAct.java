package com.dealermela.order.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;

public class OrderPrintAct extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private Button btnBack;
    private TextView textView8;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_print);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        btnBack = findViewById(R.id.btnBack);
        url = getIntent().getStringExtra(AppConstants.NAME);

        // Print an existing web page (remember to request INTERNET permission!):
//        webView.loadUrl("http://123.108.51.11/sample-invoice.html");
        configureWebView();

    }

    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter("MyOrderPrint");

        String jobName = getString(R.string.app_name) +
                " Print";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void configureWebView() {

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                AppLogger.e("", "page finished loading " + url);
                progressBar.setVisibility(View.GONE);
                createWebPrintJob(view);
                btnBack.setVisibility(View.VISIBLE);
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
//                webView = null;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
