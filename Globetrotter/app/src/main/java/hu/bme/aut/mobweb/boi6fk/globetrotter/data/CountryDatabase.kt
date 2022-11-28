package hu.bme.aut.mobweb.boi6fk.globetrotter.data

import android.content.Context
import androidx.room.*

@Database(entities=arrayOf(CountryData::class), version=1)
@TypeConverters(Converters::class)
abstract class CountryDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDAO

    companion object{
        private var INSTANCE:CountryDatabase? = null

        fun getInstance(context:Context): CountryDatabase{
            if(INSTANCE==null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    CountryDatabase::class.java, "globertrotter.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
        fun destroyInstance(){
            INSTANCE=null
        }
    }

}