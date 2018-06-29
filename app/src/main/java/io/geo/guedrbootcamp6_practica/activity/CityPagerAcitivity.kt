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
import io.geo.guedrbootcamp6_practica.R
import io.geo.guedrbootcamp6_practica.fragment.ForecastFragment
import io.geo.guedrbootcamp6_practica.model.Cities
import io.geo.guedrbootcamp6_practica.model.City
import kotlinx.android.synthetic.main.activity_city_pager_acitivity.*


class CityPagerAcitivity : AppCompatActivity() {

    companion object {

        val EXTRA_CITY = "EXTRA_CITY"

        fun intent(context: Context, cityIndex: Int): Intent {
            val intent = Intent(context, CityPagerAcitivity::class.java)
            intent.putExtra(EXTRA_CITY, cityIndex)
            return intent
        }
    }

    private val cities = Cities()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_pager_acitivity)
        toolbar.setLogo(R.mipmap.ic_launcher)
        setSupportActionBar(toolbar)

        val adapter = object: FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount() = cities.count

            override fun getItem(position: Int): Fragment {
                return ForecastFragment.newInstance(cities.getCity(position))
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return cities.getCity(position).name
            }

        }

        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                updateCityInfo(position)
            }

        })
        val initialCityIndex = intent.getIntExtra(EXTRA_CITY, 0)
        moveToCity(initialCityIndex)
        updateCityInfo(initialCityIndex)
    }

    fun updateCityInfo(position: Int) {
        supportActionBar?.title = cities.getCity(position).name
    }

    private fun moveToCity(position: Int) {
        view_pager.currentItem = position
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.pager_navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) =  when(item?.itemId) {
            R.id.previous -> {
                view_pager.currentItem = view_pager.currentItem -1
                true
            }
            R.id.next -> {
                view_pager.currentItem = view_pager.currentItem +1
                true
            }
            else ->  super.onOptionsItemSelected(item)
        }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)

        val previousMenu = menu?.findItem(R.id.previous)
        val nextMenu = menu?.findItem(R.id.next)

        val adapter = view_pager.adapter!!
        previousMenu?.isEnabled = view_pager.currentItem > 0
        nextMenu?.isEnabled = view_pager.currentItem < adapter.count -1
        return true
    }

}



