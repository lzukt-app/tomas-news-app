package com.example.tomasNewsApp.about

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tomasNewsApp.BuildConfig
import com.example.tomasNewsApp.R
import com.example.tomasNewsApp.utils.location.LocationManager
import com.google.android.gms.location.LocationServices
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {
    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_about, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.toolbar_title_about)

        version.text = "${getString(R.string.version)} ${BuildConfig.VERSION_NAME}"
        other.text = "${getString(R.string.developer)} ${getString(
            R.string.company
        )}"

        val manager =
            LocationManager(
                LocationServices.getFusedLocationProviderClient(requireActivity()),
                LocationServices.getSettingsClient(requireActivity())
            )
        disposable = manager.locationUpdates
            .mergeWith(manager.lastLocation)
            .doOnSubscribe { manager.checkLocationSettings(requireActivity()) }
            .subscribe {
                Log.d("lcoation", it.toString())
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }

    companion object {
        fun newInstance(): AboutFragment {
            val arguments = Bundle()
            val fragment = AboutFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}