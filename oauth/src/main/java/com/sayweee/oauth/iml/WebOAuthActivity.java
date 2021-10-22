package com.sayweee.oauth.iml;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sayweee.oauth.OAuth;
import com.sayweee.oauth.R;

import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author:  winds
 * Date:    2021/10/14.
 * Desc:
 */
public class WebOAuthActivity extends AppCompatActivity {

    protected WebView web;
    private boolean logEnable = false;

    public final static String URL = "url";
    public final static String LANG = "lang";

    private final static String PATTERN = "weeecosmos.*://weee-callback\\?.*";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);   //去除默认actionbar
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//统一管理竖屏
            setStatusBarTheme(this, true);
        } catch (Exception ignored) {
        }
        setContentView(R.layout.fragment_webview);

        web = findViewById(R.id.web);
        init(web);
        String url = getIntent().getStringExtra(URL);
        String lang = getIntent().getStringExtra(LANG);
        if (url != null) {
            Map<String, String> header = new LinkedHashMap<>();
            if (lang != null) {
                header.put(LANG, lang);
            }
            web.loadUrl(url, header);
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        destroy(web);
        super.onDestroy();
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void init(WebView view) {
        WebSettings settings = view.getSettings();
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        view.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                log(url);
                if (url != null && url.matches(PATTERN)) {
                    try {
                        dispatch(URLDecoder.decode(url, "utf-8"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    public void destroy(WebView web) {
        if (web != null) {
            ViewParent parent = web.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(web);
            }
            web.stopLoading();
            web.getSettings().setJavaScriptEnabled(false);
            web.clearHistory();
            web.clearView();
            web.removeAllViews();
            web.destroy();
        }
    }

    private void dispatch(String result) {

        WeeeOAuth.sInstance.dispatchResult(result);
    }

    private void log(String msg) {
        if (logEnable) {
            Log.i(WeeeOAuth.TAG, msg);
        }
    }

    private boolean setStatusBarTheme(Activity activity, boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (dark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                if (decorView.getSystemUiVisibility() != vis) {
                    decorView.setSystemUiVisibility(vis);
                }
                return true;
            }
        }
        return false;
    }

}
