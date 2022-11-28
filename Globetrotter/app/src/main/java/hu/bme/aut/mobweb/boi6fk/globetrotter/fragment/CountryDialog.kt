package hu.bme.aut.mobweb.boi6fk.globetrotter.fragment

import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.app.Dialog
import android.content.Context

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.mobweb.boi6fk.globetrotter.R
import hu.bme.aut.mobweb.boi6fk.globetrotter.data.CountryData
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

import hu.bme.aut.mobweb.boi6fk.globetrotter.data.Flags
import hu.bme.aut.mobweb.boi6fk.globetrotter.data.Name
import hu.bme.aut.mobweb.boi6fk.globetrotter.databinding.DialogCountryNameBinding

class CountryDialog : DialogFragment() {
    interface CountryHandler {
        fun countryAdded(country: String)
    }

    lateinit var countryHandler: CountryHandler
    private var _binding: DialogCountryNameBinding? = null
    private val binding get() = _binding!!

    private lateinit var etCountryName: EditText
    lateinit var alertDialog: AlertDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        countryHandler = (context as? CountryHandler)!!

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(requireContext())

        _binding = DialogCountryNameBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)

        etCountryName = binding.etCountryName

        builder.setTitle("Country Dialog")
        builder.setPositiveButton("Ok") { dialog, which->}
        builder.setNegativeButton("Cancel") { dialog, which->}

        alertDialog = builder.create()
        return alertDialog
    }


    override fun onResume() {

       alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener() {
           if (binding.etCountryName.text.isNotEmpty()) {
               countryHandler.countryAdded(
                    binding.etCountryName.text.toString()
               )
               //Toast.makeText(context,"Country is added",Toast.LENGTH_LONG).show()
           }else{
               binding.etCountryName.error = getString(R.string.empty_wtext)
           }
       }

        super.onResume()
    }
}
