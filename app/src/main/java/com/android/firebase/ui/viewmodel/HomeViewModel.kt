package com.android.firebase.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.firebase.model.Mahasiswa
import com.android.firebase.repository.MahasiswaRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed class HomeUiState {
    data class Success(val mahasiswa: List<Mahasiswa>) : HomeUiState()
    data class error(val exception: Throwable) : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModel (private val mhs : MahasiswaRepository):ViewModel(){

    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    fun getMhs() {
        viewModelScope.launch {
            mhs.getAllMahasiswa()
                .onStart {
                    mhsUIState = HomeUiState.Loading
                }
                .catch {
                    mhsUIState = HomeUiState.error(it)
                }
                .collect{
                    mhsUIState = if (it.isEmpty()){
                        HomeUiState.error(Exception("Belum ada daftar Mahasiswa"))
                    }else{
                        HomeUiState.Success(it)
                    }
                }
        }
    }

}