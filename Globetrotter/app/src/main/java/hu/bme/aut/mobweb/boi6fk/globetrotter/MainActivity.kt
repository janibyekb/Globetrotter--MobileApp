package hu.bme.aut.mobweb.boi6fk.globetrotter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import hu.bme.aut.mobweb.boi6fk.globetrotter.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancel.setOnClickListener(){

            binding.etUserName.text.clear()
            binding.etPassword.text.clear()
        }
        try {
            binding.btnLogin.setOnClickListener() {
                if (binding.etUserName.text.isNotEmpty()) {
                    val intent = Intent(this, CountryListActivity::class.java)
                    intent.putExtra(KEY_NAME, binding.etUserName.text.toString())
                    intent.putExtra(KEY_PASSWORD, binding.etPassword.text.toString())
                    startActivity(intent)
                } else {
                    binding.etUserName.error = getString(R.string.empty_wtext)
                }
            }
        }
        catch(e:Exception){
            binding.etUserName.error = e.message
        }
    }

    companion object {
        val KEY_NAME = "KEY_NAME";
        val KEY_PASSWORD = "KEY_PASSWORD"
    }
}