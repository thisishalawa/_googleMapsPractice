package master.google.maps_practice.intro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import master.google.maps_practice.MainActivity
import master.google.maps_practice.R
import master.google.maps_practice.databinding.FragmentIntroBinding
import master.google.maps_practice.utils.Constant.Companion.cairoLat
import master.google.maps_practice.utils.Constant.Companion.cairoLng
import master.google.maps_practice.utils.TAG

class IntroFragment : Fragment(), OnMapReadyCallback {

    // binding
    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    // map
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if ((activity as MainActivity).isLocationServicesOK()) {

            Log.d(TAG, "locationServicesOK .. ")
            setUpMap()

        }


    }

    private fun setUpMap() {
        val mapFragment =
            (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        /*
        * signingReport
        * getApplication SHA1
        * go to https://console.cloud.google.com/
        * credentials -> do your magic
        * add project package & SHA
        *
        * */
        mMap = googleMap
        val cairo = LatLng(cairoLat, cairoLng)
        mMap.addMarker(MarkerOptions().position(cairo).title("Marker in Cairo"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cairo))

    }

}
