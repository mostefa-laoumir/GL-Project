package com.example.gl_app

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.admin_user_ticket.view.*
import kotlinx.android.synthetic.main.speaker_ticket.view.*
import kotlinx.android.synthetic.main.speaker_ticket.view.ivSpeaker
import kotlinx.android.synthetic.main.speaker_ticket.view.tvDescription
import kotlinx.android.synthetic.main.speaker_ticket.view.tvNamee

class admin : AppCompatActivity() {
    var list = ArrayList<UserModel>()
    lateinit var usersDBHelper : UsersDBHelper
    var adapter: Adapterr?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        usersDBHelper = UsersDBHelper(this)
        list =usersDBHelper.readAllUsers()
        adapter = Adapterr(this,list)
        adapter = Adapterr(this,list)
        tvList.adapter = adapter
        button.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
    inner class Adapterr: BaseAdapter {
        var list = ArrayList<UserModel>()
        var context: Context?= null
        constructor(context: Context, list :ArrayList<UserModel>):super(){
            this.list=list
            this.context = context
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val user = list[position]
            var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            if (user.reg == "oui") {
                var myView = inflater.inflate(R.layout.admin_user_ticket, null)
                myView.tvNamee.text = user.name!!
                myView.ivSpeaker.setImageResource(R.drawable.speaker)
                myView.tvDescription.text = user.niveau!!
                myView.accept.setOnClickListener{
                //myView.setBackgroundColor(Color.GREEN)
                myView.layout.setBackgroundColor(Color.BLACK)
                    myView.tvNamee.text="holla madafaka"
                }

                myView.refuse.setOnClickListener {
                    myView.setBackgroundColor(Color.RED)

                }
                return myView

            }else{
                var myView = inflater.inflate(R.layout.admin_user_ticket, null)
                myView.tvNamee.text = user.name!!+"(not registered)"
                myView.ivSpeaker.setImageResource(R.drawable.speaker)
                myView.tvDescription.text = user.niveau!!
                return myView            }
        }


        override fun getItem(position: Int): Any {
            return list[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }

    }

}
