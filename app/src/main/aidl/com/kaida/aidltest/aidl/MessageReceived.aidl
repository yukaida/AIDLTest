// MessageReceived.aidl
package com.kaida.aidltest.aidl;
import com.kaida.aidltest.data.MessageModel;
// Declare any non-default types here with import statements

interface MessageReceived {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            void onMessageReceived(in MessageModel receivedMessage);
}