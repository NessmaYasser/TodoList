package com.example.mytodos.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class MyTodoItem(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var title : String = "",
    var body : String = "",
    var checked : Boolean = false,
    var time : String = ""
): Parcelable
