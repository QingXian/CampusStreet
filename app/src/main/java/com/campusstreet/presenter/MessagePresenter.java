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



    public MessagePresenter(MessageImpl messageImpl, IMessageContract.View view) {
        mMessageImpl = messageImpl;
        mView = view;

        mView.setPresenter(this);
    }


    @Override
    public void fetchMessageList() {

    }
}
