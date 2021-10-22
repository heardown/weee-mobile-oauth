package com.sayweee.oauth.iml;

import android.app.Activity;

/**
 * Author:  winds
 * Date:    2021/10/13.
 * Desc:
 */
public interface IOAuthSession {

    IOAuthSession forceRetryLogin(boolean force);

    IOAuthSession clientId(String clientId);

    IOAuthSession execute(Activity context, String state);
}
