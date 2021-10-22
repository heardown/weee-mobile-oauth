package com.sayweee.oauth.iml;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.collection.ArrayMap;

import java.net.URLEncoder;
import java.util.Map;

/**
 * Author:  winds
 * Date:    2021/10/13.
 * Desc:
 */
public class AuthorizeWeeeSession implements IOAuthSession {

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
        Map<String, String> params = new ArrayMap<>();
        params.put("client_id", clientId);
        params.put("grant_type", "authorization_code");
        params.put("state", tag);
        params.put("response_type", "code");
        String target = Utils.packetUrlParams(WeeeOAuth.sInstance.getOauthWeeeUrl(), params);

        String url = "sayweee://weee.app/oauth";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url + "?target=" + URLEncoder.encode(target)));

        try {
            context.startActivityForResult(intent, WeeeOAuth.OAUTH);
        } catch (Exception e) {
            new AuthorizeSession().clientId(clientId).forceRetryLogin(forceRetryLogin).execute(context, tag);
        }
        return this;
    }
}
