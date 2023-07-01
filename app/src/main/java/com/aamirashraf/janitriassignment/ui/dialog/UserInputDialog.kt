package com.aamirashraf.janitriassignment.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.aamirashraf.janitriassignment.R

class UserInputDialog:DialogFragment() {
    var dialogListener: DialogListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Color Hex code")
        alertDialogBuilder.setMessage("Please enter your hexcode")

        val inputEditText = EditText(context)
        alertDialogBuilder.setView(inputEditText)

        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
            val userInput = inputEditText.text.toString()
//            Toast.makeText(context, "Input: $userInput", Toast.LENGTH_SHORT).show()
            if(isValidHexCode(userInput)){

                dialogListener?.onDialogDataReceived(userInput)
//
            }else{
                Toast.makeText(requireContext(),"not a valid HashCode",Toast.LENGTH_LONG).show()
            }
//

        }

        alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->
            Toast.makeText(context, "Input canceled", Toast.LENGTH_SHORT).show()
        }


        return alertDialogBuilder.create()

    }
    fun isValidHexCode(hexCode: String): Boolean {
        val regexPattern = Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
        return regexPattern.matches(hexCode)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dialogListener = context as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement DialogListener")
        }
    }

    interface DialogListener {
        fun onDialogDataReceived(data: String)
    }

}