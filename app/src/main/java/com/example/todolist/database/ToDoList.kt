package com.example.todolist.database

import android.widget.CheckBox
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity

data class ToDoList (


    @PrimaryKey val id :UUID = UUID.randomUUID(),
    var description:String="",
    var title:String ="",
    var date : Date = Date(),
    var checkBox: Boolean = false,
    var dueDate :Date? = null


    )
