package io.geo.guedrbootcamp6_practica

import android.content.Context
import android.preference.PreferenceManager
import io.geo.guedrbootcamp6_practica.model.TemperatureUnit

val PREFERENCE_UNITS = "UNITS"

fun getTemperatureUnits(context: Context) = if (PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREFERENCE_UNITS, TemperatureUnit.CELSIUS.ordinal) == TemperatureUnit.CELSIUS.ordinal)
    TemperatureUnit.CELSIUS
else
    TemperatureUnit.FAHRENHEIT


fun setTemperatureUnits(context: Context, units: TemperatureUnit) {
    PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putInt(PREFERENCE_UNITS, units.ordinal)
            .apply()
}