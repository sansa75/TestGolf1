package com.example.testgolf1


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.speech.tts.TextToSpeech
import android.widget.Button
import kotlinx.android.synthetic.main.activity_start_login_m.*
import java.util.*
import kotlin.collections.ArrayList

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_login_m)

        var cnt = 0



        //강민석 . 로그인 버튼 클릭 리스너 -> 로그인 화면 전환
        button_login.setOnClickListener {
            cnt++
            Toast.makeText(applicationContext,"수량확인"+cnt.toString(),Toast.LENGTH_SHORT).show()
            //성공 2021.11.22 20:25
        }



    }


}

//com.example.testgolf1.

//button_login
/*button_login.setOnClickListener    {
//editText로부터 입력된 값을 받아온다
var id = edit_id.text.toString()
var pw = edit_pw.text.toString()

// 쉐어드로부터 저장된 id, pw 가져오기
val sharedPreference = getSharedPreferences("file name", Context.MODE_PRIVATE)
val savedId = sharedPreference.getString("id", "")
val savedPw = sharedPreference.getString("pw", "")

// 유저가 입력한 id, pw값과 쉐어드로 불러온 id, pw값 비교
if(id == savedId && pw == savedPw){
    // 로그인 성공 다이얼로그 보여주기
    dialog("success")
}
else{
    // 로그인 실패 다이얼로그 보여주기
    dialog("fail")
}
}
// 로그인 성공/실패 시 다이얼로그를 띄워주는 메소드
fun dialog(type: String){
    var dialog = AlertDialog.Builder(this)

    if(type.equals("success")){
        dialog.setTitle("로그인 성공")
        dialog.setMessage("로그인 성공!")
    }
    else if(type.equals("fail")){
        dialog.setTitle("로그인 실패")
        dialog.setMessage("아이디와 비밀번호를 확인해주세요")
    }

    var dialog_listener = object: DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            when(which){
                DialogInterface.BUTTON_POSITIVE ->
                    Log.d(TAG, "")
            }
        }
    }

    dialog.setPositiveButton("확인",dialog_listener)
    dialog.show()
}
 */