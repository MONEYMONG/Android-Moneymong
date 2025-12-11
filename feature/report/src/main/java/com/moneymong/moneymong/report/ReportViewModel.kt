package com.moneymong.moneymong.report

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.moneymong.moneymong.report.navigation.ReportArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    init {
        Log.d("heejik", ReportArgs(savedStateHandle).agencyId.toString())
    }
}