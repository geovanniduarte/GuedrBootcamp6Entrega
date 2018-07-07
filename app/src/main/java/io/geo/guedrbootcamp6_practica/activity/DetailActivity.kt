package io.geo.guedrbootcamp6_practica.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.geo.guedrbootcamp6_practica.R
import io.geo.guedrbootcamp6_practica.forecastDay
import io.geo.guedrbootcamp6_practica.getTemperatureUnits
import io.geo.guedrbootcamp6_practica.model.Cities
import io.geo.guedrbootcamp6_practica.model.City
import io.geo.guedrbootcamp6_practica.model.Forecast
import io.geo.guedrbootcamp6_practica.units2String
import kotlinx.android.synthetic.main.content_forecast.*

class DetailActivity : AppCompatActivity() {

    companion object {

        val EXTRA_CITY_INDEX = "EXTRA_CITY_INDEX"
        val EXTRA_FORECAST_INDEX = "EXTRA_FORECAST_INDEX"
        fun intent(context: Context, cityIndex: Int, forecastIndex: Int) : Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_CITY_INDEX, cityIndex)
            intent.putExtra(EXTRA_FORECAST_INDEX, forecastIndex)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Sacamos los datos con los que debemos configurar la interfaz
        val city: City = Cities[intent.getIntExtra(EXTRA_CITY_INDEX, 0)]
        val dayIndex = intent.getIntExtra(EXTRA_FORECAST_INDEX, 0)
        val forecast: Forecast = city.forecast[dayIndex]

        day.text = forecastDay(this, dayIndex)
        // Actualizamos la interfaz
        forecast_image.setImageResource(forecast.icon)
        humidity.text = getString(R.string.humidity_format, forecast.humidity)
        forecast_description.text = forecast.description

        val temperatureUnit = getTemperatureUnits(this)
        val unitsString = units2String(temperatureUnit)
        max_temp?.text = getString(R.string.max_temp_format, forecast?.getMaxTemp(temperatureUnit), unitsString)
        min_temp?.text = getString(R.string.min_temp_format, forecast?.getMinTemp(temperatureUnit), unitsString)

    }
}
