package com.example.member

import android.util.Log
import com.moneymong.moneymong.common.base.BaseViewModel
import com.moneymong.moneymong.domain.usecase.agency.FetchAgencyIdUseCase
import com.moneymong.moneymong.domain.usecase.member.DeleteAgencyUseCase
import com.moneymong.moneymong.domain.usecase.member.MemberBlockUseCase
import com.moneymong.moneymong.domain.usecase.member.MemberInvitationCodeUseCase
import com.moneymong.moneymong.domain.usecase.member.MemberListUseCase
import com.moneymong.moneymong.domain.usecase.member.MemberReInvitationCodeUseCase
import com.moneymong.moneymong.domain.usecase.member.UpdateMemberAuthorUseCase
import com.moneymong.moneymong.domain.usecase.user.FetchMyInfoUseCase
import com.moneymong.moneymong.model.agency.MyAgencyResponse
import com.moneymong.moneymong.model.member.AgencyUser
import com.moneymong.moneymong.model.member.MemberBlockRequest
import com.moneymong.moneymong.model.member.UpdateAuthorRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val memberInvitationCodeUseCase: MemberInvitationCodeUseCase,
    private val memberReInvitationCodeUseCase: MemberReInvitationCodeUseCase,
    private val memberListUseCase: MemberListUseCase,
    private val fetchMyInfoUseCase: FetchMyInfoUseCase,
    private val updateMemberAuthorUseCase: UpdateMemberAuthorUseCase,
    private val memberBlockUseCase: MemberBlockUseCase,
    private val fetchAgencyIdUseCase: FetchAgencyIdUseCase,
    private val deleteAgencyUseCase: DeleteAgencyUseCase
) : BaseViewModel<MemberState, MemberSideEffect>(MemberState()) {

    init {
        fetchAgencyId()
    }

    @OptIn(OrbitExperimental::class)
    fun fetchAgencyId() = blockingIntent {
        val agencyId = fetchAgencyIdUseCase()
        reduce { state.copy(agencyId = agencyId) }
    }

    fun updateAgencyId(agencyId: Int) = intent {
        reduce { state.copy(agencyId = agencyId) }
    }

    fun onVertClickChanged(vertClick: Boolean) = intent {
        reduce {
            state.copy(
                visibleBottomSheet = vertClick
            )
        }
    }

    fun vertClickedUserIdChanged(userId: Long) = intent {
        reduce {
            state.copy(
                vertClickedUserId = userId
            )
        }
    }

    fun onCopyClickChanged(onCopyClick: Boolean) = intent {
        reduce {
            state.copy(
                onCopyClick = onCopyClick
            )
        }
    }

    fun onReissueChanged(onReissueChange: Boolean) = intent {
        reduce {
            state.copy(
                onReissueChange = onReissueChange
            )
        }
    }

    fun onShowDialogChanged(showDialog: Boolean) = intent {
        reduce {
            state.copy(
                showDialog = showDialog
            )
        }
    }

    fun isStaffCheckedChanged(isStaffChecked: Boolean) = intent {
        reduce {
            state.copy(
                isStaffChecked = isStaffChecked
            )
        }
    }

    fun isMemberCheckedChanged(isMemberChecked: Boolean) = intent {
        reduce {
            state.copy(
                isMemberChecked = isMemberChecked
            )
        }
    }

    fun isRoleChanged(roleChanged: Boolean) = intent {
        reduce {
            state.copy(
                roleChanged = roleChanged
            )
        }
    }

    fun memberMyInfoChanged(id: Long, userId: Long, nickname: String, agencyUserRole: String) =
        intent {
            reduce {
                state.copy(
                    memberMyInfo = AgencyUser(id, userId, nickname, agencyUserRole)
                )
            }
        }

    fun updateFilteredMemberList(memberMyInfoId: Long) = intent {
        val updatedFilteredMemberList =
            state.memberList.filterNot { it.userId == memberMyInfoId }

        reduce {
            state.copy(filteredMemberList = updatedFilteredMemberList)
        }
    }

    fun visibleErrorChanged(visibleError : Boolean) = intent{
        reduce {
            state.copy(
                visibleError = visibleError,
            )
        }
    }

    fun visiblePopUpErrorChanged(visiblePopUpError : Boolean) = intent{
        reduce {
            state.copy(
                visiblePopUpError = visiblePopUpError,
            )
        }
    }

    fun isBlockedUser(isBlockedUser : Boolean) = intent{
        reduce{
            state.copy(
                isBlockedUser = isBlockedUser
            )
        }
    }


    fun getInvitationCode(agencyId: Long) = intent {
        memberInvitationCodeUseCase.invoke(agencyId)
            .onSuccess {
                reduce {
                    state.copy(
                        invitationCode = it.code
                    )
                }
                Log.d("invitationCode", state.invitationCode)

            }.onFailure {
                reduce{
                    state.copy(
                        visibleError = true,
                        errorMessage = it.message.toString(),
                        inviteCodeError = true
                    )
                }
            }
    }

    fun getReInvitationCode(agencyId: Long) = intent {
        memberReInvitationCodeUseCase.invoke(agencyId)
            .onSuccess {
                reduce {
                    state.copy(
                        invitationCode = it.code,
                        onReissueChange = true,
                    )
                }
            }
            .onFailure {
                reduce{
                    state.copy(
                        visibleError = true,
                        errorMessage = it.message.toString(),
                        //reInviteCodeError = true

                    )
                }
            }
    }

    fun getMemberLists(agencyId: Long) = intent {
        memberListUseCase.invoke(agencyId)
            .onSuccess {
                reduce {
                    state.copy(
                        memberList = it.agencyUsers
                    )
                }
            }
            .onFailure {
                if(it.message == "소속에서 강제퇴장된 유저입니다."){
                    reduce{
                        state.copy(
                           isBlockedUser = true
                        )
                    }
                }
                reduce{
                    state.copy(
                        visibleError = true,
                        errorMessage = it.message.toString(),
                    )
                }
            }
    }

    fun getMyInfo() = intent {
        fetchMyInfoUseCase.invoke()
            .onSuccess {
                reduce {
                    state.copy(
                        memberMyInfoId = it.id
                    )
                }
            }
            .onFailure {
                reduce{
                    state.copy(
                        visibleError = true,
                        errorMessage = it.message.toString(),
                        //myInfoError = true
                    )
                }
            }
    }

    fun updateMemberAuthor(agencyId: Long, role: String, userId: Long) = intent {
        updateMemberAuthorUseCase(agencyId, UpdateAuthorRequest(role, userId))
            .onSuccess {
                updateFilteredMemberList(userId, role)
                updateMemberList(userId, role)
            }
            .onFailure {
                reduce{
                    state.copy(
                        visiblePopUpError = true,
                        errorPopUpMessage = it.message.toString()
                    )
                }
            }
    }


    fun blockMemberAuthor(agencyId: Long, userId: Long) = intent {
        memberBlockUseCase(agencyId, MemberBlockRequest(userId))
            .onSuccess {
                updateFilteredMemberListByBlock(userId)
                updateMemberListByBlock(userId)
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


    fun deleteAgency(
        agencyId: Int,
        agencyList: List<MyAgencyResponse>,
        onClickItem: (agencyId: Int) -> Unit,
        changeAgencyList: (agencyList: List<MyAgencyResponse>) -> Unit
    ) = intent {
        deleteAgencyUseCase.invoke(agencyId)
            .onSuccess {
                Log.d("deleteAgency${agencyId}", it.toString())
                val filteredList = agencyList.filter { it.id != agencyId }
                if (filteredList.isNotEmpty()) {
                    val randomAgency = filteredList.random()
                    onClickItem(randomAgency.id)
                    changeAgencyList(filteredList)
                } else {
                    changeAgencyList(emptyList())
                }
                reduce {
                    state.copy(
                        deleteAgency = false
                    )
                }
            }.onFailure {
                reduce {
                    state.copy(
                        deleteAgency = false,
                        visiblePopUpError = true,
                        errorPopUpMessage = it.message.toString()
                    )
                }
            }
    }


    fun deleteAgencyBtnClicked(deleteAgencyBtnClicked : Boolean) = intent {
        reduce {
            state.copy(
                deleteAgency = deleteAgencyBtnClicked
            )
        }
    }

    private fun updateFilteredMemberListByBlock(userId: Long) = intent {
        val currentMemberList = state.filteredMemberList
        val updateBlockedMemberList = currentMemberList.filterNot { member ->
            member.userId == userId
        }
        reduce {
            state.copy(
                filteredMemberList = updateBlockedMemberList,
            )
        }
    }

    private fun updateMemberListByBlock(userId: Long) = intent {
        val currentMemberList = state.memberList
        val updateBlockedMemberList = currentMemberList.filterNot { member ->
            member.userId == userId
        }
        reduce {
            state.copy(
                memberList = updateBlockedMemberList,
            )
        }
    }

    private fun updateFilteredMemberList(userId: Long, role: String) = intent {
        val currentFilteredMemberList = state.filteredMemberList
        val updatedFilteredMemberList = currentFilteredMemberList.map { member ->
            if (member.userId == userId) {
                member.copy(agencyUserRole = role)
            } else {
                member
            }
        }
        reduce {
            state.copy(
                filteredMemberList = updatedFilteredMemberList,
                roleChanged = true
            )
        }
    }

    private fun updateMemberList(userId: Long, role: String) = intent {
        val currentMemberList = state.memberList
        val updatedMemberList = currentMemberList.map { member ->
            if (member.userId == userId) {
                member.copy(agencyUserRole = role)
            } else {
                member
            }
        }
        reduce {
            state.copy(
                memberList = updatedMemberList,
            )
        }
    }
}