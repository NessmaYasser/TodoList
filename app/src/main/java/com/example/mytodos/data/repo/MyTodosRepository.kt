package com.example.mytodos.data.repo

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mytodos.data.local.database.TodosDao
import com.example.mytodos.data.local.database.TodosDatabase
import com.example.mytodos.model.MyTodoItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class MyTodosRepository(private val database: TodosDao) {

    fun saveTodoItem(item : MyTodoItem) = database.saveTodoItem(item)
    fun getTodoList() = database.getTodoList()
    fun deleteTodoItem(item : MyTodoItem) = database.deleteTodoItem(item)
    fun updateTodoItem(item : MyTodoItem) = database.updateTodoItem(item)
}