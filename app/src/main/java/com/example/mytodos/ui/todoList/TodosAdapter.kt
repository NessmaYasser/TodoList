package com.example.mytodos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodos.databinding.TodoItemViewBinding
import com.example.mytodos.model.MyTodoItem


class TodosAdapter(val onClick: OnItemClicked) :
    ListAdapter<MyTodoItem, TodosAdapter.TodoViewHolder>(DiffUtils()) {

    var onChecked: ((Boolean, MyTodoItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(currentList[position], onClick, onChecked)
    }

    fun removeItem(position: Int){
        val currentList =  currentList.toMutableList()
        currentList.removeAt(position)
//        notifyItemRemoved(position)
        submitList(currentList)

    }

     class TodoViewHolder(var bind: TodoItemViewBinding) : RecyclerView.ViewHolder(bind.root) {
         init {

         }
        fun bind(item: MyTodoItem, onClick: OnItemClicked, onChecked: ((Boolean, MyTodoItem) -> Unit)?) {
            bind.checkView.isChecked = item.checked
            bind.checkView.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked -> // Invoke the callback
                item.checked = isChecked
                onChecked?.invoke(isChecked, item)
            })
            bind.todoItem = item
            bind.itemClick = onClick


            if (!item.time.isNullOrBlank()) {
                bind.todoTaskTimeTV.visibility = View.VISIBLE
            } else {
                bind.todoTaskTimeTV.visibility = View.GONE
            }
            bind.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TodoViewHolder {
                return TodoViewHolder(TodoItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

}

class DiffUtils() : DiffUtil.ItemCallback<MyTodoItem>() {
    override fun areItemsTheSame(oldItem: MyTodoItem, newItem: MyTodoItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MyTodoItem, newItem: MyTodoItem): Boolean {
        return oldItem.id == newItem.id
    }

}

class OnItemClicked(val click: (MyTodoItem) -> Unit) {
    fun onClick(data: MyTodoItem) = click(data)
}

class OnItemChecked(val check: (Boolean) -> Unit) {
    fun onCheck(isCheck: Boolean) = check(isCheck)
}