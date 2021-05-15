package com.akshay.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import com.akshay.myapplication.databinding.ActivityTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

const val DB_NAME="ToDo.db"
class TaskActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var myCalendar: Calendar
    lateinit var binding:ActivityTaskBinding

    var finalDate=0L
    var finalTime=0L

    lateinit var datesetListener:DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener

    private val labels= arrayListOf("Personal","Business","Insurance","Shopping","Banking","Education")

    val db by lazy {
        AppDataBase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.DateEdt.setOnClickListener(this)
        binding.timeEdt.setOnClickListener(this)
        binding.saveBtn.setOnClickListener(this)

        setUpSpinner()

    }

    private fun setUpSpinner() {
        val adapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,labels)

        labels.sort()

        binding.spinnerCategory.adapter=adapter
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.DateEdt->{
                setListener()
            }
            R.id.timeEdt->{
                setTimeListener()
            }
            R.id.saveBtn -> {
                saveTodo()
            }
        }
    }

    private fun saveTodo() {

        val category = binding.spinnerCategory.selectedItem.toString()
        val title = binding.titleInplay.editText?.text.toString()
        val description = binding.titleInplay.editText?.text.toString()

        GlobalScope.launch(Dispatchers.Main) {

            val id = withContext(Dispatchers.IO) {
                return@withContext db.todoDao().insert(
                    TodoModel(
                        title,
                        description,
                        category,
                        finalDate,
                        finalTime
                    )
                )
            }
            finish()
        }

    }

    private fun setTimeListener() {
        myCalendar= Calendar.getInstance()

        timeSetListener=TimePickerDialog.OnTimeSetListener(){ _: TimePicker, hourOfDay:Int, min:Int  ->
            myCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
            myCalendar.set(Calendar.MINUTE,min)
            updateTime()
        }

        val TimePickerDialog=TimePickerDialog(
            this,timeSetListener,myCalendar.get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE),false
        )
        TimePickerDialog.show()

    }

    @SuppressLint("NewApi")
    private fun updateTime() {
        val myformat="h:mm a"
        val sdf=SimpleDateFormat(myformat)
        binding.timeEdt.setText(sdf.format(myCalendar.time))

    }

    private fun setListener() {
       myCalendar= Calendar.getInstance()

        datesetListener=DatePickerDialog.OnDateSetListener{ _: DatePicker, year:Int, month:Int, dayOfMonth:Int ->
            myCalendar.set(Calendar.YEAR,year)
            myCalendar.set(Calendar.MONTH,month)
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateDate()
        }

        val datePickerDialog=DatePickerDialog(
            this,datesetListener,myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate=System.currentTimeMillis()
        datePickerDialog.show()
    }


    @SuppressLint("NewApi")
    private fun updateDate() {
        //Mon, 5 Jan 2020
        val myformat="EEE, d MMM yyyy"
        val sdf=SimpleDateFormat(myformat)
        binding.DateEdt.setText(sdf.format(myCalendar.time))

        binding.timeInptly.visibility=View.VISIBLE
    }
}