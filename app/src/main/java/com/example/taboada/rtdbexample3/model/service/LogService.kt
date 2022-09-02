package com.example.taboada.rtdbexample3.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}