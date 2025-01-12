package com.android.firebase.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.android.firebase.MahasiswaApplications

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiMhs().container.mahasiswaRepository) }
        initializer { InsertViewModel(aplikasiMhs().container.mahasiswaRepository) }
        //initializer { DetailViewModel(aplikasiKontak().container.mahasiswaRepository) }
        //initializer { UpdateViewModel(aplikasiKontak().container.mahasiswaRepository) }
    }

    fun CreationExtras.aplikasiMhs(): MahasiswaApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as MahasiswaApplications)
}