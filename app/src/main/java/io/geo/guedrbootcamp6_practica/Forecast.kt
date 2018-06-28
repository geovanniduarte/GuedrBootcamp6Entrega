package io.geo.guedrbootcamp6_practica

enum class TemperatureUnit {
    CELSIUS, FAHRENHEIT
}

data class Forecast(private val maxTemp: Float,private val minTemp: Float, val humidity: Float, val description: String, val icon: Int) {

    protected fun toFahrenheit(celsius: Float) = celsius * 1.8f + 32

    fun getMaxTemp(units: TemperatureUnit) =
        when (units) {
            TemperatureUnit.CELSIUS -> maxTemp
            TemperatureUnit.FAHRENHEIT -> toFahrenheit(maxTemp)
        }


    fun getMinTemp(units: TemperatureUnit)  =
        when(units) {
            TemperatureUnit.CELSIUS -> minTemp
            TemperatureUnit.FAHRENHEIT -> toFahrenheit(minTemp)
        }


}