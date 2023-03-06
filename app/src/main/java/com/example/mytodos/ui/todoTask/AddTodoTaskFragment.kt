package com.example.mytodos.ui.todoTask

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mytodos.databinding.FragmentAddTodoTaskBinding
import com.example.mytodos.model.MyTodoItem
import com.example.mytodos.ui.viewModel.TodoViewModel
import com.example.mytodos.ui.viewModel.TodoViewState
import com.example.mytodos.utils.showTimePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [AddTodoTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTodoTaskFragment : Fragment() {

    lateinit var binding: FragmentAddTodoTaskBinding
    val viewModel: TodoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTodoTaskBinding.inflate(inflater)
        binding.lifecycleOwner = this
        //bind viewmodel to view
        binding.viewModel = viewModel

        setupView()
        // observe on the state of todoListLiveData
        viewModel.todoListLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            handleState(it)
        })

        return binding.root
    }

    fun setupView() {
        // set the clicked to do item in todoItem in viewmodel
        viewModel.todoItem =
            AddTodoTaskFragmentArgs.fromBundle(requireArguments()).myTask ?: MyTodoItem()

        // button click to open Time picker to set the task finishing time
        binding.timePickerTV.setOnClickListener {
            showTimePicker(requireContext()) { time, hour, mins ->
                //display the time on time textview
                binding.timePickerTV.text = time
                // set it in the todoItem
                viewModel.todoItem.time = time
                // set alarm with time and send notification
                viewModel.sendAlarm(requireContext(), hour, mins)
            }
        }

        // text watcher to get the entered title
        binding.TaskTitleET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.todoItem.title = p0.toString()
            }

        })
// text watcher to get the entered task body
        binding.TaskBodyET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.todoItem.body = p0.toString()
            }

        })
// click on start button ande save todoItem in database
        binding.startBtn.setOnClickListener {
            viewModel.addNewItem()
        }
// clicking on done button to make task checked
        // then update task in database
        binding.doneBtn.setOnClickListener {
            viewModel.todoItem.checked = true
            viewModel.updateTodoItem()
        }
// clicking on it to delete task from database
        binding.deleteBtn.setOnClickListener {
            viewModel.deleteTodoItem()
        }
    }

    //handel the view state
    fun handleState(state: TodoViewState) {
        if (state.itemAdded == true) {
            // back to list fragment
            findNavController().navigateUp()
        }

        if (state.itemUpdated == true) {
            // back to list fragment
            findNavController().navigateUp()
        }

        if (state.itemDeleted == true) {
            // back to list fragment
            findNavController().navigateUp()
        }
    }

}