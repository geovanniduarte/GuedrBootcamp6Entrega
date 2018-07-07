package io.geo.guedrbootcamp6_practica.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import io.geo.guedrbootcamp6_practica.model.Forecast
import io.geo.guedrbootcamp6_practica.R
import io.geo.guedrbootcamp6_practica.activity.DetailActivity
import io.geo.guedrbootcamp6_practica.model.TemperatureUnit
import io.geo.guedrbootcamp6_practica.activity.SettingsActivity
import io.geo.guedrbootcamp6_practica.adapter.ForecastRecyclerViewAdapter
import io.geo.guedrbootcamp6_practica.getTemperatureUnits
import io.geo.guedrbootcamp6_practica.model.Cities
import io.geo.guedrbootcamp6_practica.model.City
import io.geo.guedrbootcamp6_practica.setTemperatureUnits
import kotlinx.android.synthetic.main.content_forecast.*
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

    private  enum class VIEW_INDEX(val index: Int) {
        LOADING(0), FORECAST(1)
    }

    val REQUEST_SETTINGS = 1

    var forecast: List<Forecast>? = null
        set(value) {
            field = value
            if (value != null) {
                val adapter = ForecastRecyclerViewAdapter(value)
                forecast_list.adapter = adapter
                setRecyclerViewClickListener()
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var root = inflater?.inflate(R.layout.fragment_forecast, container, false)
        return  root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuramos las animaciones para el viewSwitcher
        view_switcher.setInAnimation(activity, android.R.anim.fade_in)
        view_switcher.setOutAnimation(activity, android.R.anim.fade_out)

        //Le decimos al viewswitcher que muestre la primera vista
        view_switcher.displayedChild = VIEW_INDEX.LOADING.index
        view.postDelayed({

            //Aqui simulamos que ya nos hemos bajado la informaciond el tiempo.
            //Configuramos el ReciclerView, primero decidimos como se vusualizan sus elementos.
            forecast_list.layoutManager =  GridLayoutManager(activity, resources.getInteger(R.integer.forecast_columns))

            // Le decimos quien es el que anima el ReciclerView
            forecast_list.itemAnimator = DefaultItemAnimator()

            // Le decimos los datos que van a llenar el ReciclerView. Eso lo hace el setter

            val city = arguments?.getSerializable(ARG_CITY) as City
            forecast = city.forecast
            view_switcher.displayedChild = VIEW_INDEX.FORECAST.index
        }, resources.getInteger(R.integer.default_fake_delay).toLong())
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
                startActivityForResult(SettingsActivity.intent(activity!!, getTemperatureUnits(activity!!)),
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
                    val oldUnits = getTemperatureUnits(activity!!)
                    //Guardamos las preferenciaas del usuario
                    setTemperatureUnits(activity!!, newUnits)

                    //Actualizo la interfaz con las nuevas unidades.
                    updateTemperatureView()

                    val newUnitsString = if (newUnits == TemperatureUnit.CELSIUS) getString(R.string.user_selects_celsius) else
                        getString(R.string.user_selects_fahrenheit)

                    //Toast.makeText(this, newUnitsString, Toast.LENGTH_LONG).show()
                    Snackbar.make(view!!, newUnitsString, Snackbar.LENGTH_LONG)
                            .setAction("Deshacer", View.OnClickListener {
                                setTemperatureUnits(activity!!, oldUnits)
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

    fun setRecyclerViewClickListener() {
        val adapter = forecast_list.adapter as? ForecastRecyclerViewAdapter
        // Si alguien ha pulsado un elemento del ReciclerView, nos queremos enterar
        adapter?.onClickListener = View.OnClickListener {
            // Alguien ha pulsado un elemento del reciclerView
            val forecastIndex = forecast_list.getChildAdapterPosition(it)
            val city = arguments?.getSerializable(ARG_CITY) as City
            val cityIndex = Cities.getIndex(city)

            startActivity(DetailActivity.intent(activity!!, cityIndex, forecastIndex))
        }
    }

    // Aquí actualizaremos la interfaz con las temperaturas
    fun updateTemperatureView() {
        forecast_list?.adapter = ForecastRecyclerViewAdapter(forecast!!)
    }

    fun units2String() = if (getTemperatureUnits(activity!!) == TemperatureUnit.CELSIUS) "ºC" else "F"
}