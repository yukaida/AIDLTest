package com.kaida.aidltest.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageModel(var from: String, var to: String, var content: String) : Parcelable