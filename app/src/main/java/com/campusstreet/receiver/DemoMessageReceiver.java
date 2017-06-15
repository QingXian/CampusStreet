package com.campusstreet.receiver;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.campusstreet.activity.BountyHallDetailActivity;
import com.campusstreet.activity.BuyZoneDetailActivity;
import com.campusstreet.activity.CampusInformationDetailActivity;
import com.campusstreet.activity.CampusRecruitmentDetailActivity;
import com.campusstreet.activity.IdleSaleDetailActivity;
import com.campusstreet.activity.PartnerDetailActivity;
import com.campusstreet.activity.PostDetailActivity;
import com.google.gson.JsonObject;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.campusstreet.common.Const.ID_EXTRA;
import static com.campusstreet.common.Const.TYPE;

/**
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br/>
 * 2、需要将自定义的 DemoMessageReceiver 注册在 AndroidManifest.xml 文件中：
 * <pre>
 * {@code
 *  <receiver
 *      android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"
 *      android:exported="true">
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.ERROR" />
 *      </intent-filter>
 *  </receiver>
 *  }</pre>
 * 3、DemoMessageReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br/>
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br/>
 * 5、DemoMessageReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
 * 6、DemoMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br/>
 * 7、DemoMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
 * 8、以上这些方法运行在非 UI 线程中。
 *
 * @author mayixiang
 */
public class DemoMessageReceiver extends PushMessageReceiver {

    private static final String TAG = "DemoMessageReceiver";
    public static final String EMERGENCY_ID_EXTRA = "emergency_id";
    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        Log.d(TAG, "onReceivePassThroughMessage: is called");
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        Log.d(TAG, "onNotificationMessageClicked: is called");
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
        if (message.getExtra() != null && message.getExtra().get("mainid") != null) {
            if (message.getExtra() != null) {
                switch (message.getExtra().get("type")) {
                    case "1":
                        Intent intent = new Intent(context, IdleSaleDetailActivity.class);
                        intent.putExtra(ID_EXTRA, message.getExtra().get("mainid"));
                        context.startActivity(intent);
                        break;
                    case "2":
                        intent = new Intent(context, BuyZoneDetailActivity.class);
                        intent.putExtra(ID_EXTRA, message.getExtra().get("mainid"));
                        context.startActivity(intent);
                        break;
                    case "3":
                        intent = new Intent(context, CampusRecruitmentDetailActivity.class);
                        intent.putExtra(TYPE, message.getExtra().get("type"));
                        intent.putExtra(ID_EXTRA, message.getExtra().get("mainid"));
                        context.startActivity(intent);
                        break;
                    case "4":
                        intent = new Intent(context, BountyHallDetailActivity.class);
                        intent.putExtra(ID_EXTRA, message.getExtra().get("mainid"));
                        context.startActivity(intent);
                        break;
                    case "5":
                        intent = new Intent(context, PostDetailActivity.class);
                        intent.putExtra(ID_EXTRA, message.getExtra().get("mainid"));
                        context.startActivity(intent);
                        break;
                    case "6":
                        intent = new Intent(context, CampusInformationDetailActivity.class);
                        intent.putExtra(ID_EXTRA, message.getExtra().get("mainid"));
                        context.startActivity(intent);
                        break;
                    case "7":
                        intent = new Intent(context, PartnerDetailActivity.class);
                        intent.putExtra(ID_EXTRA, message.getExtra().get("mainid"));
                        context.startActivity(intent);
                        break;
                    case "8":
                        intent = new Intent(context, CampusRecruitmentDetailActivity.class);
                        intent.putExtra(TYPE, message.getExtra().get("type"));
                        intent.putExtra(ID_EXTRA, message.getExtra().get("mainid"));
                        context.startActivity(intent);
                        break;
                }
            }
        }
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        Log.d(TAG, "onNotificationMessageArrived: is called");
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
        Log.d(TAG, "onNotificationMessageArrived: <== " + mMessage);
    }

    @Override
    public void onCommandResult(final Context context, MiPushCommandMessage message) {
        Log.d(TAG, "onCommandResult: is called");
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        Log.d(TAG, "onReceiveRegisterResult: is called");
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                Log.d(TAG, "onReceiveRegisterResult: 注册推送服务成功 regId <== " + mRegId);
            }
        }
    }

}
