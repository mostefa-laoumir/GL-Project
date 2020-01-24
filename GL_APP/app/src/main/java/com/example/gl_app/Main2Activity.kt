package com.example.gl_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_signup.*

class Main2Activity : AppCompatActivity() {
    lateinit var usersDBHelper : UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        usersDBHelper = UsersDBHelper(this)
        signupTv.setOnClickListener{
            val intent = Intent(this,signup::class.java)
            startActivity(intent)
        }

    }
    fun signIn(view:View){
        val email:String = emailTextEdit.text.toString()
        val password:String = PasswordTextEdit.text.toString()
        val user = usersDBHelper.readUser(email)
        if(user.elementAt(0).email == email && user.elementAt(0).password == password){
            Toast.makeText(this, user.elementAt(0).name+" : is connected!", Toast.LENGTH_LONG).show()
            var intent = Intent(this,signedin::class.java)
            intent.putExtra("name", user.elementAt(0).name)
            startActivity(intent)
        }else if(user.elementAt(0).email == "admin" && user.elementAt(0).password == "admin"){
                Toast.makeText(this, user.elementAt(0).name+" : is connected!", Toast.LENGTH_LONG).show()
                var intent = Intent(this,admin::class.java)
                intent.putExtra("name", user.elementAt(0).name)
                startActivity(intent)
        }else{
            Toast.makeText(this, "Not a user", Toast.LENGTH_LONG).show()
        }

    }
}
