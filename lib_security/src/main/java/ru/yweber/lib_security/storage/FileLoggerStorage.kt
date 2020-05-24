package ru.yweber.lib_security.storage

import android.content.Context
import ru.yweber.lib_security.service.LogMessage
import java.io.File

/**
 * Created on 23.05.2020
 * @author YWeber */

private const val LOGS_DIR = "logs"
private const val LOG_FILE = "log.txt"

class FileLoggerStorage(private val context: Context) : LoggerStorage {


    private val filePath
        get() = context.getExternalFilesDir(LOGS_DIR)?.toString() ?: LOGS_DIR

    /**
     * save log in file, path ~/android/data/"app_name"/logs/log
     * text encoding UTF-8
     **/
    override fun saveLog(log: LogMessage) {
        val locationDir = File(filePath)
        if (locationDir.isDirectory) {
            locationDir.mkdir()
        }
        val file = File(locationDir.absolutePath + File.separator + LOG_FILE)
        if (!file.isFile) {
            file.writeText(log.message)
        } else {
            file.appendText(log.message)
        }
    }
}