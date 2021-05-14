package com.akshay.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ToDoDao{

    @Insert
    suspend fun insert(todoModel: ToDoModel):Long

//Selecting
    @Query("Select * From TodoModel where isFinished == 0")
    fun getTask(): LiveData<List<ToDoModel>>

    //Updating
    @Query("Update TodoModel Set isFinished=1 where id=:uni")
    fun finishedTask(uni:Long)
//Deleting
    @Query("Delete from ToDoModel where id=:uni")
    fun deleteTask(uni:Long)


}