package io.geo.guedrbootcamp6_practica.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import io.geo.guedrbootcamp6_practica.R
import io.geo.guedrbootcamp6_practica.model.Cities
import io.geo.guedrbootcamp6_practica.model.City
import kotlinx.android.synthetic.main.fragment_city_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CityListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CityListFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = CityListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cities = Cities()

        val adapter = ArrayAdapter<City>(
                activity,
                android.R.layout.simple_list_item_1,
                cities.toArray())

        city_list.adapter = adapter

    }
}
