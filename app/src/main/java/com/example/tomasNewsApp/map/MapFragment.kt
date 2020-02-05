package com.example.tomasNewsApp.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.example.tomasNewsApp.R
import com.example.tomasNewsApp.main.MainActivity
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).setSupportActionBar(toolbar)
        (requireActivity() as MainActivity).actionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as MainActivity).title = getString(R.string.toolbar_title_map)


        // create a map with the BasemapType topographic
        //val map = ArcGISMap(Basemap.Type.TOPOGRAPHIC, 55.00, 24.00, 6)

        val map2 = ArcGISTiledLayer("http://www.maps.lt/arcgis/rest/services/LietuvaTopo/MapServer")
        // val tiledLayerBaseMap = ArcGISTiledLayer("http://www.maps.lt/arcgis/rest/services/mapslt_parks/MapServer")

        val basemap = Basemap(map2)
        val map = ArcGISMap(basemap)
//        mMapView.setMap(map)


        mapView.map = map

    }

    override fun onPause() {
        super.onPause()
        mapView.pause()
    }
    override fun onResume() {
        super.onResume()
        mapView.resume()
    }
    override fun onDestroy() {
        super.onDestroy()
        mapView.dispose()
    }

    companion object {
        fun newInstance() = MapFragment()
    }
}
