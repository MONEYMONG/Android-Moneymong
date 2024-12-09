package com.moneymong.moneymong.feature.sign.viewmodel

import android.view.View
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneymong.moneymong.common.base.BaseViewModel
import com.moneymong.moneymong.domain.usecase.agency.RegisterAgencyUseCase
import com.moneymong.moneymong.domain.usecase.signup.SchoolInfoUseCase
import com.moneymong.moneymong.domain.usecase.university.CreateUniversityUseCase
import com.moneymong.moneymong.domain.usecase.university.SearchUniversityUseCase
import com.moneymong.moneymong.feature.sign.sideeffect.SignUpUniversitySideEffect
import com.moneymong.moneymong.feature.sign.state.SignUpUniversityState
import com.moneymong.moneymong.feature.sign.util.AgencyType
import com.moneymong.moneymong.model.agency.AgencyRegisterRequest
import com.moneymong.moneymong.model.sign.UnivRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class SignUpUniversityViewModel @Inject constructor(
    private val createUniversityUseCase: CreateUniversityUseCase,
    private val registerAgencyUseCase : RegisterAgencyUseCase,
    private val schoolInfoUseCase : SchoolInfoUseCase,
    private val searchUniversityUseCase: SearchUniversityUseCase
) : BaseViewModel<SignUpUniversityState, SignUpUniversitySideEffect>(SignUpUniversityState()){

    fun searchUniv(searchQuery: String) = intent {
        searchUniversityUseCase(searchQuery)
            .onSuccess {
                reduce {
                    state.copy(
                        universityResponse = it
                    )
                }
            }.onFailure {
                reduce {
                    state.copy(
                        visibleError = true,
                        errorMessage = it.message.toString()
                    )
                }
            }
    }


    fun createUniv(universityName: String?, grade: Int?) = intent {
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

     fun registerAgency(agencyName: String, agencyType: AgencyType) = intent{
        registerAgencyUseCase(AgencyRegisterRequest(agencyName, agencyType.text))
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

    private fun storeSchoolInfoProvided(infoExist : Boolean ){
        viewModelScope.launch {
            schoolInfoUseCase.invoke(infoExist)
        }
    }

    fun isSelectedChanged(isSelected: Boolean) = intent {
        reduce {
            state.copy(
                isSelected = isSelected
            )
        }
    }

    fun selectedUnivChanged(selectedUniv: String) = intent {
        reduce {
            state.copy(
                selectedUniv = selectedUniv
            )
        }
    }

    @OptIn(OrbitExperimental::class)
    fun textValueChanged(textValue: TextFieldValue) = blockingIntent {
        reduce {
            state.copy(
                textValue = textValue
            )
        }
    }

    fun isFilledChanged(isFilled: Boolean) = intent {
        reduce {
            state.copy(
                isFilled = isFilled
            )
        }
    }

    fun isListVisibleChanged(isListVisible: Boolean) = intent {
        reduce {
            state.copy(
                isListVisible = isListVisible
            )
        }
    }

    fun isItemSelectedChanged(isItemSelected: Boolean) = intent {
        reduce {
            state.copy(
                isItemSelected = isItemSelected
            )
        }
    }

    fun isButtonVisibleChanged(isButtonVisible: Boolean) = intent {
        reduce {
            state.copy(
                isButtonVisible = isButtonVisible
            )
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
}