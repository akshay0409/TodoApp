package com.akshay.myapplication


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.jvm.java as java


@Database(entities = [TodoModel::class],version=1)
abstract class AppDataBase :RoomDatabase(){
    abstract  fun todoDao():TodoDao

    companion object{

        @Volatile
        private var INSTANCE:AppDataBase?=  null

        fun getDatabase(context: Context): AppDataBase {
            val tempInstance= INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    DB_NAME
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }

}