package com.example.gl_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_signup.*

class signup : AppCompatActivity() {
    lateinit var usersDBHelper : UsersDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        usersDBHelper = UsersDBHelper(this)

    }

    fun addUser(v: View){
        var email = this.emailEdit.text.toString()
        var name = this.nameEdit.text.toString()
        var password = this.passwordEdit.text.toString()
    //    var niveau = this..text.toString()
        var result = usersDBHelper.insertUser(UserModel(name = name,email = email,password = password, niveau = "License 1"))
        //clear all edittext s
        this.emailEdit.setText("")
        this.nameEdit.setText("")
        this.passwordEdit.setText("")
        this.userTextView.text = "Added user : "+result
        //this.ll_entries.removeAllViews()
    }

}
