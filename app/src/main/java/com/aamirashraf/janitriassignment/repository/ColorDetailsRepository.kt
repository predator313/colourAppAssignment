package com.aamirashraf.janitriassignment.repository

import androidx.lifecycle.LiveData
import com.aamirashraf.janitriassignment.db.ColorDetailsDatabase
import com.aamirashraf.janitriassignment.model.ColorDetails

class ColorDetailsRepository(
    val db:ColorDetailsDatabase
) {
    suspend fun insert(colorDetails: ColorDetails)=db.getColorDetailsDao().insert(colorDetails)
    suspend fun delete(colorDetails: ColorDetails)=db.getColorDetailsDao().delete(colorDetails)
     fun fetch(): List<ColorDetails> {
        return db.getColorDetailsDao().getAllColors()
    }
    //for count
    fun getCount():LiveData<Int>{
        return db.getColorDetailsDao().getCount()
    }

}