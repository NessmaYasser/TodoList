package com.example.mytodos.ui.viewModel

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.mytodos.brodcastReceiver.AlarmReceiver
import com.example.mytodos.data.repo.MyTodosRepository
import com.example.mytodos.model.MyTodoItem
import com.example.mytodos.ui.base.BaseViewModel
import com.example.mytodos.utils.cancelNotifications
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*


class TodoViewModel(private val repository: MyTodosRepository
) : BaseViewModel() {

    private val _todoListLiveData = MutableLiveData<TodoViewState>()
    val todoListLiveData : MutableLiveData<TodoViewState>
        get() = _todoListLiveData

    var todoItem = MyTodoItem()

    init {
        todoListLiveData.value = TodoViewState()
    }
// save task in database
    fun addNewItem(){
        add{
            repository.saveTodoItem(todoItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _todoListLiveData.value = _todoListLiveData.value?.copy(itemAdded = true)
                })
        }
    }
// get all todoList from database
    fun getAllTodo(){
        add{
            repository.getTodoList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _todoListLiveData.value = _todoListLiveData.value?.copy(todoList = it)
                })
        }
    }
// delete task from database
    fun deleteTodoItem(){
        add {
            repository.deleteTodoItem(todoItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _todoListLiveData.value = _todoListLiveData.value?.copy(itemDeleted = true)
                })
        }
    }
// update saved item i database
    fun updateTodoItem(){
        add {
            repository.updateTodoItem(todoItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _todoListLiveData.value = _todoListLiveData.value?.copy(itemUpdated = true)
                })
        }
    }
// update database with checked or not in database
    fun onCheckBoxCheckedListener(checked : Boolean){
        todoItem.checked = checked
        updateTodoItem()
    }

    fun sendAlarm(context: Context, hour: Int, mins: Int) {
        val REQUEST_CODE = 0
        // create alarmManager instance
        val alarmManager : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // create AlarmReceiver intent
        val notifyIntent = Intent(context, AlarmReceiver::class.java)
        // create broadcast pendingIntent
        var notifyPendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // create notificationManager instace for notification
        val notificationManager =
            ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager

        notificationManager.cancelNotifications()

        // set task finish time to calender
        // get time with - 10 minutes
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(System.currentTimeMillis())
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, mins)
        calendar.add(Calendar.MINUTE, -10)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, notifyPendingIntent)
        else
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, notifyPendingIntent)
    }


}