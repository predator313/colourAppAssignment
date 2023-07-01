package com.aamirashraf.janitriassignment.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aamirashraf.janitriassignment.model.ColorDetails

@Database(
    entities = [ColorDetails::class],
    version = 1
)
//@TypeConverters(Converters::class)
abstract class ColorDetailsDatabase:RoomDatabase() {
    abstract fun getColorDetailsDao():ColorDetailsDao
    companion object{
        @Volatile   //volatile means all thread now if there is any changes in the db
        private var INSTANCE:ColorDetailsDatabase?=null
        fun createDatabase(context: Context):ColorDetailsDatabase{
            if(INSTANCE==null){
                synchronized(this){
                    INSTANCE= Room.databaseBuilder(context.applicationContext,ColorDetailsDatabase::class.java,"colordetails_db.db").build()
                }

            }
            return INSTANCE!!
        }
    }
}