package com.akshay.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TodoDao{

    @Insert
    suspend fun insert(todoModel: TodoModel):Long

//Selecting
    @Query("Select * From TodoModel where isFinished == 0")
    fun getTask(): LiveData<List<TodoModel>>

    //Updating
    @Query("Update TodoModel Set isFinished=1 where id=:uni")
    fun finishTask(uni:Long)
//Deleting
    @Query("Delete from TodoModel where id=:uni")
    fun deleteTask(uni:Long)


}