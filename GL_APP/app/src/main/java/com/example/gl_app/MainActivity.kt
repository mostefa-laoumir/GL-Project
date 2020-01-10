package com.example.gl_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.speaker_ticket.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            val intent = Intent(this,Main2Activity::class.java)
            startActivity(intent)
        }
    }
    inner class AnimalAdapter: BaseAdapter{
        var list = ArrayList<UserModel>()
        var context:Context?= null
        constructor(context: Context, list :ArrayList<UserModel>):super(){
            this.list=list
            this.context = context
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val user = list[position]
            var inflater =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            if (user.reg == "oui") {
                var myView = inflater.inflate(R.layout.speaker_ticket, null)
                myView.tvNamee.text = user.name!!
                myView.tvDescription.text = user.niveau!!
                return myView

            }else{
                return null
            }
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
