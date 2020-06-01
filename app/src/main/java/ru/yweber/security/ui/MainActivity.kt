package ru.yweber.security.ui

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.yweber.lib_security.log.listener.TouchEventInterceptor
import ru.yweber.security.R

class MainActivity : AppCompatActivity() {

    private val log by lazy { TouchEventInterceptor(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Examples touch interceptor use our the project
        log.interceptTouchListener(btnNext) // specific log on view

        log.interceptTouchListener(emptyContainer, touch = { view, event ->
            //logic touch
            return@interceptTouchListener true
        })

        log.interceptTouchListener(tvFrameTest, nameEvent = "tvFrameTest", touch = { _, _ ->
            // send event touch to children and
            return@interceptTouchListener false
        })


        btnNext.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        log.commonTouch(ev)// commons log on full windows
        return super.dispatchTouchEvent(ev)
    }

}
