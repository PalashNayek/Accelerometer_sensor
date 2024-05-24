package com.palash.accelerometer_sensor.repository

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class SensorRepository @Inject constructor(private val sensorManager: SensorManager) {
    private val _accelerometerData = MutableLiveData<Triple<Float, Float, Float>>()
    val accelerometerData: LiveData<Triple<Float, Float, Float>> = _accelerometerData

    private val accelerometerListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    _accelerometerData.postValue(Triple(it.values[0], it.values[1], it.values[2]))
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // No action needed
        }
    }

    fun startListening() {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(accelerometerListener)
    }
}