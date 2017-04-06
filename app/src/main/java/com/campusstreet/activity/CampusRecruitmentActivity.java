package com.campusstreet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.campusstreet.R;

import butterknife.ButterKnife;

/**
 * Created by Orange on 2017/4/6.
 */

public class CampusRecruitmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_recruitment);
        ButterKnife.bind(this);

    }
}
