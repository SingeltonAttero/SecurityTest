package ru.yweber.lib_security.log

import android.app.Application
import android.view.MotionEvent
import android.view.View
import ru.yweber.lib_security.service.LogMessage
import ru.yweber.lib_security.service.SaveLogService
import ru.yweber.lib_security.utils.br
import ru.yweber.lib_security.utils.hooks
import ru.yweber.lib_security.utils.sp
import java.util.*

/**
 * Created on 24.05.2020
 * @author YWeber */

class TouchEventInterceptor(private val application: Application) : View.OnTouchListener {
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val builder = StringBuilder()
        repeat(2) {
            builder.br()
        }
        builder.sp("Event :").hooks {
            it.sp("date : ${Date()}")
                .sp("type : ${v.javaClass.simpleName}")
                .sp("coordinate :{ x : ${event.x} y : ${event.y} }")
                .sp("id : ${v.id}")
        }
        SaveLogService.enqueueEvent(application, LogMessage(builder.toString(), LogMessage.Type.TOUCH))
        return false
    }
}