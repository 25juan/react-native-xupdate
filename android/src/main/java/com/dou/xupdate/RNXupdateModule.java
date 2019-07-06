
package com.dou.xupdate;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class RNXupdateModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNXupdateModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    //this.initUpdate();
    this.initOKHttpUtils();
  }

  public static void initUpdate(Context context, String packageName, Application application) {
    XUpdate.get()
            .debug(false)
            .isWifiOnly(false)                                               //默认设置只在wifi下检查版本更新
            .isGet(true)                                                    //默认设置使用get请求检查版本
            .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
            .param("versionCode", UpdateUtils.getVersionCode(context))         //设置默认公共请求参数
            .param("appKey", packageName)
            .setOnUpdateFailureListener(new OnUpdateFailureListener() {     //设置版本更新出错的监听
              @Override
              public void onFailure(UpdateError error) {
                if (error.getCode() != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
                  //Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT);
                }
              }
            })
            .supportSilentInstall(false)                                     //设置是否支持静默安装，默认是true
            .setIUpdateHttpService(new OKHttpUpdateHttpService())           //这个必须设置！实现网络请求功能。
            .init(application);
  }

  @ReactMethod
  public void update(String updateUrl){
    XUpdate.newBuild(getCurrentActivity())
            .updateUrl(updateUrl)
            .update();
  }

  private void initOKHttpUtils() {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20000L, TimeUnit.MILLISECONDS)
            .readTimeout(20000L, TimeUnit.MILLISECONDS)
            .build();
    OkHttpUtils.initClient(okHttpClient);
  }

  @Override
  public String getName() {
    return "RNXupdate";
  }


}
