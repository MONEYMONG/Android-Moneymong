package com.moneymong.moneymong.domain.usecase.member

import com.moneymong.moneymong.domain.base.BaseUseCase
import com.moneymong.moneymong.domain.repository.member.MemberRepository
import com.moneymong.moneymong.model.member.InvitationCodeResponse
import javax.inject.Inject

class MemberReInvitationCodeUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) : BaseUseCase<Long, Result<InvitationCodeResponse>>() {

    override suspend fun invoke(data: Long): Result<InvitationCodeResponse> {
        return memberRepository.reInvitationCode(data)
    }

}