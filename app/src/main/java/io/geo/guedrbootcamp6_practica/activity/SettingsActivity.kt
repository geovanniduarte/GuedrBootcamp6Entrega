package io.geo.guedrbootcamp6_practica.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.geo.guedrbootcamp6_practica.R
import io.geo.guedrbootcamp6_practica.model.TemperatureUnit
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    companion object {

        val EXTRA_UNITS = "EXTRA_UNITS"

        fun intent(context: Context, initialUnits: TemperatureUnit) : Intent {
            var intent = Intent(context, SettingsActivity::class.java)
            intent.putExtra(EXTRA_UNITS, initialUnits)
            return intent
        }
    }

    val initialUnits by lazy {
        intent.getSerializableExtra(EXTRA_UNITS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        ok_button.setOnClickListener {
            acceptSettings()
        }

        cancell_button.setOnClickListener {
            acceptSettings()
        }
        units_rg.check(if (initialUnits == TemperatureUnit.CELSIUS) R.id.celsius_rb else R.id.farenheit_rb)
        //Decidimos que radiobutton debe estar marcado en funcion de initialUnits
//        if (initialUnits == TemperatureUnit.CELSIUS) {
//            units_rg.check(R.id.celsius_rb)
//        } else {
//            units_rg.check(R.id.farenheit_rb)
//        }


    }

    private fun cancelSettings() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun acceptSettings() {
        //Creamos los datos de regreso, en este caso las unidades elegidas
        val returnIntent = Intent()
        when (units_rg.checkedRadioButtonId) {
            R.id.celsius_rb -> returnIntent.putExtra(EXTRA_UNITS, TemperatureUnit.CELSIUS)
            R.id.farenheit_rb -> returnIntent.putExtra(EXTRA_UNITS, TemperatureUnit.FAHRENHEIT)
        }
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
