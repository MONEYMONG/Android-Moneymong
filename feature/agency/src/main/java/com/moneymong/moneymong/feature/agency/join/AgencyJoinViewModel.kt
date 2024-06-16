package com.moneymong.moneymong.feature.agency.join

import androidx.lifecycle.viewModelScope
import com.moneymong.moneymong.common.base.BaseViewModel
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

    fun checkInviteCode(agencyId: Long) = intent {
        viewModelScope.launch {
            agencyJoinUseCase(agencyId = agencyId, data = AgencyJoinRequest(state.inputCode))
                .onSuccess {
                    if (it.certified) {
                        saveAgencyIdUseCase(agencyId.toInt())
                        reduce {
                            state.copy(
                                isError = false,
                                codeAccess = true
                            )
                        }
                    } else {
                        reduce {
                            state.copy(
                                isError = true
                            )
                        }
                    }
                }
                .onFailure {
                    reduce {
                        state.copy(
                            visiblePopUpError = true,
                            errorPopUpMessage = it.message.toString()
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
            if (state.isError.not() && (length <= CODE_MAX_SIZE)) {
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