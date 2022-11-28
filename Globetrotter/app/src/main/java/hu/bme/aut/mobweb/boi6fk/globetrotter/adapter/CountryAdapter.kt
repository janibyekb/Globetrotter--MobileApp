package hu.bme.aut.mobweb.boi6fk.globetrotter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.mobweb.boi6fk.globetrotter.CountryListActivity
import hu.bme.aut.mobweb.boi6fk.globetrotter.adapter.CountryAdapter.*
import hu.bme.aut.mobweb.boi6fk.globetrotter.data.CountryData
import hu.bme.aut.mobweb.boi6fk.globetrotter.data.CountryDatabase
import hu.bme.aut.mobweb.boi6fk.globetrotter.databinding.CountryItemBinding

class CountryAdapter(var context: Context) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    var countryItems = mutableListOf<CountryData>()

    constructor(context: Context, dbCountries:List<CountryData>) : this(context) {
        countryItems.addAll(dbCountries)
    }


    inner class ViewHolder(val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCountry = countryItems[position]

        holder.binding.tvCountryName.text = currentCountry.name.common
        holder.binding.tvCountryCode.text = currentCountry.cca3

        Glide.with(context).load(currentCountry.flags.png).into(holder.binding.ivFlag)

        holder.binding.btnDelete.setOnClickListener {
            deleteCountry(holder.adapterPosition)
        }
    }

    private fun deleteCountry(position: Int) {


        Thread{
            CountryDatabase.getInstance(context).countryDao().deleteCountry(countryItems.get(position))


            (context as CountryListActivity).runOnUiThread() {
                countryItems.removeAt(position)
                notifyDataSetChanged()
            }
        }.start()
    }

    fun addCountry(country: CountryData){

            Thread{
                try {
                    CountryDatabase.getInstance(context).countryDao().insertCountry(country)

                    (context as CountryListActivity).runOnUiThread() {
                        countryItems.add(country)
                        notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    Snackbar.make((context as CountryListActivity).binding.root, e.localizedMessage, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
                }
            }.start()

    }

    override fun getItemCount(): Int {
        return countryItems.size
    }

}