package com.example.roomdatabasecompose.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Students(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val section: String,
    val rollNo: String
)
