package io.geo.guedrbootcamp6_practica.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.geo.guedrbootcamp6_practica.R
import io.geo.guedrbootcamp6_practica.activity.SettingsActivity
import io.geo.guedrbootcamp6_practica.model.TemperatureUnit
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsDialog : DialogFragment() {

    companion object {

        var ARG_UNITS = "ARG_UNITS"
        fun newInstance(initialUnits: TemperatureUnit) : SettingsDialog {
            val arguments = Bundle()
            arguments.putSerializable(ARG_UNITS, initialUnits)

            val dialog = SettingsDialog()
            dialog.arguments = arguments
            return dialog
        }
    }

    val initialUnits by lazy {
        arguments?.getSerializable(ARG_UNITS)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
         super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.activity_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ok_button.setOnClickListener {
            acceptSettings()
        }

        cancell_button.setOnClickListener {
            cancelSettings()
        }

        units_rg.check(if (initialUnits == TemperatureUnit.CELSIUS) R.id.celsius_rb else R.id.farenheit_rb)
    }

    private fun cancelSettings() {
        //Indicamos que cancelamos elenvio de datos
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null)
        dismiss()
    }

    private fun acceptSettings() {
        //Creamos los datos de regreso, en este caso las unidades elegidas
        val returnIntent = Intent()
        when (units_rg.checkedRadioButtonId) {
            R.id.celsius_rb -> returnIntent.putExtra(ARG_UNITS, TemperatureUnit.CELSIUS)
            R.id.farenheit_rb -> returnIntent.putExtra(ARG_UNITS, TemperatureUnit.FAHRENHEIT)
        }
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, returnIntent)
        dismiss()
    }
}