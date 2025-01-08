package com.android.firebase

import android.app.Application
import com.android.firebase.dependenciesinjection.AppContainer
import com.android.firebase.dependenciesinjection.MahasiswaContainer

class MahasiswaApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate(){
        super.onCreate()
        container = MahasiswaContainer()
    }
}