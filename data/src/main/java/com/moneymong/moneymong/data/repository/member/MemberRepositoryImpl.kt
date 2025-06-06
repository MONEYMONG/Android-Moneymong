package com.moneymong.moneymong.data.repository.member

import com.moneymong.moneymong.data.datasource.member.MemberRemoteDataSource
import com.moneymong.moneymong.domain.repository.member.MemberRepository
import com.moneymong.moneymong.model.member.InvitationCodeResponse
import com.moneymong.moneymong.model.member.MemberBlockRequest
import com.moneymong.moneymong.model.member.MemberListResponse
import com.moneymong.moneymong.model.member.UpdateAuthorRequest
import javax.inject.Inject

class MemberRepositoryImpl
    @Inject
    constructor(
        private val memberRemoteDataSource: MemberRemoteDataSource,
    ) : MemberRepository {
        override suspend fun getInvitationCode(agencyId: Long): Result<InvitationCodeResponse> {
            return memberRemoteDataSource.getInvitationCode(agencyId)
        }

        override suspend fun reInvitationCode(agencyId: Long): Result<InvitationCodeResponse> {
            return memberRemoteDataSource.reInvitationCode(agencyId)
        }

        override suspend fun getMemberLists(agencyId: Long): Result<MemberListResponse> {
            return memberRemoteDataSource.getMemberLists(agencyId)
        }

        override suspend fun updateMemberAuthor(
            agencyId: Long,
            data: UpdateAuthorRequest,
        ): Result<Unit> {
            return memberRemoteDataSource.updateMemberAuthor(agencyId, data)
        }

        override suspend fun blockMemberAuthor(
            agencyId: Long,
            data: MemberBlockRequest,
        ): Result<Unit> {
            return memberRemoteDataSource.blockMemberAuthor(agencyId, data)
        }

        override suspend fun deleteAgency(agencyId: Int): Result<Unit> {
            return memberRemoteDataSource.deleteAgency(agencyId)
        }
    }
