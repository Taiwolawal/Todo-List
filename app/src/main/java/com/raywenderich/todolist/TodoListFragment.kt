package com.raywenderich.todolist

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TodoListFragment : Fragment(), TodoListAdapter.TodoClickListener {

    private lateinit var todoListRecyclerView: RecyclerView
    private lateinit var todoListDataManager: TodoListDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            todoListDataManager = ViewModelProviders.of(this).get(TodoListDataManager::class.java)
        }
        val lists = todoListDataManager.readLists()
        todoListRecyclerView = view.findViewById(R.id.list_recycle)
        todoListRecyclerView.layoutManager = LinearLayoutManager(activity)
        todoListRecyclerView.adapter = TodoListAdapter(lists,this)
        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            showCreateTodoListDialog()
        }
    }

    private fun showCreateTodoListDialog() {
        activity?.let {
            val dialogTitle = "What is the name of the list?"
            val positiveButtonTitle = "Create"
            val myDialog = AlertDialog.Builder(it)
            val todoTitleEditText = EditText(it)
            todoTitleEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

            myDialog.setTitle(dialogTitle)
            myDialog.setView(todoTitleEditText)
            myDialog.setPositiveButton(positiveButtonTitle){
                dialog, _ ->
                val list = TaskList(todoTitleEditText.text.toString())
                addList(list)
                dialog.dismiss()
                showTaskListItems(list)
            }
        }
    }

    private fun showTaskListItems(list: TaskList) {
        TODO("Not yet implemented")
    }

    private fun addList(list: TaskList) {
        todoListDataManager.saveList(list)
        val todoAdapter = todoListRecyclerView.adapter as TodoListAdapter
        todoAdapter
    }

    override fun listItemClicked(list: TaskList) {
        TODO("Not yet implemented")
    }


}