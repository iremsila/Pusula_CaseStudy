package com.dijitalgaraj.study.models

import android.content.Context
import android.os.Parcelable
import com.dijitalgaraj.study.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import java.io.InputStreamReader

@Parcelize
data class Place (
    val title:String,
    val places: List<String>,
    val colorName:String,
    val color:String
): Parcelable

fun loadPlacesFromJson(context: Context): List<Place> {
    context.resources.openRawResource(R.raw.places).use { inputStream ->
        InputStreamReader(inputStream).use { reader ->
            val placeListType = object : TypeToken<List<Place>>() {}.type
            return Gson().fromJson(reader, placeListType)
        }
    }
}
