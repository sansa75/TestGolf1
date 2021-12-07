package com.example.testgolf1

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tab_practice_s_rhythm.*
import java.io.File


class RythmActivity : AppCompatActivity() {
    val TAG : String = "MainActivity"
    val audioPlay = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //원하는 디렉토리에 있는 파일 목록을 가져온다. 이목록으로 여러가지를 할 수 있을 것이다.
        val arrayList = ArrayList<String>()
        File("${Environment.getExternalStorageDirectory()}/NaverClova/").walkBottomUp().forEach {
            arrayList.add(it.toString())
            //println(it)
        }
        //val max = textView.length() //왜 갯수가 이상하게 나오지?
        //Log.d(TAG, max.toString())
        textViewList.setText(arrayList[0])

        //재생할 파일을 준비한다. 목록의 첫번째 파일을 재생해 보기로 하자.
        //val Path_to_file = "${Environment.getExternalStorageDirectory()}/NAVER Cloud/노래-320k.mp3"
        val Path_to_file = arrayList[0]
        audioPlay.setDataSource(Path_to_file)
        audioPlay.prepare()

        //버튼을 클릭하면 mp3를 재생하도록 하자.
        buttonPlay.setOnClickListener {
            if(audioPlay.isPlaying() == false) {
                audioPlay.start();
            }
        }

        //필요하다면 Stop버튼등을 추가 할 수 있겠다.
        buttonPause.setOnClickListener {
            if(audioPlay.isPlaying()){
                audioPlay.pause()
            }
        }

        buttonStop.setOnClickListener {
            if(audioPlay.isPlaying()){
                audioPlay.stop()//stop하면 무슨일이 생기나???
                //새로 준비해 줌
                audioPlay.reset();
                audioPlay.setDataSource(Path_to_file);
                audioPlay.prepare();
            }
        }
    }

    override fun onDestroy () {
        super.onDestroy ()
        audioPlay.release ()
    }



}