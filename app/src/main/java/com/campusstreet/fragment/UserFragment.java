package com.campusstreet.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.activity.AssociationActivity;
import com.campusstreet.activity.JoinTaskActivity;
import com.campusstreet.activity.LoginActivity;
import com.campusstreet.activity.MyBountyHallActivity;
import com.campusstreet.activity.MyBuyZoneActivity;
import com.campusstreet.activity.MyIdleSaleActivity;
import com.campusstreet.activity.PayActivity;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.api.UserClient;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.IVersionBiz;
import com.campusstreet.model.VersionImpl;
import com.campusstreet.utils.GetLocalVersionUtil;
import com.campusstreet.utils.InternetStateUtil;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.getVersion;

/**
 * Created by Orange on 2017/4/1.
 */

public class UserFragment extends Fragment {
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    @BindView(R.id.tv_recharge)
    TextView mTvRecharge;
    @BindView(R.id.tv_bounty_mission)
    TextView mTvBountyMission;
    @BindView(R.id.tv_my_idle)
    TextView mTvMyIdle;
    @BindView(R.id.tv_my_club)
    TextView mTvMyClub;
    @BindView(R.id.tv_about_us)
    TextView mTvAboutUs;
    @BindView(R.id.rl_user_info)
    RelativeLayout mRlUserInfo;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_user_idle)
    TextView mTvUserIdle;
    @BindView(R.id.tv_join_task)
    TextView mTvJoinTask;
    @BindView(R.id.tv_update)
    TextView mTvUpdate;
    private Unbinder mUnbinder;
    private UserInfo mUserInfo;
    private Toast mToast;
    private UserClient mUserClient;
    private ProgressDialog mProgressDlg;
    private Handler mHandler;

    public static UserFragment newInstance(UserInfo userInfo) {
        Bundle args = new Bundle();
        args.putSerializable(Const.USERINFO_EXTRA, userInfo);
        UserFragment userFragment = new UserFragment();
        userFragment.setArguments(args);
        return userFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserInfo = (UserInfo) getArguments().getSerializable(Const.USERINFO_EXTRA);
        }
        mUserClient = ServiceGenerator.createService(getActivity().getApplicationContext(), UserClient.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        if (mUserInfo != null) {
            mRlUserInfo.setVisibility(View.VISIBLE);
            mTvLogin.setVisibility(View.GONE);
            getUserPot(mUserInfo.getUid());
            initView();
        } else {
            mRlUserInfo.setVisibility(View.GONE);
            mTvLogin.setVisibility(View.VISIBLE);
        }
        return root;
    }

    @Override
    public void onResume() {
        if (mUserInfo != null) {
            getUserPot(mUserInfo.getUid());
        }
        super.onResume();
    }

    private void getUserPot(String uid) {
        Call<JsonObject> call = mUserClient.getUserPot(uid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        mUserInfo.setPoint(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                        double price = Double.parseDouble(mUserInfo.getPoint());
                        DecimalFormat df = new DecimalFormat("0.00");
                        String strprice = df.format(price);
                        mTvBalance.setText("余额：" + strprice);
                    } else {
                        showMessage(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                showMessage("网络异常");
            }
        });
    }

    private void initView() {
        Picasso.with(getActivity())
                .load(AppConfig.AVATAR_SERVER_HOST + mUserInfo.getUserpic())
                .fit()
                .error(R.drawable.ic_head_test)
                .into(mIvHead);
        mTvDepartment.setText(mUserInfo.getMajor());
        mTvName.setText(mUserInfo.getUsername());
        if (mUserInfo.getPoint() != null) {
            double price = Double.parseDouble(mUserInfo.getPoint());
            DecimalFormat df = new DecimalFormat("0.00");
            String strprice = df.format(price);
            mTvBalance.setText("余额：" + strprice);
        } else
            mTvBalance.setText("余额：0");
        mProgressDlg = new ProgressDialog(getActivity());
        mProgressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        mProgressDlg.setIndeterminate(false);
        mHandler = new Handler();
    }


    @OnClick({R.id.tv_recharge, R.id.tv_bounty_mission, R.id.tv_user_idle, R.id.tv_my_idle, R.id.tv_my_club, R.id.tv_login, R.id.tv_join_task, R.id.tv_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge:
                if (mUserInfo != null) {
                    Intent intent = new Intent(getActivity(), PayActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("请先登录");
                }
                break;
            case R.id.tv_bounty_mission:
                if (mUserInfo != null) {
                    Intent intent = new Intent(getActivity(), MyBountyHallActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("请先登录");
                }
                break;
            case R.id.tv_user_idle:
                if (mUserInfo != null) {
                    Intent intent = new Intent(getActivity(), MyIdleSaleActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("请先登录");
                }
                break;
            case R.id.tv_my_idle:
                if (mUserInfo != null) {
                    Intent intent = new Intent(getActivity(), MyBuyZoneActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("请先登录");
                }
                break;
            case R.id.tv_my_club:
                if (mUserInfo != null) {
                    Intent intent = new Intent(getActivity(), AssociationActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    intent.putExtra(Const.TYPE, 1);
                    startActivity(intent);
                } else {
                    showMessage("请先登录");
                }
                break;
            case R.id.tv_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.tv_join_task:
                if (mUserInfo != null) {
                    intent = new Intent(getActivity(), JoinTaskActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    intent.putExtra(Const.TYPE, 1);
                    startActivity(intent);
                } else {
                    showMessage("请先登录");
                }
                break;
            case R.id.tv_update:
                showAlertDialog("软件更新");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void showMessage(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        cancelToast();
    }

    @OnClick(R.id.tv_update)
    public void onViewClicked() {
    }

    public void showAlertDialog(final String msg) {
        VersionImpl versionImpl = new VersionImpl(getActivity().getApplicationContext(), GetLocalVersionUtil.getVerName(getActivity()));
        versionImpl.getVersion(new IVersionBiz.LoadVersionCallback() {
            @Override
            public void onVersionLoad(final String appUrl) {
                new AlertDialog.Builder(getActivity()).setTitle(msg)
                        .setMessage("发现新版本是否更新")
                        .setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (InternetStateUtil.isWifi(getActivity())) {
                                    mProgressDlg.setTitle("正在下载");
                                    mProgressDlg.setMessage("请稍候...");
                                    downFile(AppConfig.APP_SERVER_HOST + appUrl);
                                } else {
                                    new AlertDialog.Builder(getActivity()).setTitle("确认提示")
                                            .setMessage("当前为非WIFI网络，是否继续下载")
                                            .setPositiveButton("继续下载", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    mProgressDlg.setTitle("正在下载");
                                                    mProgressDlg.setMessage("请稍候...");
                                                    downFile(AppConfig.APP_SERVER_HOST + appUrl);
                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            })
                                            .show();

                                }

                            }
                        })
                        .setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }

            @Override
            public void onDataNotAvailable() {
                new AlertDialog.Builder(getActivity()).setTitle(msg)
                        .setMessage("当前版本是：" + GetLocalVersionUtil.getVerName(getActivity()) + "\n已经是最新版本")
                        .show();
            }
        });
    }

    public void downFile(final String url) {
        mProgressDlg.show();
        new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                HttpResponse response;
                try {
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    long length = entity.getContentLength();

                    mProgressDlg.setMax((int) length);//设置进度条的最大值

                    InputStream is = entity.getContent();
                    FileOutputStream fileOutputStream = null;
                    if (is != null) {
                        File file = new File(
                                Environment.getExternalStorageDirectory(),
                                "fireant.apk");
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int ch = -1;
                        int count = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            count += ch;
                            if (length > 0) {
                                mProgressDlg.setProgress(count);//设置当前进度
                            }
                        }
                    }
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    down();  //告诉HANDER已经下载完成了，可以安装了
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void down() {
        mHandler.post(new Runnable() {
            public void run() {
                mProgressDlg.cancel();
                update();
            }
        });
    }

    void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "fireant.apk")),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

}
