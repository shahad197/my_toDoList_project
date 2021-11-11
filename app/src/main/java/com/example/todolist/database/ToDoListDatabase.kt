package com.example.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [ToDoList::class], version = 2)
@TypeConverters (ToDoListConverter::class)

abstract class ToDoListDatabase : RoomDatabase() {
    abstract fun TodolistDao():TodolistDao




}