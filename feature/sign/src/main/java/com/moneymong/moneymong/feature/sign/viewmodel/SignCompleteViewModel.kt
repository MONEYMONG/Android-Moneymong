package com.moneymong.moneymong.feature.sign.viewmodel

import com.moneymong.moneymong.android.BaseViewModel
import com.moneymong.moneymong.feature.sign.sideeffect.SignCompleteSideEffect
import com.moneymong.moneymong.feature.sign.state.SignCompleteState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignCompleteViewModel @Inject constructor() :
    com.moneymong.moneymong.android.BaseViewModel<SignCompleteState, SignCompleteSideEffect>(SignCompleteState()) {

}