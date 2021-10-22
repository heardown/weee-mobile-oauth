
# WeeeKit SDK Integration Guide

## Android SDK
### 集成步骤

1. 将lib添加到build文件中，如下
```
   maven { url  "http://maven.sayweee.net:8081/repository/maven-releases/" }

   implementation 'com.sayweee.libs:oauth:0.0.6'
```

2. 集成后，使用步骤
```
  //配置
  OAuth.shared().setClientId(clientId) //必传
                .isTest(true) //默认false，可不设置，用于环境切换，true tb1环境

  //获取授权
  OAuth.shared().setOAuthCallback(new OAuthCallback() {     //设置回调 必传
            @Override
            public void onResult(boolean success, OAuthBean data) {
                //data即为授权数据
            }
        })
        .doOAuthWeee(getActivity(), client_secret);

 //页面onActivityResult增加回调处理
 OAuth.shared().onActivityResult(requestCode, resultCode, data);

  //为防止内存泄漏，在获取授权后，务必移除当前的回调
  @Override
  public void onDestroy() {
       super.onDestroy();
       OAuth.shared().removeOAuthCallback()
  }
```

3. 最低支持版本
```
    sdk: 0.0.6
    app: 12.5.3
```

