package com.campusstreet.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.campusstreet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.campusstreet.common.Const.BANNER_TITLE_EXTRA;
import static com.campusstreet.common.Const.BANNER_URL_EXTRA;

/**
 * Created by Orange on 2017/5/4.
 */

public class AdActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_error)
    TextView mTvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra(BANNER_TITLE_EXTRA);
        String url = getIntent().getStringExtra(BANNER_URL_EXTRA);
        mToolbar.setTitle("");
        mToolbarTitle.setText(title);
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
        final String cacheDirPath = getCacheDir().getPath();

        final WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//启用js
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//js和android交互
        settings.setAppCachePath(cacheDirPath); //设置缓存的指定路径
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setAppCacheEnabled(true); //设置H5的缓存打开,默认关闭
        settings.setUseWideViewPort(true);//设置webview自适应屏幕大小
        settings.setLoadWithOverviewMode(true);//设置webview自适应屏幕大小
        settings.setDomStorageEnabled(true);//设置可以使用localStorage
        settings.setSupportZoom(false);//关闭zoom按钮
        settings.setBuiltInZoomControls(false);//关闭zoom

        // 告诉WebView先不要自动加载图片，等页面finish后再发起图片加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }

        // 解决硬件加速导致页面渲染闪烁
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        /**
         * webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);这个属性
         可以让webview只显示一列，也就是自适应页面大小 不能左右滑动，但在使用中发现，只针对4.4以下有效，
         因为4.4的webview内核改了，Google也在api中说了，要么改html样式，要么改变WebView；
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        }

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mWebView.setVisibility(View.GONE);
                mTvError.setVisibility(View.VISIBLE);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mProgressBar.setProgress(newProgress);//设置进度值
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //par   serURL(url); // 解析url,如果存在有跳转原生界面的url规则，则跳转原生。
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!settings.getLoadsImagesAutomatically()) {
                    settings.setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
        });

        mWebView.loadUrl(url);
    }
}
