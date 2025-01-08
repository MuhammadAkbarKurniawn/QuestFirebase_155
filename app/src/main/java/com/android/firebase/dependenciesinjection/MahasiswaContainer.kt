package com.android.firebase.dependenciesinjection

import com.android.firebase.repository.MahasiswaRepository
import com.android.firebase.repository.NetworkMahasiswaRepository
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer {
    val mahasiswaRepository: MahasiswaRepository
}

class MahasiswaContainer : AppContainer {
    private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()

    override val  mahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(firebase)
    }
}