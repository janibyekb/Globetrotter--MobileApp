package hu.bme.aut.mobweb.boi6fk.globetrotter.network

import retrofit2.Call
import hu.bme.aut.mobweb.boi6fk.globetrotter.data.CountryData
import retrofit2.http.GET
import retrofit2.http.Path


interface CountryApi {
    @GET("v3.1/name/{name}")
    fun getCountryByName(@Path("name") name: String) : Call<List<CountryData>?>
}