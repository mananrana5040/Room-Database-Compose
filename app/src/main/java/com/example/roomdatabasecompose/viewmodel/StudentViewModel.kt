package com.example.roomdatabasecompose.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabasecompose.database.StudentDatabase
import com.example.roomdatabasecompose.model.Students
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StudentViewModel (application: Application) : AndroidViewModel(application) {
    private val db = StudentDatabase.getDatabase(application)
    private val dao = db.studentDao()





    val allStudents: Flow<List<Students>> = dao.getAllStudents()

    fun saveStudent(student: Students) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertStudent(student)
        }
    }
}