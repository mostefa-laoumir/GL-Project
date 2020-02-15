package com.example.gl_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signedin.*
import java.io.File

class signedin : AppCompatActivity() {
        var name:String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signedin)
        var bundle: Bundle? =intent.extras


            /*signBtn.setOnClickListener {

            }*/
    }
    fun convertToBase64(attachment: File): String {
        return Base64.encodeToString(attachment.readBytes(), Base64.NO_WRAP)
    }
}
