package com.campusstreet.presenter;

import android.util.Log;

import com.campusstreet.contract.IMessageContract;
import com.campusstreet.contract.IMessageContract;
import com.campusstreet.entity.MessageInfo;
import com.campusstreet.model.IMessageBiz;
import com.campusstreet.model.MessageImpl;

import java.util.Collections;
import java.util.List;

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
    public void fetchMessageList(String uid, int pi) {
        mMessageImpl.getMessageList(uid, pi, new IMessageBiz.LoadMessageListCallback() {
            @Override
            public void onMessageListLoaded(List<MessageInfo> messageInfos) {
                mView.setMessageList(messageInfos);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void readMessage(String uid, int smsids) {
        mMessageImpl.onReadMessage(uid, smsids, new IMessageBiz.onReadMessageCallback() {
            @Override
            public void onReadMessageSuccess() {
                mView.showReadMessageSuccess();
            }

            @Override
            public void onReadMessageFailure(String errorMsg) {
                mView.showErrorMsg(errorMsg);
            }
        });
    }
}
