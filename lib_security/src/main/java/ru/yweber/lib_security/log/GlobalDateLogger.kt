package ru.yweber.lib_security.log

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.os.SystemClock
import android.provider.Telephony
import ru.yweber.lib_security.utils.br
import ru.yweber.lib_security.utils.hooks
import ru.yweber.lib_security.utils.sp

/**
 * Created on 24.05.2020
 * @author YWeber */

class GlobalDateLogger(private val application: Application) {

    fun collectTimeOs(): String {
        val currentTime = System.currentTimeMillis()
        // spend time for last start android os
        val uptimeSecondsStartOs = SystemClock.elapsedRealtime()
        //  date install os
        val installTime = Build.TIME
        // time first boot android os
        val firstInstallTime = firstBootSystemOsTime()

        val stringBuilder = StringBuilder()
        stringBuilder.br().br().sp(" StartApp ").br().hooks {
            it.sp("currentTime : $currentTime")
                .sp("uptimeSecondsStartOs : $uptimeSecondsStartOs")
                .sp("installTime : $installTime ")
                .sp("firstInstallTime : $firstInstallTime")
        }
        return stringBuilder.toString()
    }

    private fun firstBootSystemOsTime(): Long? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(application)
            val packageManager = application.packageManager
            if (isPackageInstalled(defaultSmsPackage, packageManager)) {
                packageManager.getPackageInfo(defaultSmsPackage, 0).firstInstallTime
            } else null
        } else {
            null
        }
    }

    private fun isPackageInstalled(packageName: String?, packageManager: PackageManager): Boolean {
        if (packageName == null) return false
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}