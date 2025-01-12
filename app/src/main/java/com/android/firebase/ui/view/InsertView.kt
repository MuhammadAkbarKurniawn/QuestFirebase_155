package com.android.firebase.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.firebase.ui.viewmodel.FormState
import com.android.firebase.ui.viewmodel.HomeUiState
import com.android.firebase.ui.viewmodel.InsertViewModel
import com.android.firebase.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState // state untuk loading, sukses, error
    val uiEvent = viewModel.uiEvent // state untuk form dan validasi
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState) {
        when (uiState) {
            is FormState.Success -> {
                println(
                    "InsertMhsView: uiState is Formstate.Succes, navigate to home" + uiState.message
                )
            coroutineScope.launch {
                snackbarHostState.showSnackbar(uiState.message) // tampilkan snackbar
            }
                delay(700)

                onNavigate()

                viewModel.resetSnackBarMessage() // reset snackbar
        }
            is FormState.Error -> {
                coroutineScope.launch {
                snackbarHostState.showSnackbar(uiState.message)
                }
            }
            else -> Unit
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Tambah Mahasiswa") },
                navigationIcon = {
                    Button(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            InsertBodyMhs (
                uiState = uiEvent,
                HomeUiState = uiState,
                OnValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    viewModel.validateFields()
                    viewModel.insertMhs()
                    //onNavigate()
                }
            )
        }
    }
}