package com.sayweee.oauth.iml;

import androidx.annotation.Keep;

import java.util.Map;

/**
 * Author:  winds
 * Date:    2021/10/13.
 * Desc:
 */
@Keep
public class OAuthBean {

    public String error;
    public String description;
    public String tag;
    public String code;

    public OAuthBean(Map<String, String> data) {
        if (data != null && data.size() > 0) {
            error = data.get("error");
            description = data.get("error_description");
            tag = data.get("state");
            code = data.get("code");
        }
    }

    public boolean isSuccess() {
        return code != null && code.length() > 0;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    public String getTag() {
        return tag;
    }
}
