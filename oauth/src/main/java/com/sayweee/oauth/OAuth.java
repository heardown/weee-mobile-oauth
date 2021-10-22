package com.sayweee.oauth;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.sayweee.oauth.iml.OAuthCallback;
import com.sayweee.oauth.iml.WeeeOAuth;

/**
 * Author:  winds
 * Date:    2021/10/13.
 * Desc:
 */
public abstract class OAuth {

    public static OAuth shared() {
        return WeeeOAuth.sInstance;
    }

    public abstract OAuth isTest(boolean test);

    public abstract OAuth lang(String lang);

    public abstract OAuth setClientId(@NonNull String clientId);

    public abstract OAuth setOAuthCallback(@NonNull OAuthCallback callback);

    public abstract OAuth removeOAuthCallback();

    public abstract boolean onActivityResult(int requestCode, int resultCode, Intent data);

    public abstract void doOAuthWeee(@NonNull Activity context, boolean forceRetryLogin, String tag);
}
