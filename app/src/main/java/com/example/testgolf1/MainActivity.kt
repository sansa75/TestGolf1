package com.example.testgolf1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

//2021.12.08 강민석* 으으 하기 싫어. 의지가 바닥이 난 상황
//준병이는 바빠서 아예 안쳐다 보고 있고
// 나라도 마무리 해서 끝내야지. 의지가 약하니 개발자되기는 힘들구나

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //STT 변수
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognitionListener: RecognitionListener

    //TTS 변수
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        requestPermission() //폰 마이크 권한 허용

        //TTS 호출
        tts = TextToSpeech(this,this)
        btn_ent.setOnClickListener{startTTS()}

        //STT 변수 설정
        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR")

        setListener() //STT 호출

        //버튼 누르면 STT 시작
        btnStart.setOnClickListener {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
            speechRecognizer.setRecognitionListener(recognitionListener)
            speechRecognizer.startListening(intent)
        }

        //강민석 . 로그인 버튼 클릭 리스너 -> 로그인 화면 전환
        myInfoButton.setOnClickListener {
            //초기 카운트 변수 선언
            var int_count = 1
            var int_last = 5
            //2021.11.24 로그인 화면 지연을 어떻게 할까>???
            //intro 화면
            Toast.makeText(applicationContext,"intro 시작합니다.",Toast.LENGTH_SHORT).show()
            val intentIntro = Intent(this, introActivity::class.java)
            startActivity(  intentIntro)


            //로그인 화면 (intro 5초뒤 전환)
            //setContentView(R.layout.activity_start_login_m)
            //2021.11.22 intent로 화면전환
            Toast.makeText(applicationContext,"login 시작합니다.",Toast.LENGTH_SHORT).show()
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(  intentLogin)
        }
    }

    //STT
    private fun requestPermission() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.RECORD_AUDIO),0)
        }
    }

    private fun setListener() {
        recognitionListener = object: RecognitionListener {

            override fun onReadyForSpeech(params: Bundle?) {
                Toast.makeText(applicationContext,"음성인식을 시작합니다.",Toast.LENGTH_SHORT).show()

            }

            override fun onBeginningOfSpeech() {

            }

            override fun onRmsChanged(rmsdB: Float) {

            }

            override fun onBufferReceived(buffer: ByteArray?) {

            }

            override fun onEndOfSpeech() {

            }

            override fun onError(error: Int) {
                var message: String

                when(error){
                    SpeechRecognizer.ERROR_AUDIO ->
                        message = "오디오 에러"
                    SpeechRecognizer.ERROR_CLIENT ->
                        message = "클라이언트 에러"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS ->
                        message = "퍼미션 없음"
                    SpeechRecognizer.ERROR_NETWORK ->
                        message = "네트워크 에러"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT ->
                        message = "네트워크 타임아웃"
                    SpeechRecognizer.ERROR_NO_MATCH ->
                        message = "찾을 수 없음"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY ->
                        message = "RECOGNIZER가 바쁨"
                    SpeechRecognizer.ERROR_SERVER ->
                        message = "서버가 이상함"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT ->
                        message = "말하는 시간초과"
                    else -> message = "알 수 없는 오류"
                }
                Toast.makeText(application,"에러 발생 $message",Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                var matches: ArrayList<String> = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) as ArrayList<String>

                for (i in 0 until matches.size) {
                    tvResult.text = matches[i]
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {

            }

            override fun onEvent(eventType: Int, params: Bundle?) {

            }
        }

    }

    private fun initTextToSpeech() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            Toast.makeText(this,"SDK VERSION IS LOW ",Toast.LENGTH_SHORT).show()
            return
        }
    }

    //TTS
    private fun startTTS() {

        tts!!.speak(edt_speech.text.toString(), TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.KOREA)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){

            }else{

            }
        }
    }

    override fun onDestroy() {

        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }


}