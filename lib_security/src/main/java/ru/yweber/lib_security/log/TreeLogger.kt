package ru.yweber.lib_security.log

import android.app.Activity

/**
 * Created on 21.05.2020
 * @author YWeber */

interface TreeLogger {
    fun createTreeLogInActivity(activity: Activity)
}