// MessageSender.aidl
package com.kaida.aidltest.aidl;
import com.kaida.aidltest.data.MessageModel;
import com.kaida.aidltest.aidl.MessageReceived;
// Declare any non-default types here with import statements

interface MessageSender {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    //客户端发送消息
    void sendMessage(in MessageModel messageModel);


    void registerReceiveListener(MessageReceived messageReceiver);
    void unRegisterReceiveListener(MessageReceived messageReceiver);
}