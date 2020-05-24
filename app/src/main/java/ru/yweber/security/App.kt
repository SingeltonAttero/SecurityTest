package ru.yweber.security

import android.app.Application
import ru.yweber.lib_security.SecurityInitialize

/**
 * Created on 21.05.2020
 * @author YWeber */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SecurityInitialize(this)
    }
}