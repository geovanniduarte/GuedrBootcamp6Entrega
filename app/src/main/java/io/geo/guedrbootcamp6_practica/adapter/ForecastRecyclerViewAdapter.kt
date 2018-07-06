package io.geo.guedrbootcamp6_practica.adapter


import io.geo.guedrbootcamp6_practica.model.Forecast
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.geo.guedrbootcamp6_practica.R
import io.geo.guedrbootcamp6_practica.model.TemperatureUnit
import kotlinx.android.synthetic.main.content_forecast.view.*


class ForecastRecyclerViewAdapter(private val forecast: List<Forecast>): RecyclerView.Adapter<ForecastRecyclerViewAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.content_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun getItemCount() = forecast.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
       holder.bindForecast(forecast[position], TemperatureUnit.CELSIUS, position)
    }

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayText = itemView.findViewById<TextView>(R.id.day)
        val forecastImage = itemView.findViewById<ImageView?>(R.id.forecast_image)
        val maxTemp = itemView.findViewById<TextView?>(R.id.max_temp)
        val minTemp = itemView.findViewById<TextView?>(R.id.min_temp)
        val humidity = itemView.findViewById<TextView?>(R.id.humidity)
        val forecastDescription = itemView.findViewById<TextView?>(R.id.forecast_description)
        val context = itemView.context

        fun bindForecast(forecast: Forecast, temperatureUnit: TemperatureUnit, day: Int) {
            // Actualizamos la vista con el modelo.
            forecastImage?.setImageResource(forecast.icon)
            forecastDescription?.text = forecast.description
            updateTemperatureView(forecast, temperatureUnit)
            humidity?.text = context.getString(R.string.humidity_format, forecast.humidity)
            dayText?.text = forecastDay(day)
        }

        fun updateTemperatureView(forecast: Forecast, unit: TemperatureUnit) {
            val unitsString = units2String(unit)
            maxTemp?.text = context.getString(R.string.max_temp_format, forecast?.getMaxTemp(unit), unitsString)
            minTemp?.text = context.getString(R.string.min_temp_format, forecast?.getMinTemp(unit), unitsString)
        }

        fun units2String(unit: TemperatureUnit) = if (unit == TemperatureUnit.CELSIUS) "ºC" else "F"
        
        fun forecastDay(index: Int) = when(index) {
            0 -> context.getString(R.string.today)
            1 -> context.getString(R.string.tomorrow)
            2 -> context.getString(R.string.day_after_tomorrow)
            3 -> context.getString(R.string.day_after_after_tomorrow)
            4 -> context.getString(R.string.day_after_after_after_tomorrow)
            else -> context.getString(R.string.unknown_day)
        }
    }


}