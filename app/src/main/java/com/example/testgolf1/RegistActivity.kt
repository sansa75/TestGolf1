package com.example.testgolf1

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent

import android.os.Bundle

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start_login_m.*
import kotlinx.android.synthetic.main.activity_start_login_s_regist.*
import kotlinx.android.synthetic.main.activity_start_login_s_regist.edittext_id2
import kotlinx.android.synthetic.main.activity_start_login_s_regist.edittext_password2


public class RegistActivity : AppCompatActivity() {
            val TAG: String = "Register"
            var isExistBlank = false
            var isPWSame = false

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_start_login_s_regist)

                registctivity_button_signup2.setOnClickListener {
                    Log.d(TAG, "회원가입 버튼 클릭")

                    var id = edittext_id2.text.toString()
                    var pw = edittext_password2.text.toString()
                    val pw_re = edittext_password3.text.toString()

                    // 유저가 항목을 다 채우지 않았을 경우
                    if(id.isEmpty() || pw.isEmpty() || pw_re.isEmpty()){
                        isExistBlank = true
                    }
                    else{
                        if(pw == pw_re){
                            isPWSame = true
                        }
                    }

                    if(!isExistBlank && isPWSame){

                        // 회원가입 성공 토스트 메세지 띄우기
                        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()

                        // 유저가 입력한 id, pw를 쉐어드에 저장한다.
                        val sharedPreference = getSharedPreferences("file name", Context.MODE_PRIVATE)
                        val editor = sharedPreference.edit()
                        editor.putString("id", id)
                        editor.putString("pw", pw)
                        editor.apply()

                        // 로그인 화면으로 이동
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    }
                    else{

                        // 상태에 따라 다른 다이얼로그 띄워주기
                        if(isExistBlank){   // 작성 안한 항목이 있을 경우
                            dialog("blank")
                        }
                        else if(!isPWSame){ // 입력한 비밀번호가 다를 경우
                            dialog("not same")
                        }
                    }

                }
            }

            // 회원가입 실패시 다이얼로그를 띄워주는 메소드
            fun dialog(type: String){
                val dialog = AlertDialog.Builder(this)

                // 작성 안한 항목이 있을 경우
                if(type.equals("blank")){
                    dialog.setTitle("회원가입 실패")
                    dialog.setMessage("입력란을 모두 작성해주세요")
                }
                // 입력한 비밀번호가 다를 경우
                else if(type.equals("not same")){
                    dialog.setTitle("회원가입 실패")
                    dialog.setMessage("비밀번호가 다릅니다")
                }

                val dialog_listener = object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        when(which){
                            DialogInterface.BUTTON_POSITIVE ->
                                Log.d(TAG, "다이얼로그")
                        }
                    }
                }

                dialog.setPositiveButton("확인",dialog_listener)
                dialog.show()
            }





}