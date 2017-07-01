package com.campusstreet.wxapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
import com.campusstreet.activity.BaseActivity;
import com.campusstreet.activity.LoginActivity;
import com.campusstreet.common.Const;
import com.campusstreet.entity.UserWxInfo;
import com.campusstreet.utils.PreferencesUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.campusstreet.common.AppConfig.SERVER_HOST;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
//import com.imtoy.wechatdemo.util.JsonUtils;
import com.campusstreet.utils.Util;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.BaseResp.ErrCode;
//import com.tencent.mm.sdk.openapi.JumpToBizProfile;
import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.openapi.SendAuth.Resp;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.SendMessageToWX.Req;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXMusicObject;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXVideoObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


/**
 * 
 * @description 微信第三方登录，分享demo,更多移动开发内容请关注： http://blog.csdn.net/xiong_it
 * @charset UTF-8
 * @author xiong_it 
 * @date 2015-9-9下午2:50:14
 * @version
 */
/*
 * 微信登录，分享应用中必须有这个名字叫WXEntryActivity，并且必须在wxapi包名下，腾讯官方文档中有要求
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

	@BindView(R.id.btn_wx_login)
	Button mBtnWxLogin;

	private static final String TAG = "WXEntryActivity";
	
	public static final String APP_ID = "wxf2cbad533bd474c5";// 微信开放平台申请到的app_id
	public static final String APP_SECRET = "e165318ff3024376484071d80f302890";// 微信开放平台申请到的app_id对应的app_secret
	private static final String WEIXIN_SCOPE = "snsapi_userinfo";// 用于请求用户信息的作用域
	private static final String WEIXIN_STATE = "login_state"; // 自定义
	
	protected static final int RETURN_OPENID_ACCESSTOKEN = 0;// 返回openid，accessToken消息码
	protected static final int RETURN_NICKNAME_UID = 1; // 返回昵称，uid消息码
	
//	protected static final int THUMB_SIZE = 150;// 分享的图片大小
	
	private IWXAPI api;
	private SendAuth.Req req;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RETURN_OPENID_ACCESSTOKEN:
				Bundle bundle1 = (Bundle) msg.obj;
				String accessToken = bundle1.getString("access_token");
				String openId = bundle1.getString("open_id");
				Log.i("xxxxxxxxxx", "handleMessage===0");
				getUID(openId, accessToken);
				break;

			case RETURN_NICKNAME_UID:
				Log.i("xxxxxxxxxx", "handleMessage===1");
				JSONObject jsonObj = (JSONObject) msg.obj;
				Log.i("xxxxxxxxxx", jsonObj.toString());
				try {
					String nickname = jsonObj.getString("nickname");
					Log.i("xxxxxxxxxx",nickname);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					String unionid = jsonObj.getString("unionid");
					Log.i("xxxxxxxxxx",unionid);

				} catch (JSONException e) {
					e.printStackTrace();
				}
				Gson gson = new GsonBuilder().setLenient().create();
//				UserWxInfo wxInfo = gson.fromJson(jsonObj.toString(),UserWxInfo.class);
				PreferencesUtil.getDefaultPreferences(WXEntryActivity.this, Const.PREF_USER)
						.edit()
						.putString(Const.PREF_WEIXIN_INFO_KEY, jsonObj.toString())
						.apply();
				Intent intent = new Intent(WXEntryActivity.this , LoginActivity.class);
				startActivity(intent);
				WXEntryActivity.this.finish();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wx_login);
		ButterKnife.bind(this);

		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.registerApp(APP_ID);//

		try {
			api.handleIntent(getIntent(), this);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

//		if (!api.isWXAppInstalled())
//		{
//			Toast.makeText(WXEntryActivity.this, "没有安装微信,请先安装微信!", Toast.LENGTH_SHORT).show();
//			this.finish();
//			return;
//		}


	}

	@OnClick({R.id.btn_wx_login})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.btn_wx_login:
				// 请求授权登录
				Toast.makeText(getApplicationContext(), "登录微信",
						Toast.LENGTH_LONG).show();
				Log.i(TAG, "登录微信");
				sendAuth();
				break;
		}
	}

	/**
	 * 构造一个用于请求的唯一标识
	 * @param type 分享的内容类型
	 * @return 
	 */
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	/**
	 * 申请授权
	 */
	private void sendAuth() {
		req = new SendAuth.Req();
		req.scope = WEIXIN_SCOPE;
		req.state = WEIXIN_STATE;
		api.sendReq(req);
	}
//
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.i(TAG, "onNewIntent");
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	/**
	 * 请求回调接口
	 */
	@Override
	public void onReq(BaseReq req) {
		Log.i(TAG, "onReq");
	}
	
    /**
     * 请求响应回调接口
     */
	@Override
	public void onResp(BaseResp resp) {
		Log.i("xxxxxxxxxx", "onResp+");

		SendAuth.Resp sendAuthResp = (SendAuth.Resp) resp;// 用于分享时不要有这个，不能强转
		Log.i("xxxxxxxxxx", "onResp+");
		if (sendAuthResp == null)
		{
			Log.e("xxxxxxx","error");
		}

		String code = sendAuthResp.token;
		Log.i("xxxxxxxxxx", "code = " + code);

		if (resp.errCode == ErrCode.ERR_OK) {
//			Toast.makeText(this, "errCode = " +  ErrCode.ERR_OK, Toast.LENGTH_SHORT).show();
//			this.finish();
			getResult(code);
		}
		else
		{
			Toast.makeText(this, "errCode = " +  resp.errCode, Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 获取openid accessToken值用于后期操作
	 * @param code 请求码
	 */
	private void getResult(final String code) {
		Log.i("xxxxxxxxxx", "getResult");
		new Thread() {// 开启工作线程进行网络请求
			public void run() {
				final String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
						+ APP_ID
						+ "&secret="
						+ APP_SECRET
						+ "&code="
						+ code
						+ "&grant_type=authorization_code";

					//创建okHttpClient对象
					OkHttpClient mOkHttpClient = new OkHttpClient();
					//创建一个Request
					final Request request = new Request.Builder()
							.url(path)
							.build();
						//new call
					Call call = mOkHttpClient.newCall(request);
					//请求加入调度
					call.enqueue(new Callback() {
						@Override
						public void onFailure(Call call, IOException e) {
							Log.e("xxxxxxxxxx", "onFailure: ");
							WXEntryActivity.this.finish();
						}

						@Override
						public void onResponse(Call call, Response response) throws IOException {
							Log.i("xxxxxxxxxx", "getResult onResponse");

							String httpStr =  response.body().string();
							Log.i("xxxxxxxxxx",httpStr);
							JSONObject jsonObject = null; // 请求https连接并得到json结果
							try {
								jsonObject = new JSONObject(httpStr);
							} catch (JSONException e) {
								e.printStackTrace();
							}

							if (null != jsonObject) {
								String openid = null;
								try {
									openid = jsonObject.getString("openid")
                                            .toString().trim();
								} catch (JSONException e) {
									e.printStackTrace();
								}
								String access_token = null;
								try {
									access_token = jsonObject
                                            .getString("access_token").toString().trim();
								} catch (JSONException e) {
									e.printStackTrace();
								}
								Log.i("xxxxxxxxxx", "openid = " + openid);
								Log.i("xxxxxxxxxx", "access_token = " + access_token);

								Message msg = handler.obtainMessage();
								msg.what = RETURN_OPENID_ACCESSTOKEN;
								Bundle bundle = new Bundle();
								bundle.putString("openid", openid);
								bundle.putString("access_token", access_token);
								msg.obj = bundle;
								handler.sendMessage(msg);
							}
						}
					});

				return;
			}
		}.start();
	}

	/**
	 * 获取用户唯一标识
	 * @param openId
	 * @param accessToken
	 */
	private void getUID(final String openId, final String accessToken) {
		Log.i("xxxxxxxxxx", "getUID");

		new Thread() {
			@Override
			public void run() {
				String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
						+ accessToken + "&openid=" + openId;
				JSONObject jsonObject = null;

				//创建okHttpClient对象
				OkHttpClient mOkHttpClient = new OkHttpClient();
				//创建一个Request
				final Request request = new Request.Builder()
						.url(path)
						.build();
				//new call
				Call call = mOkHttpClient.newCall(request);
				//请求加入调度
				call.enqueue(new Callback() {
					@Override
					public void onFailure(Call call, IOException e) {
						Log.e("getUID", "onFailure: ");
						WXEntryActivity.this.finish();
					}
					@Override
					public void onResponse(Call call, Response response) throws IOException {
						Log.i("xxxxxxxxxx", "getUID onResponse");
						JSONObject jsonObj = null;
						try {
							 jsonObj = new JSONObject(response.body().string());
						} catch (JSONException e) {
							e.printStackTrace();
						}
						try {
							String nickname = jsonObj.getString("nickname");
							Log.i("xxxxxxxxxx",nickname);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						try {
							String unionid = jsonObj.getString("unionid");
							Log.i("xxxxxxxxxx",unionid);

						} catch (JSONException e) {
							e.printStackTrace();
						}
						Message msg = handler.obtainMessage();
						msg.what = RETURN_NICKNAME_UID;
						msg.obj = jsonObj;
						handler.sendMessage(msg);
					}
				});


			}
		}.start();
	}
	
}
