package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface TodolistDao {

    @Query("SELECT* FROM ToDoList")

    fun getAllToDoList():LiveData<List<ToDoList>>
    @Query("SELECT *FROM ToDoList WHERE id = (:id)")
    fun getToDoList(id: UUID):LiveData<ToDoList?>

    @Update
    fun updateToDoList(list: ToDoList)

    @Insert
    fun addToDoList(list: ToDoList)

    @Delete
    fun deleteToDoList (list: ToDoList)


}
