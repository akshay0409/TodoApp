package com.akshay.myapplication

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class TodoAdapter (val list : List<TodoModel>):RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        @SuppressLint("NewApi")
        fun bind(toDoModel: TodoModel) {
            with(itemView){
                val colors=resources.getIntArray(R.array.random_color)
                val randomColor=colors[Random().nextInt(colors.size)]
                val viewColorTag=findViewById<View>(R.id.viewColorTag)
                viewColorTag.setBackgroundColor(randomColor)
                val txtShowTitle=findViewById<TextView>(R.id.txtShowTitle)
                val txtShowTask=findViewById<TextView>(R.id.txtShowTask)
                val txtShowCategory=findViewById<TextView>(R.id.txtShowCategory)

                val txtShowDate=findViewById<TextView>(R.id.txtShowDate)

                txtShowTitle.text=toDoModel.title
                txtShowTask.text=toDoModel.description
                txtShowCategory.text=toDoModel.category
                updateTime(toDoModel.time)
                updateDate(toDoModel.date)
            }
        }
        @RequiresApi(Build.VERSION_CODES.N)
        private fun updateTime(time:Long) {
            val txtShowTime=itemView.findViewById<TextView>(R.id.txtShowTime)
            val myformat="h:mm a"
            val sdf= SimpleDateFormat(myformat)
            txtShowTime.text=sdf.format(Date(time))

        }
        @RequiresApi(Build.VERSION_CODES.N)
        private fun updateDate(time: Long) {
            val txtShowDate=itemView.findViewById<TextView>(R.id.txtShowDate)
            val myformat="EEE, d MMM yyyy"
            val sdf=SimpleDateFormat(myformat)
            txtShowDate.text=sdf.format(Date(time))

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_todo,parent,false))

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemCount()= list.size

    override fun getItemId(position: Int): Long {
        return list[position].id
    }


}