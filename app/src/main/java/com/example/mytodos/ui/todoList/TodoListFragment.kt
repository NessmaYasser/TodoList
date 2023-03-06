package com.example.mytodos.ui.todoList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodos.R
import com.example.mytodos.databinding.FragmentTodoListBinding
import com.example.mytodos.model.MyTodoItem
import com.example.mytodos.ui.OnItemClicked
import com.example.mytodos.ui.TodosAdapter
import com.example.mytodos.ui.viewModel.TodoViewModel
import com.example.mytodos.ui.viewModel.TodoViewState
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 * Use the [TodoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoListFragment : Fragment() {

    lateinit var binding: FragmentTodoListBinding
    val mainViewModle: TodoViewModel by viewModel()
    lateinit var todosAdapter: TodosAdapter
    var todoList = mutableListOf<MyTodoItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTodoListBinding.inflate(inflater)
        binding.lifecycleOwner = requireActivity()
        //set viewmodel
        binding.viewModel = mainViewModle

        setupView()

        mainViewModle.todoListLiveData.observe(viewLifecycleOwner, Observer {
            handelState(it)
        })
        return binding.root
    }

    fun setupView() {

        //click on floating action button to navigate to addTaskFragment
        binding.addTaskBtn.setOnClickListener {
            findNavController().navigate(TodoListFragmentDirections.actionTodoListFragmentFragmentToAddTodoTaskFragment())
        }
        // create adapter instance
        //navigate to addTaskFragment with clicked item
        todosAdapter = TodosAdapter(onClick = OnItemClicked {
            findNavController().navigate(
                TodoListFragmentDirections.actionTodoListFragmentFragmentToAddTodoTaskFragment(
                    it
                )
            )
        })
        // on check listener on check box
        // update database item if checked or not
        todosAdapter.onChecked = { checked, item ->
            mainViewModle.todoItem = item
            mainViewModle.onCheckBoxCheckedListener(checked)
        }
        // setup todoList recycleriew
        binding.todoListRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todosAdapter

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    // this method is called
                    // when the item is moved.
                    return false
                }

                // swipe item to delete
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    // this method is called when item is swiped.
                    // below line is to remove item from our list.
                    val adapter = this@apply.adapter as TodosAdapter
                    adapter.removeItem(viewHolder.adapterPosition)
                    // below line is to delete item from database
                    mainViewModle.todoItem = adapter.currentList[viewHolder.adapterPosition]
                    mainViewModle.deleteTodoItem()

                }
            }).attachToRecyclerView(this)

        }
    }


    override fun onResume() {
        super.onResume()
        //get todoList from database
        mainViewModle.getAllTodo()
    }

    // handle view state
    fun handelState(state: TodoViewState) {
        if (!state.todoList.isNullOrEmpty()) {
            todoList = (state.todoList)!!.toMutableList()
            binding.emptyView.visibility = View.GONE
            todosAdapter.submitList(state.todoList)
        } else {
            binding.emptyView.visibility = View.VISIBLE
        }
    }

}