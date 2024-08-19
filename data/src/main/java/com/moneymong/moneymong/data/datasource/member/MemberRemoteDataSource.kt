package com.moneymong.moneymong.data.datasource.member

import com.moneymong.moneymong.model.member.MemberBlockRequest
import com.moneymong.moneymong.model.member.UpdateAuthorRequest
import com.moneymong.moneymong.model.member.InvitationCodeResponse
import com.moneymong.moneymong.model.member.MemberListResponse

interface MemberRemoteDataSource {
    suspend fun getInvitationCode(agencyId: Long): Result<InvitationCodeResponse>
    suspend fun reInvitationCode(agencyId: Long): Result<InvitationCodeResponse>
    suspend fun getMemberLists(agencyId: Long): Result<MemberListResponse>
    suspend fun updateMemberAuthor(agencyId : Long, data : UpdateAuthorRequest) : Result<Unit>
    suspend fun blockMemberAuthor(agencyId: Long, data : MemberBlockRequest) : Result<Unit>
    suspend fun deleteAgency(agencyId: Int) : Result<Unit>

}