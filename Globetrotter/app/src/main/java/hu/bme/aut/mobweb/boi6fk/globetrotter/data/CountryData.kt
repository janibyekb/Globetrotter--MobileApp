package hu.bme.aut.mobweb.boi6fk.globetrotter.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson


@Entity(tableName="countries")
class CountryData(var name: Name, @PrimaryKey var cca3: String, var flags: Flags ){

}
class Name {
    lateinit var common: String
    constructor(str: String){
        common = str
    }
}
class Flags{
    lateinit var png: String
    constructor(str: String){
        png = str
    }
}
class Converters{
    val gson = Gson()
    @TypeConverter
    fun nameToString(name: Name):String{
        return gson.toJson(name)
    }

    @TypeConverter
    fun stringToName(stringName: String):Name{
        return gson.fromJson(stringName, Name::class.java)
    }
    @TypeConverter
    fun flagsToString(flagsToString: Flags):String{
        return gson.toJson(flagsToString)
    }
    @TypeConverter
    fun stringToFlags(stringFlags: String):Flags{
        return gson.fromJson(stringFlags, Flags::class.java)
    }
}