package com.example.notes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ActivityMainBinding
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    /*
    * Инициализация необходимых переменных
    * */
    private lateinit var mainActivitiClass: ActivityMainBinding //подключение байндинга для работы с xml элементами
    private var selectedDate: Long = Formater().getCurrentDateLong()
    private lateinit var adptr: NoteAdapter
    private var selectedNotes = mutableListOf<Note>() //объявление пустого списка для сортировки элементов

    private val notes = mutableListOf(
        Note(0, 1699999999002, 1699999999002, "Только рожая", "женщина может понять боль, которую испытывает JS программист на Котлине"),
        Note(1, 1662580800000, 1662698000000, "У России два союзника", "Статическая типизация и ООП"),
        Note(2, 1662698000000, 1762580800000, "Не лееееезь", "Она тебя сожрёт"),
        Note(3, 1662698000000, 1762580800000, "Папей динамической типизации", "Сам попей"),
        Note(4, 1662808080000, 1762580800000, "Папей реакт", "Сам попей"),
        Note(5, 1662678000000, 1762580800000, "Я пёёёс, я пёс, я пёс", "Собак")
    ) //Создание и заполнение списка заметок

    /*
    * Функция, выполняющая фильтрацию заметок в соотвтетствии с выбранной в календаре
    * */
    fun filterNotes(): MutableList<Note>{
        selectedNotes.clear()
        var currentDateString = Formater(selectedDate).getDateFromString()
        for(item in notes){
            var itemDate = Formater(item.date_start).getDateFromString()
            Log.i("timelog", item.name)
            if(itemDate==currentDateString){
                selectedNotes.add(item)
            }
            Log.e("timelog", "Сортировка")
            Log.e("timelog", "${selectedNotes.toString()} $currentDateString, $itemDate")
        }
        selectedNotes.sortBy { it.date_start} //сортировка по дате добавления заметки
        return selectedNotes
    }

    /*
    * Запуск активити с отображение конкретной заметки и передача в него параметров
    * */
    fun onItemClick() = { Note: Note ->
        val intent = Intent(this, DetailView::class.java)
        intent.putExtra("id", Note.id)
        intent.putExtra("name", Note.name)
        intent.putExtra("description", Note.description)
        intent.putExtra("from", Note.date_start)
        intent.putExtra("to", Note.date_finish)
        startActivity(intent) //Запуск активити с заданными переменными
    }

    fun openNewNote() {
        val intent = Intent(this, NewNote::class.java)
        startActivityForResult(intent, 123)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivitiClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivitiClass.root)
        var recyclerView: RecyclerView = mainActivitiClass.notesContainer
        recyclerView.layoutManager = LinearLayoutManager(this) //Задание layout для RecyclerView
        this.adptr = NoteAdapter(onItemClick()) //Создание экземпляра адаптера заметки
        recyclerView.adapter = adptr
        this.adptr.setData(filterNotes()) //Обновление данных в recycler в соответствии с текущей датой
        //Задание функции, которая будет слушать изменение даты в календаре и запускать фильтр в соответствии с выбранной датой
        mainActivitiClass.calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var date = "$year-${"%02d".format(month+1)}-${"%02d".format(dayOfMonth)}"
            val format = SimpleDateFormat("yyyy-MM-dd")
            selectedDate = format.parse(date).time
            adptr.setData(filterNotes()) //Обновление заметок в RecyclerView
        }
        mainActivitiClass.newNote.setOnClickListener {
            openNewNote()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==123 && resultCode== RESULT_OK){
            if (data!=null){
                var title = data.getStringExtra("name").toString()
                var description = data.getStringExtra("description").toString()
                var date_start = data.getLongExtra("date_start", 0)
                var date_finish = data.getLongExtra("date_finish", 0)
                var id = this.notes.size + 1
                this.notes.add(Note(id, date_start, date_finish, title, description))
                this.adptr.setData(filterNotes())
            }
        }
    }

}