package com.jayant.pocketlibrary.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import com.jayant.pocketlibrary.R;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URLEncoder;

public class PdfView extends AppCompatActivity {

    private WebView pdfWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        pdfWebView = findViewById(R.id.pdf_web_view);
        String pdfName = getIntent().getStringExtra("pdfName");
        String pdfUrl = getIntent().getStringExtra("pdfUrl");

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(pdfName);
        pd.setMessage("Opening...");

        pdfWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String url = "";

        try {
            url = URLEncoder.encode(pdfUrl, "UTF-8");
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        pdfWebView.getSettings().setJavaScriptEnabled(true);
        pdfWebView.loadUrl("https://docs.google.com/viewer?url=" + url);

    }
}