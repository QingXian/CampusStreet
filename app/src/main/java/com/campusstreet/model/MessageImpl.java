package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Orange on 2017/4/16.
 */

public class MessageImpl implements IMessageBiz{

    private final String TAG = this.getClass().getSimpleName();
    private static MessageImpl sMessageImpl;



    private MessageImpl(Context context) {
    }

    public static MessageImpl getInstance(Context context) {

        if (sMessageImpl == null) {
            sMessageImpl = new MessageImpl(context);
        }
        return sMessageImpl;
    }
    
    @Override
    public void getMessageList(@NonNull LoadMessageListCallback callback) {

    }
}
