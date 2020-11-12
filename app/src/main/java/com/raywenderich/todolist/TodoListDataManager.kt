package com.raywenderich.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager

class TodoListDataManager(app: Application): AndroidViewModel(app) {
    private val context = app.applicationContext

    fun saveList(list: TaskList){
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.putStringSet(list.name, list.tasks.toHashSet())
        sharedPrefs.apply()
    }

    fun readLists(): ArrayList<TaskList>{
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        //contents contain a map of keys and values
        val contents =sharedPrefs.all
        val taskLists = ArrayList<TaskList>()

        for (taskList in contents){
            //get the saved hashset and convert to arraylist
            val taskItems = ArrayList(taskList.value as HashSet<String>)
            val lists = TaskList(taskList.key, taskItems)
            //add to arraylist
            taskLists.add(lists)
        }

        return taskLists
    }
}