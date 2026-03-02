package com.example.roomdatabasecompose.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabasecompose.dao.StudentDao
import com.example.roomdatabasecompose.model.Students

@Database(entities = [Students::class], version = 1)
abstract class StudentDatabase: RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object{

        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getDatabase(context: Context): StudentDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "Student_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}