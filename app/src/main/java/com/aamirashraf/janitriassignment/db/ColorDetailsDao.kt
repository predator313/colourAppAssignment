package com.aamirashraf.janitriassignment.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aamirashraf.janitriassignment.model.ColorDetails

@Dao
interface ColorDetailsDao {
    @Insert
    suspend fun insert(colorDetails: ColorDetails)
    @Delete
    suspend fun delete(colorDetails: ColorDetails)
    @Query("select * from colorDetails")
    fun getAllColors():List<ColorDetails>

    //now lets count the number of rows in room db
    @Query("select count(*) from colorDetails")
    fun getCount():LiveData<Int>
}