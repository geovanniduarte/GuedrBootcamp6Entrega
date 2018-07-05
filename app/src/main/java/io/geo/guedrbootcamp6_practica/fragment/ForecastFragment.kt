package io.geo.guedrbootcamp6_practica.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.*
import io.geo.guedrbootcamp6_practica.model.Forecast
import io.geo.guedrbootcamp6_practica.R
import io.geo.guedrbootcamp6_practica.model.TemperatureUnit
import io.geo.guedrbootcamp6_practica.activity.SettingsActivity
import io.geo.guedrbootcamp6_practica.model.City
import kotlinx.android.synthetic.main.fragment_forecast.*

class ForecastFragment: Fragment() {

    companion object {

        val ARG_CITY = "ARG_CITY"

        fun newInstance(city: City) : Fragment {
            // Nos creamos el fragment
            val fragment = ForecastFragment()

            //Nos creamos los arg del fragment
            val arguments = Bundle()
            arguments.putSerializable(ARG_CITY, city)

            //Asignamos los arg al fragment
            fragment.arguments = arguments

            //Devolvermos el fragment
            return fragment
        }
    }


    val REQUEST_SETTINGS = 1
    val PREFERENCE_UNITS = "UNITS"

    var forecast: Forecast? = null
        set(value) {
            field = value
            if (value != null) {
                forecast_image.setImageResource(value.icon)
                forecast_description.text = value.description

                updateTemperatureView()
                humidity.text = getString(R.string.humidity_format, value.humidity)
            }
        }
    //variable que indica las unidades en las que queremos la temperatura, por defecto celsius
    val units : TemperatureUnit
        get () = when ( PreferenceManager.getDefaultSharedPreferences(activity)
                .getInt(PREFERENCE_UNITS, TemperatureUnit.CELSIUS.ordinal)) {
            TemperatureUnit.CELSIUS.ordinal -> TemperatureUnit.CELSIUS
            else -> TemperatureUnit.FAHRENHEIT
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var root = inflater?.inflate(R.layout.fragment_forecast, container, false)
        return  root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val city = arguments?.getSerializable(ARG_CITY) as City
            forecast = city.forecast
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.activity_forecast, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menu_show_settings -> {
                //lanzamos la pantalla de ajustes
                startActivityForResult(SettingsActivity.intent(activity!!, units),
                        REQUEST_SETTINGS)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            REQUEST_SETTINGS -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    //Volvemos de settings con datos sobre las unidades elegidads por el usuario.
                    val newUnits = data.getSerializableExtra(SettingsActivity.EXTRA_UNITS) as TemperatureUnit
                    val oldUnits = units
                    //Guardamos las preferenciaas del usuario
                    PreferenceManager.getDefaultSharedPreferences(activity)
                            .edit()
                            .putInt(PREFERENCE_UNITS, newUnits.ordinal)
                            .apply()

                    //Actualizo la interfaz con las nuevas unidades.
                    updateTemperatureView()

                    val newUnitsString = if (newUnits == TemperatureUnit.CELSIUS) getString(R.string.user_selects_celsius) else
                        getString(R.string.user_selects_fahrenheit)

                    //Toast.makeText(this, newUnitsString, Toast.LENGTH_LONG).show()
                    Snackbar.make(view!!, newUnitsString, Snackbar.LENGTH_LONG)
                            .setAction("Deshacer", View.OnClickListener {
                                PreferenceManager.getDefaultSharedPreferences(activity)
                                        .edit()
                                        .putInt(PREFERENCE_UNITS, oldUnits.ordinal)
                                        .apply()
                                updateTemperatureView()
                            })
                            .show()
                }
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && forecast != null) {
            updateTemperatureView()
        }
    }

    // Aquí actualizaremos la interfaz con las temperaturas
    fun updateTemperatureView() {
        val unitsString = units2String()
        max_temp?.text = getString(R.string.max_temp_format, forecast?.getMaxTemp(units), unitsString)
        min_temp?.text = getString(R.string.min_temp_format, forecast?.getMinTemp(units), unitsString)
    }

    fun units2String() = if (units == TemperatureUnit.CELSIUS) "ºC" else "F"
}