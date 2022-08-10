package com.kaida.aidltest

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import com.kaida.aidltest.aidl.MessageReceived
import com.kaida.aidltest.aidl.MessageSender
import com.kaida.aidltest.data.MessageModel

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private var mBound: Boolean = false

    lateinit var messageSender: MessageSender

    val messageReceived = object : MessageReceived.Stub() {
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {

        }

        override fun onMessageReceived(receivedMessage: MessageModel) {
            Log.d(TAG, "onMessageReceived: $receivedMessage")
        }

    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, serviceBinder: IBinder) {
            Log.i(TAG, "onServiceConnected()")

            messageSender = MessageSender.Stub.asInterface(serviceBinder)
            messageSender.registerReceiveListener(messageReceived)
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        bindService(Intent(this, MessageService::class.java), serviceConnection, BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()

        if (messageSender != null && messageSender.asBinder().isBinderAlive) {
            messageSender.unRegisterReceiveListener(messageReceived)
        }
        unbindService(serviceConnection)
        mBound = false
    }

    fun onButtonClick(view: View) {
        try {
            messageSender.sendMessage(
                MessageModel(
                    "MainActivity",
                    "MessageService",
                    "content form Activity"
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}