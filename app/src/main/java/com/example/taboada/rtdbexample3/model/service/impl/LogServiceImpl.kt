package com.example.taboada.rtdbexample3.model.service.impl

import android.util.Log
import com.example.taboada.rtdbexample3.model.service.LogService
import javax.inject.Inject

class LogServiceImpl @Inject constructor(): LogService {
    override fun logNonFatalCrash(throwable: Throwable) {
        Log.w("WARNING", throwable.toString())
    }
}