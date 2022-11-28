package hu.bme.aut.mobweb.boi6fk.globetrotter

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.mobweb.boi6fk.globetrotter.MainActivity.Companion.KEY_NAME
import hu.bme.aut.mobweb.boi6fk.globetrotter.adapter.CountryAdapter
import hu.bme.aut.mobweb.boi6fk.globetrotter.data.CountryData
import hu.bme.aut.mobweb.boi6fk.globetrotter.data.CountryDatabase
import hu.bme.aut.mobweb.boi6fk.globetrotter.databinding.ActivityCountryListBinding
import hu.bme.aut.mobweb.boi6fk.globetrotter.fragment.CountryDialog
import hu.bme.aut.mobweb.boi6fk.globetrotter.network.NetworkManager
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class CountryListActivity : AppCompatActivity(), CountryDialog.CountryHandler {

    lateinit var binding: ActivityCountryListBinding
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        countryAdapter = CountryAdapter(this)
        binding.recyclerCountry.adapter = countryAdapter

        var text = intent.getStringExtra(MainActivity.KEY_NAME)
        Snackbar.make(binding.toolbar, text.toString(), 1000)
            .setAction("Action", null).show()

        binding.fab.setOnClickListener { view ->
            CountryDialog().show(supportFragmentManager, "Dialog")
        }

        Thread{
            var dbCountries = CountryDatabase.getInstance(this).countryDao().getAllCountries()

            runOnUiThread{
                countryAdapter = CountryAdapter(this,dbCountries )
                binding.recyclerCountry.adapter = countryAdapter
            }
        }.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_country_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.map -> {
                Snackbar.make(binding.toolbar, getString(R.string.map_title), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            R.id.about -> {
                Snackbar.make(binding.toolbar, getString(R.string.txtInfo), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            R.id.logout -> {
                super.finish()
            }
        }
        return true
    }
    
    override fun onBackPressed() {
        Snackbar.make(binding.toolbar, getString(R.string.logout_wtext), Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun countryAdded(country: String) {
        NetworkManager.getCountryByName(country).enqueue(object: Callback<List<CountryData>?> {
            override fun onResponse(
                call: Call<List<CountryData>?>,
                response: Response<List<CountryData>?>
            ) {
                if(response.isSuccessful){
                    countryAdapter.addCountry(response.body()!![0])
                }
                else{
                    Snackbar.make(binding.root, "Error"+response.message(),
                    Snackbar.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<CountryData>?>, t: Throwable) {
                t.printStackTrace()
                Snackbar.make(binding.root, "Network request error occured, check LOG",
                Snackbar.LENGTH_LONG).show()
            }

        })
    }
}