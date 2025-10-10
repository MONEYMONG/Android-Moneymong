package com.example.member


import com.moneymong.moneymong.android.State
import com.moneymong.moneymong.model.member.AgencyUser

data class MemberState(
    val onCopyClick: Boolean = false,
    val onReissueChange: Boolean = false,
    val visibleBottomSheet: Boolean = false,
    val vertClickedUserId: Long = 0L,
    val isStaffChecked: Boolean = false,
    val isMemberChecked: Boolean = false,
    val roleChanged: Boolean = false,
    val showDialog: Boolean = false,
    val invitationCode: String = "",
    val memberList: List<AgencyUser> = emptyList(),
    val memberMyInfoId: Long = 0,
    val memberMyInfo: AgencyUser = AgencyUser(
        id = 0L,
        userId = 0,
        nickname = "",
        agencyUserRole = ""
    ),
    val filteredMemberList: List<AgencyUser> = emptyList(),
    val visibleError : Boolean = false,
    val errorMessage : String = "",
    val visiblePopUpError : Boolean = false,
    val errorPopUpMessage : String = "",
    val inviteCodeError : Boolean = false,
    val isUserAuthor: String = "",
    val agencyId: Int = 0,
    val isBlockedUser : Boolean = false,
    val deleteAgency : Boolean = false,

) : State