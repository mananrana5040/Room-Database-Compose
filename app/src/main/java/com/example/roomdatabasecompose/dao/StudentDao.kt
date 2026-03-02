package com.example.roomdatabasecompose.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.roomdatabasecompose.model.Students
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Insert
    suspend fun insertStudent(student: Students)

    @Query("SELECT * FROM student_table ORDER BY id DESC")
    fun getAllStudents(): Flow<List<Students>>
}