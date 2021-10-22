package com.sayweee.oauth.iml;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sayweee.oauth.OAuth;

import java.util.Map;

/**
 * Author:  winds
 * Date:    2021/10/13.
 * Desc:
 */
public class WeeeOAuth extends OAuth {

    private final static String DOMAIN_PRODUCT = "https://open.sayweee.com";
    private final static String DOMAIN_TB1 = "https://open.tb1.sayweee.net";
    //授权接口
    static String HOST = DOMAIN_PRODUCT;
    final static String URL_OAUTH = "/uaa/oauth/authorize";
    final static String URL_OAUTH_WEEE = "/uaa/oauth/authorize_weee";

    private String lang;
    private String clientId;
    private OAuthCallback callback;

    final static String TAG = "WeeeOAuth";
    final static int OAUTH = 10101;
    public static final WeeeOAuth sInstance = new WeeeOAuth();

    @Override
    public OAuth isTest(boolean test) {
        HOST = test ? DOMAIN_TB1 : DOMAIN_PRODUCT;
        return this;
    }

    @Override
    public OAuth lang(String lang) {
        this.lang = lang;
        return this;
    }

    @Override
    public OAuth setClientId(@NonNull String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Override
    public OAuth setOAuthCallback(@NonNull OAuthCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public OAuth removeOAuthCallback() {
        this.callback = null;
        return this;
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == OAUTH) {
            String result = data == null ? null : data.getStringExtra("result");
            dispatchResult(result);
            return true;
        }
        return false;
    }

    @Override
    public void doOAuthWeee(@NonNull Activity context, boolean forceRetryLogin, @Nullable String tag) {
        if (context == null) {
            throw new IllegalArgumentException("Please check argument, context can't be null");
        }
        if (clientId == null) {
            throw new IllegalArgumentException("Please check argument, clientId can't be null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("Please check argument, callback can't be null");
        }
        new AuthorizeWeeeSession().clientId(clientId).forceRetryLogin(forceRetryLogin).execute(context, tag);

    }

    String getLang() {
        return lang;
    }

    String getOauthUrl() {
        return WeeeOAuth.HOST + WeeeOAuth.URL_OAUTH;
    }

    String getOauthWeeeUrl() {
        return WeeeOAuth.HOST + WeeeOAuth.URL_OAUTH_WEEE;
    }

    void dispatchResult(String result) {
        if (callback != null) {
            Map<String, String> params = Utils.parseQueryParams(result);
            OAuthBean data = new OAuthBean(params);
            callback.onResult(data.isSuccess(), data);
        }
    }

}
