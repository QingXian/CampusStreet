package com.campusstreet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.campusstreet.R;

import butterknife.ButterKnife;

/**
 * Created by Orange on 2017/4/6.
 */

public class BountyHallActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounty_hall);
        ButterKnife.bind(this);

    }
}
