package com.example.mytodos.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mytodos.data.local.database.TodosDao
import com.example.mytodos.model.MyTodoItem

const val DB_VERSION = 1
const val DB_NAME = "TodoDatabase"

@Database(entities = [MyTodoItem::class], version = DB_VERSION)
abstract class TodosDatabase : RoomDatabase() {
    abstract fun todosDao() : TodosDao

    companion object{
        @Volatile
        private var databaseInstance : TodosDatabase? = null

        fun getDatabaseInstance(context: Context) : TodosDatabase{
            if(databaseInstance == null){
                synchronized(this){
                    databaseInstance = createDatabaseInstance(context)
                }
            }
            return databaseInstance!!
        }

        fun createDatabaseInstance(context: Context) : TodosDatabase{
            return Room.databaseBuilder(context, TodosDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}