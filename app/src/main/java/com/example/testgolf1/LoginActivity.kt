package com.example.testgolf1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_start_login_m.*
import java.util.*

//https://chjune0205.tistory.com/entry/Firebase-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-Google-Login-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-Android-Kotlin
//https://console.firebase.google.com/?pli=1

//이를 복사해서 Firebase 콘솔에 들어갑니다.
//이후 Firebase 콘솔에서 프로젝트 개요 글자 오른쪽의 톱니바퀴 클릭 -> 프로젝트 설정으로 들어갑니다.
//아래로 스크롤 하면 디지털 지문 추가라는 부분이 보입니다. 클릭합니다.
//여기에 복사했던 인증서 지문을 붙여넣습니다.
//6. 인증 방식 설정

//Firebase콘솔에서 Authentication - Sign-in-method로 들어갑니다.
//사용 설정 스위치를 On으로 바꿔주고, 저장을 하면 됩니다.

//MD5: 7C:48:8F:D4:E5:F5:49:42:93:16:18:2E:9B:45:44:63
//SHA1: 03:AB:57:91:5B:4D:3B:E1:7A:04:AF:47:F1:59:C0:DF:AC:FA:A7:FF
//SHA-256: 8C:C4:B6:C3:02:07:D4:C0:51:0B:D3:FC:08:12:6A:56:7C:2B:AD:62:8D:74:B3:F8:6B:4A:32:94:F7:49:92:1A

class LoginActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    val GOOGLE_REQUEST_CODE = 99
    val TAG = "googleLogin"
    private lateinit var googleSignInClient: GoogleSignInClient
    var cnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_login_m)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        loginActivity_button_googlelogin.setOnClickListener {
            signIn()
        }

        //회원가입  화면 전환
        loginActivity_button_signup2.setOnClickListener {
            Toast.makeText(applicationContext, "회원가입 창으로 전환합니다.", Toast.LENGTH_SHORT).show()
            val intentRegi = Intent(this, RegistActivity::class.java)
            startActivity(intentRegi)

        }

        //로그인 내용 확인
        loginActivity_button_login2.setOnClickListener {
            cnt++
            Toast.makeText(applicationContext, "기능 test" + cnt.toString(), Toast.LENGTH_SHORT)
                .show()
            //성공 2021.11.22 20:25

            //editText로부터 입력된 값을 받아온다
            var id = edittext_id2.text.toString()
            var pw = edittext_password2.text.toString()

            // 쉐어드로부터 저장된 id, pw 가져오기
            val sharedPreference = getSharedPreferences("file name", MODE_PRIVATE)
            val savedId = sharedPreference.getString("id", "")
            val savedPw = sharedPreference.getString("pw", "")

            // 유저가 입력한 id, pw값과 쉐어드로 불러온 id, pw값 비교
            if (id == savedId && pw == savedPw) {
                // 로그인 성공 다이얼로그 보여주기
                //dialog("success")
            } else {
                // 로그인 실패 다이얼로그 보여주기
                //dialog("fail")
            }
        }

    }



    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "로그인 성공")
                    val user = auth!!.currentUser
                    loginSuccess()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
    private fun loginSuccess(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }



}

data class User_infos(
                       var str_project : String? = null,
                       var str_user_id : String? = null,
                       var str_user_pw : String? = null,
                       var int_user_findQ : Int? = null,
                       var str_user_findA: String? = null,
                       var str_country : String? = null,
                       var str_user_tel : String? = null,
                       var str_user_email : String? = null,
                       var str_nick_name : String? = null,
                       var int_ref_compony1  : Int? = null,
                       var str_ref_ID1 : String? = null,
                       var str_ref_pw1 : String? = null,
                       var int_ref_compony2  : Int? = null,
                       var str_ref_ID2 : String? = null,
                       var str_ref_pw2 : String? = null,
                       var date_create_date : Date? = null,
                       var str_del_flag : String? = null)
{

}

