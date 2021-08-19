package master.google.maps_practice

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import master.google.maps_practice.databinding.ActivityMainBinding
import master.google.maps_practice.utils.Constant
import master.google.maps_practice.utils.Constant.Companion.locationPermissionCode
import master.google.maps_practice.utils.TAG

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!hasLocationPermission())
            requestLocationPermission()
        else
            Log.d(TAG, "hasLocationPermission ..")

    }

    fun isLocationServicesOK(): Boolean {
        val available =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        when {
            available == ConnectionResult.SUCCESS -> {
                return true
            }
            GoogleApiAvailability.getInstance().isUserResolvableError(available) -> {
                val dialog = GoogleApiAvailability.getInstance()
                    .getErrorDialog(this, available, Constant.errorDialogPermission)
                dialog.show()
            }
            else -> Log.d(TAG, "isServicesOK : can not make map request")
        }
        return false
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.permission_needed))
                .setMessage(getString(R.string.location_permission_des))
                .setPositiveButton(
                    android.R.string.yes
                ) { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        locationPermissionCode
                    )
                }.setNegativeButton(
                    android.R.string.cancel
                ) { dialog, _ -> dialog.dismiss() }.create().show()
        else ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissionCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: location Permission Granted")
            } else
                Log.d(TAG, "onRequestPermissionsResult: location Permission Denied")
        } else
            Log.d(TAG, "onRequestPermissionsResult: request failed!")
    }
}