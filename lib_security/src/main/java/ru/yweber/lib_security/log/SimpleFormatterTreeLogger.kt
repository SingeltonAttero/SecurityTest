package ru.yweber.lib_security.log

import android.app.Activity
import android.app.Application
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import ru.yweber.lib_security.service.LogMessage
import ru.yweber.lib_security.service.SaveLogService
import ru.yweber.lib_security.utils.br
import ru.yweber.lib_security.utils.hooks
import ru.yweber.lib_security.utils.sp
import ru.yweber.lib_security.utils.tb
import java.util.*

/**
 * Created on 21.05.2020
 * @author YWeber */

class SimpleFormatterTreeLogger(
    private val application: Application,
    private val eventInterceptor: View.OnTouchListener = TouchEventInterceptor(application)
) : TreeLogger {

    override fun createTreeLogInActivity(activity: Activity) {
        val log = createActivityLog(activity)
        SaveLogService.enqueueEvent(application, LogMessage(log, LogMessage.Type.TREE))
    }

    private fun createActivityLog(activity: Activity): String {
        val rootView = activity.findViewById<ViewGroup>(android.R.id.content)

        val strBuilder = StringBuilder()
        repeat(5) {
            strBuilder.br()
        }
        strBuilder.sp(activity.javaClass.name).sp("time : ${Date()}").br()
        strBuilder.sp("ActivityVisibleTree : ").br()
        strBuilder.hooks {

            createTreeViewLog(rootView, strBuilder, "Root")
        }
        strBuilder.br()
        return strBuilder.toString()
    }

    private fun createTreeViewLog(rootView: ViewGroup, strBuilder: StringBuilder, type: String): String {
        strBuilder.tb()
            .sp("$type : { type : ${rootView.javaClass.simpleName} id : ${rootView.id} }")
            .tb()

        val listChildren: Sequence<View> = rootView.children
        listChildren.forEach {
            it.setOnTouchListener(eventInterceptor)
            strBuilder.br()
            when (it) {
                is ViewGroup -> {
                    if (it.childCount != 0) {
                        createTreeViewLog(it, strBuilder, "RootContainer")
                    } else {
                        strBuilder.tb().sp("EmptyContainer : { ${it.javaClass.simpleName} id = ${it.id}}")
                    }
                }
                is TextView -> {
                    strBuilder.tb().sp("TextView :").hooks { builder ->
                        builder
                            .sp("type : ${it.javaClass.simpleName} id : ${it.id}")
                            .sp("text : ${it.text}")
                    }
                }
                is Button -> {
                    strBuilder
                        .tb()
                        .sp("Button :")
                        .hooks { builder ->
                            builder
                                .sp("type : ${it.javaClass.simpleName} id : ${it.id} ")
                                .sp("text : ${it.text}")
                        }
                }
                is ImageView -> {
                    strBuilder
                        .tb()
                        .sp("ImageView :").hooks { builder ->
                            builder
                                .sp("type : ${it.javaClass.simpleName} id : ${it.id} ")
                                .sp("contentDescription : ${it.contentDescription}")
                        }
                }
                else -> {
                    strBuilder
                        .tb()
                        .sp("View : { type : ${it.javaClass.simpleName} id : ${it.id} }")
                }
            }
        }
        strBuilder.br()
        return strBuilder.toString()
    }

}