package ru.yweber.lib_security.storage

import ru.yweber.lib_security.service.LogMessage

/**
 * Created on 23.05.2020
 * @author YWeber */

interface LoggerStorage {
    fun saveLog(log: LogMessage)
}