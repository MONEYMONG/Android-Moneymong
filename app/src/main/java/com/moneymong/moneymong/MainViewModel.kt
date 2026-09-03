package com.moneymong.moneymong

import android.util.Log
import com.moneymong.moneymong.android.BaseViewModel
import com.moneymong.moneymong.common.error.HttpError
import com.moneymong.moneymong.domain.usecase.agency.AgencyJoinUseCase
import com.moneymong.moneymong.domain.usecase.agency.SaveAgencyIdUseCase
import com.moneymong.moneymong.domain.usecase.version.CheckVersionUpdateUseCase
import com.moneymong.moneymong.invite.InviteDeepLink
import com.moneymong.moneymong.model.agency.AgencyJoinRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    val checkVersionUpdateUseCase: CheckVersionUpdateUseCase,
    private val agencyJoinUseCase: AgencyJoinUseCase,
    private val saveAgencyIdUseCase: SaveAgencyIdUseCase,
) : BaseViewModel<MainState, MainSideEffect>(MainState()) {

    fun checkShouldUpdate(version: String) = intent {
        checkVersionUpdateUseCase(version = version)
            .onSuccess { reduce { state.copy(shouldUpdate = false) } }
            .onFailure { reduce { state.copy(shouldUpdate = it.message?.contains("업데이트") == true) } }
    }

    fun joinByInviteCode(code: String) = intent {
        val linkedAgencyId = state.pendingInvite?.agencyId

        agencyJoinUseCase(AgencyJoinRequest(code))
            .onSuccess { response ->
                if (response.certified) saveAgencyIdUseCase(response.agencyId)
                reduce { state.copy(pendingInvite = null, inviteJoinFinished = true) }
            }.onFailure { e ->
                val alreadyJoined =
                    (e as? HttpError.BadRequestError)?.errorCode == ALREADY_JOINED_ERROR_CODE
                Log.d(
                    "heejik",
                    "joinByInviteCode: alreadyJoined = $alreadyJoined, linkedAgencyId = $linkedAgencyId, (e as? HttpError.BadRequestError)?.errorCode = ${(e as? HttpError.BadRequestError)?.errorCode}"
                )
                if (alreadyJoined && linkedAgencyId != null) {
                    saveAgencyIdUseCase(linkedAgencyId)
                }

                reduce {
                    state.copy(
                        pendingInvite = null,
                        inviteJoinFinished = true,
                        inviteJoinErrorMessage = e.message ?: "초대 링크로 가입하지 못했어요",
                    )
                }
            }

    }

    fun onInviteJoinErrorConfirmed() = intent {
        reduce { state.copy(inviteJoinErrorMessage = "") }
    }

    fun onInviteCodeReceived(invite: InviteDeepLink) = intent {
        reduce { state.copy(pendingInvite = invite) }
    }

    private companion object {
        private const val ALREADY_JOINED_ERROR_CODE = "AGENCY-USER-004"
    }
}