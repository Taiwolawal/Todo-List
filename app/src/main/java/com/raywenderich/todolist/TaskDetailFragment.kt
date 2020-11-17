package com.raywenderich.todolist

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.InputDevice
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TaskDetailFragment : Fragment() {

    lateinit var list: TaskList
    lateinit var taskListRecyclerView: RecyclerView
    lateinit var addTaskButton: FloatingActionButton
    lateinit var listDataManager: TodoListDataManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listDataManager = ViewModelProviders.of(this).get(TodoListDataManager::class.java)
        arguments?.let {
            val args = TaskDetailFragmentArgs.fromBundle(it)
            list = listDataManager.readLists().filter {list -> list.name == args.listString}[0]
        }
        activity?.let {
            taskListRecyclerView = view.findViewById(R.id.task_list_recyclerview)
            taskListRecyclerView.layoutManager = LinearLayoutManager(it)
            taskListRecyclerView.adapter = TaskListAdapter(list)
            it.title = list.name
            addTaskButton = view.findViewById(R.id.add_task_button)
            addTaskButton.setOnClickListener {
                showCreateTaskDialog()
            }
        }
    }

    private fun showCreateTaskDialog() {
       activity?.let {
           val taskEditText = EditText(it)
           taskEditText.inputType = InputType.TYPE_CLASS_TEXT
           AlertDialog.Builder(it)
                   .setTitle("What is the task you want to add")
                   .setView(taskEditText)
                   .setPositiveButton("Add"){
                       dialog, _ ->
                       val task =  taskEditText.text.toString()
                       list.tasks.add(task)
                       listDataManager.saveList(list)
                       dialog.dismiss()
                   }
                   .create()
                   .show()
       }
    }

    companion object {

        private const val ARG_LIST = "list"

        fun newInstance(list: TaskList): TaskDetailFragment{
            val bundle = Bundle()
            bundle.putParcelable(ARG_LIST, list)
            val fragment = TaskDetailFragment()
            fragment.arguments = bundle
            return  fragment
        }
    }
}