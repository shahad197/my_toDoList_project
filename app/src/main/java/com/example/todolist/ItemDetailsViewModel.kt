package com.example.todolist

import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todolist.database.ToDoList
import com.example.todolist.database.ToDoListRepository
import java.util.*

class ItemDetailsViewModel :ViewModel() {

 private val toDoListRepository = ToDoListRepository.get()
  private val todolistIDLiveData = MutableLiveData <UUID>()


 var todoLiveData: LiveData<ToDoList> =
  Transformations.switchMap(todolistIDLiveData){
   toDoListRepository.getToDoList(it)
  }
 fun loadTooList(toDoListId:UUID){
  todolistIDLiveData.value = toDoListId
 }
 fun saveUpdate(toDoList: ToDoList){
  toDoListRepository.updateToDoList(toDoList)
 }
 fun delete(toDoList: ToDoList){
  toDoListRepository.deleteToDoList(toDoList)
 }








}