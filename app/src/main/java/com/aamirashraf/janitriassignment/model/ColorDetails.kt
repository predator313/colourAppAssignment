package com.aamirashraf.janitriassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("colorDetails")
data class ColorDetails(

    var hashcode:String?=null,
    var date:String?=null,
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    )
