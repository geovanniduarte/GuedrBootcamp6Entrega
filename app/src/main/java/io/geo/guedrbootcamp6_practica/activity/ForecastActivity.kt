package io.geo.guedrbootcamp6_practica.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.ViewGroup
import android.widget.EditText
import io.geo.guedrbootcamp6_practica.R
import io.geo.guedrbootcamp6_practica.fragment.CityListFragment
import io.geo.guedrbootcamp6_practica.fragment.CityPagerFragment
import io.geo.guedrbootcamp6_practica.model.City
import kotlinx.android.synthetic.main.activity_forecast.*


class ForecastActivity : AppCompatActivity(), CityListFragment.OnCitySelectedListener {

    companion object {
        val TAG = ForecastActivity::class.java.canonicalName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        //Averiguamos que interfaz hemos cargado
        if (findViewById<ViewGroup>(R.id.city_list_fragment) != null) {
            //Comprobamos primero que no tenemos agregado el fragment a nuestra jerarquia
            if (supportFragmentManager.findFragmentById(R.id.city_list_fragment) == null) {
                // Añadiremos el fragment de forma dinámica
                val fragment = CityListFragment.newInstance()

                supportFragmentManager.beginTransaction()
                        .add(R.id.city_list_fragment, fragment)
                        .commit()
            }
        }

        if (findViewById<ViewGroup>(R.id.view_pager_fragment) != null) {
            //Hemos cargado una interfaz que tiene el hueco para el fragment citypagerfragment
            if (supportFragmentManager.findFragmentById(R.id.view_pager_fragment) == null) {
                supportFragmentManager.beginTransaction()
                        .add(R.id.view_pager_fragment, CityPagerFragment.newInstance(0))
                        .commit()
            }
        }

        add_button.setOnClickListener {
            val customView = layoutInflater.inflate(R.layout.view_cityinput, null)
            val editValue = customView.findViewById<EditText?>(R.id.city_name)
            AlertDialog.Builder(this)
                    .setTitle("Anadir ciudad")
                    .setMessage("Introduce la ciudad a anadir")
                    .setView(customView)
                    .setPositiveButton(android.R.string.ok, { _, _ ->
                        // Anadiremos la ciudad
                        Snackbar.make(findViewById(android.R.id.content), editValue?.text.toString(), Snackbar.LENGTH_LONG)
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
        }

    }

    override fun onCitySelected(city: City, position: Int) {

        val cityPagerFragment = supportFragmentManager.findFragmentById(R.id.view_pager_fragment) as? CityPagerFragment
        if (cityPagerFragment != null) {
            cityPagerFragment.moveToCity(position)
        } else {
            // Estamos en una interfaz donde solo hay lista, lanzamos la actividad citypageractivity
            val intent = CityPagerAcitivity.intent(this, position)
            startActivity(intent)
        }

    }
}
