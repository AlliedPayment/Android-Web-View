package com.alliedpayment.example_web_view;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Patrick.Weisz on 9/12/2017.
 */

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...",true);

        Intent intent = getIntent();
        String webViewUrl = intent.getStringExtra(MainActivity.WEB_VIEW_URL);

        mWebView = new WebView(this);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(ensureHasHttps(webViewUrl));
        mWebView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                final Uri uri = Uri.parse(url);
                return handleUri(uri);
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final Uri uri = request.getUrl();
                return handleUri(uri);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(pd!=null && pd.isShowing())
                {
                    pd.dismiss();
                }
            }

            private boolean handleUri(final Uri uri) {
                if (URLUtil.isNetworkUrl(uri.toString()) && !uri.toString().contains("raw")) {
                    // Returning false means that you are going to load this url in the webView itself
                    return false;
                } else {
                    final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;
                }
            }
        });

        this.setContentView(mWebView);
        setTitle(getActivityTitle(webViewUrl));
    }

    private String getActivityTitle(String url) {
        if(url.contains("final")) {
            return "Final";
        }
        else if(url.contains("mock")) {
            return "Mock";
        }
        else if(url.contains("demo")) {
            return "Demo";
        }
        else {
            return "Production";
        }
    }

    private String ensureHasHttps(String url) {
        if(!url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }
}
