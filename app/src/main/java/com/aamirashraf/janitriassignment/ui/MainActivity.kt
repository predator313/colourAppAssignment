package com.aamirashraf.janitriassignment.ui

import android.app.Activity
import android.content.ClipData.Item
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aamirashraf.janitriassignment.R
import com.aamirashraf.janitriassignment.adapter.MyAdapter
import com.aamirashraf.janitriassignment.databinding.ActivityMainBinding
import com.aamirashraf.janitriassignment.databinding.ItemRvmainBinding
import com.aamirashraf.janitriassignment.db.ColorDetailsDatabase.Companion.createDatabase
import com.aamirashraf.janitriassignment.model.ColorDetails
import com.aamirashraf.janitriassignment.repository.ColorDetailsRepository
import com.aamirashraf.janitriassignment.ui.dialog.UserInputDialog
import com.aamirashraf.janitriassignment.ui.viewmodel.ColorDetailsViewModel
import com.aamirashraf.janitriassignment.ui.viewmodel.ColorDetailsViewModelProviderFactory
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.internal.notify
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity(), UserInputDialog.DialogListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel:ColorDetailsViewModel
    private lateinit var myAdapter: MyAdapter
    private var colorCollectionRef=Firebase.firestore.collection("Colors")
    var colorDetails= mutableListOf<ColorDetails>()
    var len:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//work remaining add to room update to viewmodel and sync with firebase


        val colorDetailsRepository=ColorDetailsRepository(createDatabase(applicationContext))
        val viewModelProviderFactory=ColorDetailsViewModelProviderFactory(colorDetailsRepository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory)[ColorDetailsViewModel::class.java]
        myAdapter=MyAdapter(colorDetails as ArrayList<ColorDetails>
        )
        viewModel.fetchColorList()
        viewModel.colorList.observe(this, Observer {
            colorDetails=it as MutableList<ColorDetails>
        })
        viewModel.userCount.observe(this, Observer {
            Toast.makeText(this@MainActivity,it.toString(),Toast.LENGTH_LONG).show()
        })
        setupObserver()

        val rvMain=binding.rvMain
        rvMain.adapter=myAdapter
        val fabAdd=binding.fabButton
        rvMain.layoutManager=GridLayoutManager(this,2)



        fabAdd.setOnClickListener {
            val dialogFragment = UserInputDialog()
//            dialogFragment.setTargetFragment(r, 0)
            dialogFragment.show(supportFragmentManager, "user_inputs")

        }


    }
    fun setupObserver(){
        viewModel.colorList.observe(this, Observer {
            myAdapter.updateList(it)
        })
    }
    private fun saveToRoom(newColor:ColorDetails){
        viewModel.insert(newColor)
    }
    private fun addColorToFirebase(colorDetails: ColorDetails) = CoroutineScope(Dispatchers.IO).launch {
        try {
            colorCollectionRef.add(colorDetails).await()
            withContext(Dispatchers.Main){
                Toast.makeText(this@MainActivity,"sync Completed",Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun retrieveFromFirebase()= CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot=colorCollectionRef.get().await()
            len=querySnapshot.size()
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onDialogDataReceived(data: String) {
        // Handle the received data from the DialogFragment here
        // You can perform any required operations with the data
        // For example, update a TextView or store the data in a variable
       val hexcode=data
        val currentDate=Date()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = dateFormat.format(currentDate).toString()
        val newColor=ColorDetails(hexcode,formattedDate)
        colorDetails.add(newColor)
        saveToRoom(newColor)
//        viewModel.colorList.observe(this, Observer {
//
//        })
//        viewModel.fetchColorList()
        viewModel.colorList.postValue(colorDetails)
//        setupObserver()



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

//       menuInflater.inflate(R.menu.item_sync,menu)
//        return super.onCreateOptionsMenu(menu)
//        return super.onCreateOptionsMenu(menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.menu_item_with_sync){
//            val colorDetails=ColorDetails("#aabbcc","28/06/23",2)
//            addColorToFirebase(colorDetails)
//            retrieveFromFirebase()
//            addColorToFirebase()
//            R.id.menu_tv.


            Toast.makeText(this@MainActivity,"sync successfully",Toast.LENGTH_LONG).show()

           return true

       }
        return true
    }
}