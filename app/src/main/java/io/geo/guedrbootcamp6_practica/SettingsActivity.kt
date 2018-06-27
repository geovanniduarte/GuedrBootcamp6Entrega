package io.geo.guedrbootcamp6_practica

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        ok_button.setOnClickListener {
            acceptSettings()
        }

        cancell_button.setOnClickListener {
            acceptSettings()
        }

        units_rg.check(R.id.celsius_rb)
    }

    private fun cancelSettings() {


    }

    private fun acceptSettings() {


    }
}
