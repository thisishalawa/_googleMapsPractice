package master.google.maps_practice.auto_complete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import master.google.maps_practice.databinding.FragmentAutoCompleteBinding

class AutoCompleteFragment : Fragment() {

    private var _binding: FragmentAutoCompleteBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutoCompleteBinding.inflate(inflater, container, false)
        return binding.root
    }

}