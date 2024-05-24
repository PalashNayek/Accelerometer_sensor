package com.palash.accelerometer_sensor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.palash.accelerometer_sensor.R
import com.palash.accelerometer_sensor.databinding.FragmentHomeBinding
import com.palash.accelerometer_sensor.view_model.SensorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private val sensorViewModel by viewModels<SensorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorViewModel.accelerometerData.observe(viewLifecycleOwner, Observer {
            binding.textViewX.text = it.first.toString()
            binding.textViewY.text = it.second.toString()
            binding.textViewZ.text = it.third.toString()
        })
    }

    override fun onResume() {
        super.onResume()
        sensorViewModel.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorViewModel.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}