package ru.yweber.lib_security.log.listener

import android.content.Context
import android.util.Log
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

class TouchEventInterceptor(private val context: Context) {

    fun interceptClickListener(
        view: View,
        nameEvent: String = "click",
        click: (view: View) -> Unit = {}
    ) {
        view.setOnClickListener {
            val builder = StringBuilder()
            repeat(2) {
                builder.br()
            }
            builder.sp("$nameEvent :").hooks {
                it.sp("date : ${Date()}")
                    .sp("type : click")
                    .sp("coordinate :{ x : ${view.x} y : ${view.y} }")
            }
            click(view)
        }
    }

    fun interceptTouchListener(
        view: View,
        nameEvent: String = "touch",
        touch: (view: View, event: MotionEvent) -> Boolean = { _, _ -> false }
    ) {
        view.setOnTouchListener { v, event ->
            return@setOnTouchListener touch(v, event).apply {
                val builder = StringBuilder()
                repeat(2) {
                    builder.br()
                }
                builder.sp("$nameEvent :").hooks {
                    it.sp("date : ${Date()}")
                        .sp("parentClick : $this")
                        .sp("typeView : ${v.javaClass.simpleName}")
                        .sp("coordinate :{ x : ${event.x} y : ${event.y} }")
                        .sp("id : ${v.id}")
                }
                Log.e("TEST", builder.toString()) // TODO DELETE IN PRODUCTION
                SaveLogService.enqueueEvent(context, LogMessage(builder.toString(), LogMessage.Type.TOUCH))
            }
        }
    }

    fun commonTouch(event: MotionEvent) {
        val builder = StringBuilder()
        repeat(2) {
            builder.br()
        }
        builder.sp("CommonEvent :").hooks {
            it.sp("date : ${Date()}")
                .sp("type: common")
                .sp("action :${event.action}")
                .sp("coordinate :{ x : ${event.x} y : ${event.y} }")
        }
        Log.e("TEST", builder.toString()) // TODO DELETE IN PRODUCTION
        SaveLogService.enqueueEvent(context, LogMessage(builder.toString(), LogMessage.Type.TOUCH))
    }
}