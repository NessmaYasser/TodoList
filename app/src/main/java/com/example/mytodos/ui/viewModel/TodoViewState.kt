package com.example.mytodos.ui.viewModel

import com.example.mytodos.model.MyTodoItem

data class TodoViewState(
    var todoList : List<MyTodoItem>? = null,
    var itemDeleted : Boolean? = null,
    var itemAdded : Boolean? = null,
    var itemUpdated: Boolean? = null
)
