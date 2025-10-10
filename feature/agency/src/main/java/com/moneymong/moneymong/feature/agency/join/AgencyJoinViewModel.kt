package com.moneymong.moneymong.feature.agency.join

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import com.moneymong.moneymong.android.BaseViewModel
import com.moneymong.moneymong.domain.usecase.agency.AgencyJoinUseCase
import com.moneymong.moneymong.domain.usecase.agency.SaveAgencyIdUseCase
import com.moneymong.moneymong.feature.agency.join.component.CODE_MAX_SIZE
import com.moneymong.moneymong.model.agency.AgencyJoinRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class AgencyJoinViewModel @Inject constructor(
    private val agencyJoinUseCase: AgencyJoinUseCase,
    private val saveAgencyIdUseCase: SaveAgencyIdUseCase
) : BaseViewModel<AgencyJoinState, AgencyJoinSideEffect>(AgencyJoinState()) {

    fun findLedgerByInviteCode() = intent {
        viewModelScope.launch {
            agencyJoinUseCase(data = AgencyJoinRequest(state.inputCode))
                .onSuccess { response ->
                    reduce {
                        state.copy(
                            isError = response.certified.not(),
                            codeAccess = response.certified
                        )
                    }
                    if (response.certified) {
                        saveAgencyIdUseCase(agencyId = response.agencyId)
                    }
                }.onFailure { exception ->
                    reduce {
                        state.copy(
                            isError = true,
                            snackBarMessage = exception.message ?: "잘못된 초대코드입니다."
                        )
                    }
                }
        }
    }


    fun onIsErrorChanged(newIsError: Boolean) = intent {
        reduce {
            state.copy(isError = newIsError)
        }
    }

    fun changeInputNumber(input: String) = intent intent@{
        with(input) {
            if (isDigitsOnly() && state.isError.not() && (length <= CODE_MAX_SIZE)) {
                this@intent.reduce {
                    state.copy(inputCode = input)
                }
            }
        }
    }

    fun resetNumbers() = intent {
        reduce {
            state.copy(inputCode = "")
        }
    }

    fun visiblePopUpErrorChanged(visibleError: Boolean) = intent {
        reduce {
            state.copy(visiblePopUpError = visibleError)
        }
    }
}