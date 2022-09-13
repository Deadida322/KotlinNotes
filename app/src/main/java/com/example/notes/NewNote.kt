package com.example.notes

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.notes.databinding.ActivityNewNoteBinding
import java.util.*

class NewNote : AppCompatActivity() {

    private var title: String = ""
    private var description: String = ""
    private var date: Long = Formater().getCurrentDateLong()
    private var endTime: Long = 0
    lateinit var bindingClass: ActivityNewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        bindingClass.viewDate.text = Formater(date).getDateFromString()
        bindingClass.changeDate.setOnClickListener { onOpenDatePicker() }
        bindingClass.changeEndTime.setOnClickListener { onOpenEndTimePicker() }
        bindingClass.changeTitle.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                onTitleChange()
                return true
            }
        })
        bindingClass.changeTitle.doOnTextChanged { text, start, end, count-> onTitleChange() }
        bindingClass.changeDescription.doOnTextChanged {text, start, end, count-> onDescriptionChange() }
        bindingClass.saveBtn.setOnClickListener {
            if(this.title.isEmpty() || this.description.isEmpty()){
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG).show()
            }
            else onFormSubmit()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun onOpenDatePicker(){
        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
            val dateToShow = "$mYear-${"%02d".format(mMonth+1)}-${"%02d".format(mDay)}"
            Log.e("dates", dateToShow)
            var newDate = "$mYear-${"%02d".format(mMonth+1)}-${"%02d".format(mDay)}"
            val format = SimpleDateFormat("yyyy-MM-dd")
            this.date = format.parse(newDate).time
            bindingClass.viewDate.text  = Formater(this.date).getDateFromString()
        }
        DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    @SuppressLint("SimpleDateFormat")
    fun onOpenEndTimePicker(){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            var form = SimpleDateFormat("HH:mm")
            this.endTime = form.parse(form.format(cal.time)).time
            bindingClass.viewEndTime.text = form.format(cal.time).toString()
        }
        TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE), true).show()
    }


    fun onFormSubmit(){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("name", this.title)
        intent.putExtra("description", this.description)
        intent.putExtra("date_start", this.date+Formater().getCurrentTimeLong())
        intent.putExtra("date_finish", this.date+this.endTime)
        setResult(RESULT_OK, intent)
        finish()
    }

    fun onTitleChange() {
        this.title = bindingClass.changeTitle.text.toString()
    }

    fun onDescriptionChange() {
        this.description = bindingClass.changeDescription.text.toString()
    }


}