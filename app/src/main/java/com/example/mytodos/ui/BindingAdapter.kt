package com.example.mytodos.ui

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.example.mytodos.R
import com.example.mytodos.model.MyTodoItem
import com.example.mytodos.ui.viewModel.TodoViewModel

//@BindingAdapter("isChecked")
//fun isTaskChecked(view : View, viewModel : TodoViewModel) : Boolean{
//    if(viewModel.todoItem.checked){
//        viewModel.updateTodoItem()
//        return false
//
//    }else{
//        viewModel.updateTodoItem()
//        return true
//
//    }
//}