package io.geo.guedrbootcamp6_practica.model

import io.geo.guedrbootcamp6_practica.R

class Cities {
    private val cities: List<City> = listOf(
            City("Madrid", Forecast(25f,10f,35f,"Soleado con alguna nube", R.drawable.ico_01)),
            City("Colombia", Forecast(36f,23f,19f,"Sol a tope", R.drawable.ico_01)),
            City("Venezuela", Forecast(30f,15f,40f,"Lluvia", R.drawable.ico_01))

    )

    val count
    get() = cities.size

    fun getCity(index: Int) = cities[index]

    operator fun get(i: Int) = cities[i]

    fun toArray() = cities.toTypedArray()
}