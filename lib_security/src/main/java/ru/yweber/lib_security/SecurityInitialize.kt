package ru.yweber.lib_security

import android.app.Activity
import android.app.Application
import android.os.Bundle
import ru.yweber.lib_security.log.GlobalDateLogger
import ru.yweber.lib_security.log.SimpleFormatterTreeLogger
import ru.yweber.lib_security.log.TreeLogger
import ru.yweber.lib_security.service.LogMessage
import ru.yweber.lib_security.service.SaveLogService

/**
 * Created on 21.05.2020
 * @author YWeber */

class SecurityInitialize(
    application: Application,
    private val treeLogger: TreeLogger = SimpleFormatterTreeLogger(application)
) : Application.ActivityLifecycleCallbacks {

    init {
        application.registerActivityLifecycleCallbacks(this)
        val globalDateLogger = GlobalDateLogger(application)
        val collectTimeOs = globalDateLogger.collectTimeOs()
        SaveLogService.enqueueEvent(application, LogMessage(collectTimeOs, LogMessage.Type.TIME_OS))
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStarted(activity: Activity) {
        treeLogger.createTreeLogInActivity(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityResumed(activity: Activity) {}

}