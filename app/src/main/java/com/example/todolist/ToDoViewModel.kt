package com.example.todolist

import androidx.lifecycle.ViewModel
import com.example.todolist.database.ToDoList
import com.example.todolist.database.ToDoListRepository

class ToDoViewModel:ViewModel() {
private val toDoListRepository = ToDoListRepository.get()
    val lifeDataToDo = toDoListRepository.getAllToDoList()

    fun addList (toDoList: ToDoList){
        toDoListRepository.addToDoList(toDoList)


    }

}