package io.geo.guedrbootcamp6_practica

import android.content.Context
import io.geo.guedrbootcamp6_practica.model.TemperatureUnit

fun units2String(unit: TemperatureUnit) = if (unit == TemperatureUnit.CELSIUS) "ºC" else "F"

fun forecastDay(context: Context, index: Int) = when(index) {
    0 -> context.getString(R.string.today)
    1 -> context.getString(R.string.tomorrow)
    2 -> context.getString(R.string.day_after_tomorrow)
    3 -> context.getString(R.string.day_after_after_tomorrow)
    4 -> context.getString(R.string.day_after_after_after_tomorrow)
    else -> context.getString(R.string.unknown_day)
}