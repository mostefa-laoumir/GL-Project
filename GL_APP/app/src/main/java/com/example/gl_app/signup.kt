package com.example.gl_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*

class signup : AppCompatActivity() {
    lateinit var usersDBHelper : UsersDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        usersDBHelper = UsersDBHelper(this)
        this.emailEdit.setOnClickListener{
            this.emailEdit.setText("")
        }
        this.nameEdit.setOnClickListener{
            this.nameEdit.setText("")
        }


    }

    fun addUser(v: View){
        val email = this.emailEdit.text.toString()
        val name = this.nameEdit.text.toString()
        val password = this.passwordEdit.text.toString()
        val result = usersDBHelper.insertUser(UserModel(name = name,email = email,password = password, niveau = " ",reg = "non",accepted = "non"))
        //clear all edittext s
        this.emailEdit.setText("")
        this.nameEdit.setText("")
        this.passwordEdit.setText("")
        Toast.makeText(this,"User added : "+result.toString(),Toast.LENGTH_LONG).show()
        val intent = Intent(this,Main2Activity::class.java)
        startActivity(intent)

        //this.ll_entries.removeAllViews()
    }

}
