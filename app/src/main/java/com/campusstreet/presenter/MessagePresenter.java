package com.campusstreet.presenter;

import android.util.Log;

import com.campusstreet.contract.IMessageContract;
import com.campusstreet.contract.IMessageContract;
import com.campusstreet.model.MessageImpl;

/**
 * Created by Orange on 2017/4/17.
 */

public class MessagePresenter implements IMessageContract.Presenter {
    
    public static final String TAG = "MessagePresenter";

    private MessageImpl mMessageImpl;
    private IMessageContract.View mView;



    public MessagePresenter(MessageImpl homeImpl, IMessageContract.View view) {
        mMessageImpl = homeImpl;
        mView = view;

        mView.setPresenter(this);
    }
    
    @Override
    public void start() {
        Log.d(TAG, "start: 开始");
    }

    @Override
    public void fetchMessageList() {

    }
}
