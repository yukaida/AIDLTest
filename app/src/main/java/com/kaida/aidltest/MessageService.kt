package com.kaida.aidltest

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import com.kaida.aidltest.aidl.MessageReceived
import com.kaida.aidltest.data.MessageModel
import com.kaida.aidltest.aidl.MessageSender
import kotlin.concurrent.thread

private const val TAG = "MessageService"

class MessageService : Service() {

    val remoteClientList = RemoteCallbackList<MessageReceived>()

    private val binder = object : MessageSender.Stub() {
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {
        }

        override fun sendMessage(messageModel: MessageModel) {
            Log.d(TAG, "setMessageModel : get from activity \n $messageModel")
        }

        override fun registerReceiveListener(messageReceiver: MessageReceived) {
            remoteClientList.register(messageReceiver)
        }

        override fun unRegisterReceiveListener(messageReceiver: MessageReceived) {
            remoteClientList.unregister(messageReceiver)
        }


    }

    override fun onBind(intent: Intent): IBinder {
        thread {

            while (true) {

                Thread.sleep(5000)
                var count = remoteClientList.beginBroadcast()
                for (index in 0 until count) {
                    remoteClientList.getBroadcastItem(index).onMessageReceived(
                        MessageModel(
                            "MessageService",
                            "MainActivity",
                            "content from MessageService"
                        )
                    )
                }
                remoteClientList.finishBroadcast()
            }

        }

        return binder
    }


}