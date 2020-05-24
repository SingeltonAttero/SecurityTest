package ru.yweber.lib_security.service

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import ru.yweber.lib_security.storage.FileLoggerStorage

/**
 * Created on 23.05.2020
 * @author YWeber */

class SaveLogService : JobIntentService() {

    private val storage by lazy { FileLoggerStorage(applicationContext) }

    override fun onHandleWork(intent: Intent) {
        val logMessage = intent.getParcelableExtra<LogMessage>(EVENT_MESSAGE_EXTRA)
        if (logMessage != null) {
            storage.saveLog(logMessage)
        }
    }

    companion object {
        private const val JOB_ID = 1000
        private const val EVENT_MESSAGE_EXTRA = "event message extra $JOB_ID"
        fun enqueueEvent(context: Context, log: LogMessage) {
            val intent = Intent()
            intent.putExtra(EVENT_MESSAGE_EXTRA, log)
            enqueueWork(context, SaveLogService::class.java, JOB_ID, intent)
        }

    }

}