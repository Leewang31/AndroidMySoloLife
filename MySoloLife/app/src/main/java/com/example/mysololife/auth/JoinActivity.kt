package com.example.mysololife.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityIntroBinding
import com.example.mysololife.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.joinBtn.setOnClickListener {

            val email = binding.emailArea.text.toString()
            val password1 = binding.passwordArea1.text.toString()
            val password2 = binding.passwordArea2.text.toString()

            // 저기 값이 비어있는 지 확인
            if(email.isEmpty()){
                Toast.makeText(this,"이메일을 입력해주세요.",Toast.LENGTH_LONG).show()
            }
            else if(password1.isEmpty()){
                Toast.makeText(this,"비밀번호를 입력해주세요.",Toast.LENGTH_LONG).show()
            }
            else if(password2.isEmpty()){
                Toast.makeText(this,"비밀번호 확인을 입력해주세요.",Toast.LENGTH_LONG).show()
            }

            //비밀번호 확인인
           if(password1.equals(password2)){

            }else{
                Toast.makeText(this,"비밀번호가 다릅니다.",Toast.LENGTH_LONG).show()
            }
        }

        auth = Firebase.auth


        /*auth.createUserWithEmailAndPassword("abc@abc.abc", "abcdabcd")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"성공",Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this,"실패",Toast.LENGTH_LONG).show()
                }
            }*/
    }

}