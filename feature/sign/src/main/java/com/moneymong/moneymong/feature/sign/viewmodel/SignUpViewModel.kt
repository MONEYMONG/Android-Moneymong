package com.moneymong.moneymong.feature.sign.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewModelScope
import com.moneymong.moneymong.android.BaseViewModel
import com.moneymong.moneymong.domain.usecase.agency.RegisterAgencyUseCase
import com.moneymong.moneymong.domain.usecase.signup.SchoolInfoUseCase
import com.moneymong.moneymong.domain.usecase.university.CreateUniversityUseCase
import com.moneymong.moneymong.feature.sign.sideeffect.SignUpSideEffect
import com.moneymong.moneymong.feature.sign.state.SignUpState
import com.moneymong.moneymong.feature.sign.util.AgencyType
import com.moneymong.moneymong.model.agency.AgencyRegisterRequest
import com.moneymong.moneymong.model.sign.UnivRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUniversityUseCase: CreateUniversityUseCase,
    private val registerAgencyUseCase: RegisterAgencyUseCase,
    private val schoolInfoUseCase: SchoolInfoUseCase,
) : com.moneymong.moneymong.android.BaseViewModel<SignUpState, SignUpSideEffect>(SignUpState()) {
    fun createUniv(universityName: String, grade: Int) = intent {
        val body = UnivRequest(universityName, grade)
        createUniversityUseCase(body)
            .onSuccess {
                storeSchoolInfoProvided(true)
                reduce {
                    state.copy(
                        isUnivCreated = true
                    )
                }
            }
            .onFailure {
                reduce {
                    state.copy(
                        visiblePopUpError = true,
                        popUpErrorMessage = it.message.toString()
                    )
                }
            }
    }

    fun registerAgency() = intent {
        registerAgencyUseCase(AgencyRegisterRequest(state.agencyName.text, AgencyType.GENERAL.text))
            .onSuccess {
                reduce {
                    state.copy(
                        isAgencyCreated = true
                    )
                }
            }
            .onFailure {
                reduce {
                    state.copy(
                        visiblePopUpError = true,
                        popUpErrorMessage = it.message.toString()
                    )
                }
            }

    }


    private fun storeSchoolInfoProvided(infoExist: Boolean) {
        viewModelScope.launch {
            schoolInfoUseCase.invoke(infoExist)
        }
    }

    fun visiblePopUpErrorChanged(visiblePopUpError: Boolean) = intent {
        reduce {
            state.copy(
                visiblePopUpError = visiblePopUpError,
            )
        }
    }

    fun visibleErrorChanged(visibleError: Boolean) = intent {
        reduce {
            state.copy(
                visibleError = visibleError,
            )
        }
    }

    fun onChangeAgencyType(agencyType: AgencyType) = intent {
        reduce {
            state.copy(
                agencyType = agencyType,
                MDSSelected = true,
            )
        }
    }

    fun updateAgencyName(agencyName: TextFieldValue) = intent {
        reduce {
            state.copy(
                agencyName = agencyName,
                isButtonVisible = agencyName.text != "" && state.agencyType != null
            )
        }
    }

    fun updateEdittextFocused(focusState: Boolean) = intent {
        reduce {
            state.copy(
                editTextFocused = focusState
            )
        }
    }

    fun changeInvitedType(invited: Boolean) = intent {
        reduce {
            state.copy(
                isInvited = invited
            )
        }
    }

    fun changeButtonCornerShape(cornerShape: Dp) = intent {
        reduce {
            state.copy(
                buttonCornerShape = cornerShape
            )
        }
    }
}