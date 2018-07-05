package io.geo.guedrbootcamp6_practica.model

import java.io.Serializable

data class City(var name: String, var forecast: List<Forecast>): Serializable {

    override fun toString() = name
}