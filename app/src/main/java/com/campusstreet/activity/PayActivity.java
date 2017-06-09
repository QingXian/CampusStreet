package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.campusstreet.R;
import com.campusstreet.api.PayClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.PayResult;
import com.campusstreet.entity.UserInfo;
import com.google.gson.JsonObject;

import java.lang.ref.WeakReference;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Orange on 2017/6/1.
 */

public class PayActivity extends AppCompatActivity {

    private static final int SDK_PAY_FLAG = 1;
    public static final String RECHARGE_MONEY_EXTRA = "recharge_money_extra";

    public static final String APPID = "2017060507421835";
    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088721190992112";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "houyiyunduan2017";

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_recharge_money)
    EditText mEtRechargeMoney;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;

    private PayClient mPayClient;
    private String mMoney;  // 要充值的金额
    private MyHandler mHandler;
    private UserInfo mUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText("充值");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        mPayClient = ServiceGenerator.createService(this, PayClient.class);
        mHandler = new MyHandler(this);
    }

    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        if (mUserInfo != null) {
            mMoney = mEtRechargeMoney.getText().toString().trim();
            try {

                double mDblMoney = Double.valueOf(mMoney);
                if (!mMoney.matches("^[0-9]\\d*(\\.?\\d{1,2})?$") || mMoney.equals("0.00")
                        || mDblMoney > 10000) {
                    showMessage("金额不合法");
                    return;
                }
            } catch (NumberFormatException nfe) {
                showMessage("金额不合法");
                return;
            }
            setLoadingIndicator(true);
            rechargeMoney(mMoney);
        }
    }

    private void rechargeMoney(String money) {
        mMoney = money;

        Call<JsonObject> call = mPayClient.getalipaysign(mUserInfo.getUid(), mMoney);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        final String orderInfo = bodyJson.get(Const.MESSAGE_KEY).getAsString();
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask payTask = new PayTask(PayActivity.this);
                                Map<String, String> result = payTask.payV2(orderInfo, true);

                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };

                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    } else {
                        showMessage(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                showMessage("网络异常");
                setLoadingIndicator(false);
            }
        });
    }

    public void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                //设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.operating_progress_bar_title);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void showMessage(String msg) {
        if (this != null && !this.isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<PayActivity> mTarget;

        public MyHandler(PayActivity target) {
            mTarget = new WeakReference<PayActivity>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayActivity target = mTarget.get();
                    if (target != null) {
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         返回结果需要通过resultStatus以及result字段的值来综合判断并确定支付结果。在resultStatus=9000，
                         并且success=“true”以及sign=“xxx”校验通过的情况下，证明支付成功，其它情况归为失败。
                         较低安全级别的场合，也可以只通过检查resultStatus以及success=“true”来判定支付结果。
                         */
                        String resultInfo = payResult.getResult();  // 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            Toast.makeText(target, "支付成功", Toast.LENGTH_LONG).show();
                            Intent data = new Intent();
                            data.putExtra(RECHARGE_MONEY_EXTRA, target.mMoney);
                            target.setResult(RESULT_OK, data);
                            target.mEtRechargeMoney.setText("");
                            target.setLoadingIndicator(false);
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(target, "支付失败", Toast.LENGTH_LONG).show();
                            target.setResult(RESULT_CANCELED);
                            target.setLoadingIndicator(false);
                        }
                    }
            }
        }
    }
}
