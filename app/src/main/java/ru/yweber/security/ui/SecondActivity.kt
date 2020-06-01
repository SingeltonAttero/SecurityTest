package ru.yweber.security.ui

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*
import ru.yweber.lib_security.log.listener.TouchEventInterceptor
import ru.yweber.security.R

/**
 * Created on 21.05.2020
 * @author YWeber */

class SecondActivity : AppCompatActivity(R.layout.activity_second) {

    private val log by lazy { TouchEventInterceptor(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Examples click interceptor use our the project
        log.interceptClickListener(tvTest1)
        log.interceptClickListener(tvTest2, click = {
            // TODO handle click
        })
        log.interceptClickListener(btnTest, "finishClick") {
            this.finish()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        log.commonTouch(ev)
        return super.dispatchTouchEvent(ev)
    }

}