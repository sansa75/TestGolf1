package com.example.testgolf1

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity



class introActivity : AppCompatActivity() {

    //환경설정 변수 사전 INDEXING 실시
    val check_settup = false



    //완료 후 화면 전환


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_approch_m)
        Handler().postDelayed({},3000)
    }


}