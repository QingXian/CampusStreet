package com.campusstreet.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Orange on 2017/6/9.
 */

public class BaseActivity extends AppCompatActivity {
    private Toast mToast;

    public void showMessage(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
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
    public void onBackPressed() {
        cancelToast();
        super.onBackPressed();
    }
}
