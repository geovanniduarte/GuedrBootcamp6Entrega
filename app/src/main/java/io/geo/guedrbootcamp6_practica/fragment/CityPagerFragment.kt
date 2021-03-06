package io.geo.guedrbootcamp6_practica.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.*
import io.geo.guedrbootcamp6_practica.R
import io.geo.guedrbootcamp6_practica.activity.CityPagerAcitivity
import io.geo.guedrbootcamp6_practica.model.Cities
import kotlinx.android.synthetic.main.fragment_city_pager.*


class CityPagerFragment: Fragment() {

    companion object {
        val ARG_CITY = "ARG_CITY"

        fun newInstance(cityIndex: Int) : CityPagerFragment {
            val arguments = Bundle()
            arguments.putInt(ARG_CITY, cityIndex)
            val fragment = CityPagerFragment()
            fragment.arguments = arguments

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.fragment_city_pager, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = object: FragmentPagerAdapter(fragmentManager) {
            override fun getCount() = Cities.count

            override fun getItem(position: Int): Fragment {
                return ForecastFragment.newInstance(Cities.getCity(position))
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return Cities.getCity(position).name
            }
        }

        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                updateCityInfo(position)
            }

        })


        val initialCityIndex = arguments?.getInt(ARG_CITY, 0)

        if (initialCityIndex != null) {
            moveToCity(initialCityIndex)
            updateCityInfo(initialCityIndex)
        }

    }

    fun updateCityInfo(position: Int) {
        if (activity is AppCompatActivity) {
            val supportActionBar: ActionBar? = (activity as? AppCompatActivity?)?.supportActionBar
            supportActionBar?.title = Cities[position].name
        }
    }

    fun moveToCity(position: Int) {
        view_pager.currentItem = position
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.pager_navigation, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.previous -> {
            view_pager.currentItem = view_pager.currentItem - 1
            true
        }
        R.id.next -> {
            view_pager.currentItem = view_pager.currentItem + 1
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        val previousMenu = menu?.findItem(R.id.previous)
        val nextMenu = menu?.findItem(R.id.next)

        val adapter = view_pager.adapter!!
        previousMenu?.isEnabled = view_pager.currentItem > 0
        nextMenu?.isEnabled = view_pager.currentItem < adapter.count -1
    }

}