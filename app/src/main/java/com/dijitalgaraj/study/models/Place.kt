package com.dijitalgaraj.study.models

import android.content.Context
import android.os.Parcelable
import com.dijitalgaraj.study.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

@Parcelize
data class Place(
    val title: String,
    val places: List<String>,
    val colorName: String,
    val color: String
) : Parcelable

suspend fun loadPlacesFromJson(context: Context): List<Place> {
    return withContext(Dispatchers.IO) {
        try {
            context.resources.openRawResource(R.raw.places).use { inputStream ->
                InputStreamReader(inputStream).use { reader ->
                    val placeListType = object : TypeToken<List<Place>>() {}.type
                    Gson().fromJson<List<Place>>(reader, placeListType)
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
