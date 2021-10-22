package com.sayweee.oauth.iml;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Author:  winds
 * Date:    2021/10/14.
 * Desc:
 */
class Utils {

    public static String packetUrlParams(String url, Map<String, String> params) {
        if (params != null && params.size() > 0) {
            StringBuilder builder = new StringBuilder(url);
            builder.append("?");
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.append(key).append("=").append(value).append("&");
            }
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
            return builder.toString();
        }
        return url;
    }

    public static Map<String, String> parseQueryParams(String url) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("?")) {
                int index = url.indexOf("?");
                String query = url.substring(index + 1);
                if (!TextUtils.isEmpty(query)) {
                    String[] arr = query.split("&");
                    if (arr.length > 0) {
                        for (String path : arr) {
                            if (!TextUtils.isEmpty(path)) {
                                String[] split = path.split("=");
                                if (split.length == 2) {
                                    String key = split[0];
                                    String value = split[1];
                                    if (!TextUtils.isEmpty(key)) {
                                        params.put(key, value);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return params;
    }

    public static boolean isAppInstalled(Context context, String pkgName) {
        if (!TextUtils.isEmpty(pkgName)) {
            try {
                final PackageManager packageManager = context.getPackageManager();
                // 获取所有已安装程序的包信息
                @SuppressLint("QueryPermissionsNeeded")
                List<PackageInfo> info = packageManager.getInstalledPackages(0);
                for (int i = 0; i < info.size(); i++) {
                    if (pkgName.equals(info.get(i).packageName)) {
                        return true;
                    }
                }
            } catch (Exception ignored) {
                try {
                    ApplicationInfo n = context.getPackageManager().getApplicationInfo(pkgName, PackageManager.GET_UNINSTALLED_PACKAGES);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        try {
            @SuppressLint("QueryPermissionsNeeded")
            List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, 0);
            return list != null && list.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static Locale getCurrentLocal(Context context) {
        if (context != null) {
            Configuration config = context.getResources().getConfiguration();
            // API 版本兼容
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return config.getLocales().get(0);
            } else {
                return config.locale;
            }
        }
        return null;
    }

    public static String getCurrentLang(Context context) {
        Locale locale = getCurrentLocal(context);
        if (locale == null) {
            locale = Locale.US;
        }
        String language = locale.getLanguage();
        if ("zh".equalsIgnoreCase(language)) {
            String country = locale.getCountry();
            if ("TW".equalsIgnoreCase(country)         //华 - 台湾
                    || "HK".equalsIgnoreCase(country)  //华 - 香港
                    || "MO".equalsIgnoreCase(country)  //华 - 澳门
                    || "CHT".equalsIgnoreCase(country) //华 (传统的)
            ) {
                return "zh-Hant";
            }
        }
        return language;
    }

}
