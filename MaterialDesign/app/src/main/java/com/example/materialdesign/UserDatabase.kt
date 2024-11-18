package com.example.materialdesign

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.materialdesign.UserDao
import kotlin.jvm.Volatile

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var instance: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,  // Corrected this line
                    "User_database"  // Removed the extra space
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}

