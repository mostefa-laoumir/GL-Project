package com.example.gl_app

import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signedin.*
import java.io.File


class signedin : AppCompatActivity() {
    var list = ArrayList<UserModel>()
    lateinit var usersDBHelper : UsersDBHelper
    var adapter: MainActivity.Adapterr?=null
        var name:String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signedin)
        usersDBHelper = UsersDBHelper(this)
        list =usersDBHelper.readAllUsers()
        var bundle: Bundle? =intent.extras
        var email = bundle!!.getString("email")


          signBtn.setOnClickListener {
              var s:String = ""
              when {
                  r1.isChecked -> {
                      s = r1.text.toString()
                      Toast.makeText(this, ""+r1.text.toString(), Toast.LENGTH_SHORT).show()

                  }
                  r2.isChecked -> {
                      s = r2.text.toString()
                      Toast.makeText(this, ""+r2.text.toString(), Toast.LENGTH_SHORT).show()

                  }
                  r3.isChecked -> {
                      s = r3.text.toString()
                      Toast.makeText(this, ""+r3.text.toString(), Toast.LENGTH_SHORT).show()

                  }
                  r4.isChecked -> {
                      s = r4.text.toString()
                      Toast.makeText(this, ""+r4.text.toString(), Toast.LENGTH_SHORT).show()

                  }

              }
              usersDBHelper.updateUserNiv(email.toString(),s)
              usersDBHelper.updateUserreg(email.toString())
              Toast.makeText(this, "OK!"+r1.text.toString(), Toast.LENGTH_LONG).show()

          }

        importBtn.setOnClickListener {

            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)

        }
    }
    fun convertToBase64(attachment: File): String {
        return Base64.encodeToString(attachment.readBytes(), Base64.NO_WRAP)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data //The uri with the location of the file
            fileNameID.text=getFileName(selectedFile!!)
           // val file = File(getPath(selectedFile))
           // val b64 = convertToBase64(file)
           // val decodedByte: ByteArray = Base64.decode(b64, 0)
            //val bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)



        }
    }
    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.getScheme().equals("content")) {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        }
        if (result == null) {
            result = uri.getPath()
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    fun getPath(uri: Uri?): String? {
        val projection =
            arrayOf(MediaStore.Images.Media.DATA)
        val cursor =
            contentResolver.query(uri!!, projection, null, null, null)
                ?: return null
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s = cursor.getString(column_index)
        cursor.close()
        return s
    }
}
