package hu.bme.aut.mobweb.boi6fk.globetrotter.network

import android.content.Context
import hu.bme.aut.mobweb.boi6fk.globetrotter.data.CountryData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private const val SERVICE_URL = "https://restcountries.com/"
    private var countryApi: CountryApi
    init{

        val retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        countryApi = retrofit.create(CountryApi::class.java)
    }

    fun getCountryByName(name: String): Call<List<CountryData>?> {
        return countryApi.getCountryByName(name)
    }
}