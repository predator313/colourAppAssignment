package com.aamirashraf.janitriassignment.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.aamirashraf.janitriassignment.databinding.ItemRvmainBinding
import com.aamirashraf.janitriassignment.model.ColorDetails

class MyAdapter(val colorDetails: ArrayList<ColorDetails>):Adapter<MyAdapter.MyViewHolder>()  {

    inner class MyViewHolder(val binding: ItemRvmainBinding): RecyclerView.ViewHolder(binding.root){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding=ItemRvmainBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return colorDetails.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current=colorDetails[position]
        current.apply {
            holder.binding.tvHashcode.text=current.hashcode
            holder.binding.date.text=current.date
//            val parsecol=ColorDetails
            holder.binding.cv.setCardBackgroundColor(Color.parseColor(current.hashcode))
        }
    }
    fun updateList(list: List<ColorDetails>){
        if(list != colorDetails){
            colorDetails.clear()
            colorDetails.addAll(list)
            notifyDataSetChanged()
        }

    }
}