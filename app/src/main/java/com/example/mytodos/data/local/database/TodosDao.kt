package com.example.mytodos.data.local.database

import androidx.room.*
import com.example.mytodos.model.MyTodoItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
@Dao
interface TodosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTodoItem(item : MyTodoItem) : Completable
    @Query("SELECT * FROM MyTodoItem")
    fun getTodoList() : Observable<List<MyTodoItem>>
    @Delete
    fun deleteTodoItem(item : MyTodoItem) : Completable
    @Update
    fun updateTodoItem(item : MyTodoItem): Completable
}