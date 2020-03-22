package com.example.vainasorte

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.lang.ClassCastException

class NumberDialogFragment : DialogFragment() {

    internal lateinit var listener : NumberDialogListener

    interface NumberDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, limiteSup : String, limiteInf : String)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as NumberDialogListener
        } catch (e : ClassCastException) {
            throw  ClassCastException((context.toString() + "must implement NumberDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog{
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val mView = inflater.inflate(R.layout.layout_input, null)

            builder.setTitle(R.string.messageDialog)
                .setView(mView)
                .setIcon(R.drawable.ic_launcher_background)
                .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->

                        val limSup = mView.findViewById<EditText>(R.id.limSuperior).text.toString()
                        val limInf = mView.findViewById<EditText>(R.id.limInferior).text.toString()

                        listener.onDialogPositiveClick(this, limSup, limInf)
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener{ dialog, id ->
                        listener.onDialogNegativeClick(this)
                    })

            builder.create()
        }!!
    }
}