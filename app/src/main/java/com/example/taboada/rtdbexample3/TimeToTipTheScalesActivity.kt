package com.example.taboada.rtdbexample3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@ExperimentalMaterialApi
class TimeToTipTheScalesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { TimeToTipTheScalesApp() }
    }
}