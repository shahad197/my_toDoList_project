package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment = supportFragmentManager.findFragmentById(R.id .fragmentContainer)
        if (currentFragment==null){

            val fragment= FragmentToDoList()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer,fragment)
                .commit()


        }
    }
}