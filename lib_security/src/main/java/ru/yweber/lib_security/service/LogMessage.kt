package ru.yweber.lib_security.service

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created on 23.05.2020
 * @author YWeber */

@Parcelize
data class LogMessage(
    val message: String,
    val type: Type
) : Parcelable {

    enum class Type {
        TREE, TOUCH, TIME_OS
    }
}