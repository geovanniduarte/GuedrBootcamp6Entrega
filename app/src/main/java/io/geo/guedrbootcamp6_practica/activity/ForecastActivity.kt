package io.geo.guedrbootcamp6_practica.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.geo.guedrbootcamp6_practica.R
import io.geo.guedrbootcamp6_practica.fragment.CityListFragment
import io.geo.guedrbootcamp6_practica.model.City


class ForecastActivity : AppCompatActivity(), CityListFragment.OnCitySelectedListener {

    companion object {
        val TAG = ForecastActivity::class.java.canonicalName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        //Comprobamos primero que no tenemos agregado el fragment a nuestra jerarquia
        if (supportFragmentManager.findFragmentById(R.id.city_list) == null) {
            // Añadiremos el fragment de forma dinámica
            val fragment = CityListFragment.newInstance()

            supportFragmentManager.beginTransaction()
                    .add(R.id.city_list_fragment, fragment)
                    .commit()
        }
    }

    override fun onCitySelected(city: City, position: Int) {
        val intent = CityPagerAcitivity.intent(this, position)
        startActivity(intent)
    }
}
