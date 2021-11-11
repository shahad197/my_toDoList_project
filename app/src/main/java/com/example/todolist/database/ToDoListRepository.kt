package com.example.todolist.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_N ="todo-database"
class ToDoListRepository private constructor(context: Context){

private val database :ToDoListDatabase = Room.databaseBuilder (
    context.applicationContext,
    ToDoListDatabase::class.java ,
    DATABASE_N

).build()

   private val TodolistDao = database.TodolistDao()
    private val exector = Executors.newSingleThreadExecutor()

    fun getAllToDoList():LiveData<List<ToDoList>> {
        return TodolistDao.getAllToDoList()
    }
    fun getToDoList(id :UUID):LiveData<ToDoList?>{
        return TodolistDao.getToDoList(id)
    }
    fun updateToDoList(list:ToDoList){
        exector.execute{
            TodolistDao.updateToDoList(list)

        }
    }
    fun addToDoList(list: ToDoList){
        exector.execute{
            TodolistDao.addToDoList(list)
        }
    }
    fun deleteToDoList(list: ToDoList){
        exector.execute {
            TodolistDao.deleteToDoList(list)
        }
    }

    companion object{
      var INSTANCE:ToDoListRepository? = null

      fun initialize(context: Context) {
          if (INSTANCE == null) {
              INSTANCE = ToDoListRepository(context)
          }
      }

          fun get ():ToDoListRepository{
              return INSTANCE?:
              throw IllegalStateException("ToDoListRepository must be inshilaized")
          }
      }

    }


