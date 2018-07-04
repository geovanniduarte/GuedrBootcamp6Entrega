package io.geo.guedrbootcamp6_practica.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import io.geo.guedrbootcamp6_practica.R
import io.geo.guedrbootcamp6_practica.fragment.CityPagerFragment
import io.geo.guedrbootcamp6_practica.fragment.ForecastFragment
import io.geo.guedrbootcamp6_practica.model.Cities
import kotlinx.android.synthetic.main.activity_city_pager.*


class CityPagerAcitivity : AppCompatActivity() {

    companion object {

        val EXTRA_CITY = "EXTRA_CITY"

        fun intent(context: Context, cityIndex: Int): Intent {
            val intent = Intent(context, CityPagerAcitivity::class.java)
            intent.putExtra(EXTRA_CITY, cityIndex)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_pager)
        //toolbar.setLogo(R.mipmap.ic_launcher)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val initialCityIndex = intent.getIntExtra(EXTRA_CITY, 0)

        // Creo, si hace falta el CityPagerFragment pasandole la ciudad inicial
        if (supportFragmentManager.findFragmentById(R.id.view_pager_fragment)  == null) {
            val fragment: CityPagerFragment = CityPagerFragment.newInstance(initialCityIndex)
            supportFragmentManager.beginTransaction()
                    .add(R.id.view_pager_fragment, fragment)
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.pager_navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) =  when(item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else ->  super.onOptionsItemSelected(item)
        }

}



