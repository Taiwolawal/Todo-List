package com.raywenderich.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.title = getString(R.string.todo_list)
    }
}