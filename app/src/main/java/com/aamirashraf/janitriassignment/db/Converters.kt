package com.aamirashraf.janitriassignment.db

import androidx.room.TypeConverter
import com.aamirashraf.janitriassignment.model.ColorDetails
import java.util.Date


class Converters {
    @TypeConverter
    fun fromDate(date: Date):String{
       return date.toString()
    }
    @TypeConverter
    fun toDate(dateStr:String):Date{
        return Date()
    }
}