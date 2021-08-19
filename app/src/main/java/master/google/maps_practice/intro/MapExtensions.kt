package master.google.maps_practice.intro

import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.tasks.Tasks
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import master.google.maps_practice.utils.Constant.Companion.MAP_CAMERA_ZOOM
import master.google.maps_practice.utils.Constant.Companion.MAP_CAMERA_ZOOM_INT
import master.google.maps_practice.utils.Constant.Companion.TASK_AWAIT
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException



fun GoogleMap.moveCameraOnMap(latLng: LatLng?) {
    this.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_CAMERA_ZOOM))
}

fun GoogleMap.moveCameraOnMapBound(latLng: LatLngBounds?) {
    this.animateCamera(CameraUpdateFactory.newLatLngBounds(latLng, MAP_CAMERA_ZOOM_INT))
}

fun Fragment?.runOnUiThread(action: () -> Unit) {
    this ?: return
    if (!isAdded) return // Fragment not attached to an Activity
    activity?.runOnUiThread(action)
}

fun getAutocomplete(
    mPlacesClient: PlacesClient,
    constraint: CharSequence
): List<AutocompletePrediction> {
    var list = listOf<AutocompletePrediction>()
    val token = AutocompleteSessionToken.newInstance()
    val request = FindAutocompletePredictionsRequest.builder().setTypeFilter(TypeFilter.CITIES)
        .setSessionToken(token).setQuery(constraint.toString()).build()
    val prediction = mPlacesClient.findAutocompletePredictions(request)
    try {
        Tasks.await(prediction, TASK_AWAIT, TimeUnit.SECONDS)
    } catch (e: ExecutionException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    } catch (e: TimeoutException) {
        e.printStackTrace()
    }

    if (prediction.isSuccessful) {
        val findAutocompletePredictionsResponse = prediction.result
        findAutocompletePredictionsResponse?.let {
            list = findAutocompletePredictionsResponse.autocompletePredictions
        }
        return list
    }
    return list
}