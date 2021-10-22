package com.sayweee.oauth.iml;

/**
 * Author:  winds
 * Date:    2021/10/13.
 * Desc:
 */
public interface OAuthCallback {

    void onResult(boolean success, OAuthBean data);

}
