package com.sayweee.oauth.iml;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;

/**
 * Author:  winds
 * Date:    2021/10/13.
 * Desc:
 */
public class AuthorizeSession implements IOAuthSession {

    private String clientId;
    private boolean forceRetryLogin;

    @Override
    public IOAuthSession forceRetryLogin(boolean force) {
        this.forceRetryLogin = force;
        return this;
    }

    @Override
    public IOAuthSession clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Override
    public IOAuthSession execute(Activity context, String tag) {
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("grant_type", "authorization_code");
        params.put("state", tag);
        params.put("response_type", "code");
        params.put("force_login", Boolean.toString(forceRetryLogin));
        String url = Utils.packetUrlParams(WeeeOAuth.sInstance.getOauthUrl(), params);
        context.startActivity(new Intent(context, WebOAuthActivity.class)
                .putExtra(WebOAuthActivity.URL, url)
                .putExtra(WebOAuthActivity.LANG, WeeeOAuth.sInstance.getLang())
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        return this;
    }

}
