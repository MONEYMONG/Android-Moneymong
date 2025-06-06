package com.moneymong.moneymong.domain.usecase.member

import com.moneymong.moneymong.domain.repository.member.MemberRepository
import com.moneymong.moneymong.model.member.InvitationCodeResponse
import javax.inject.Inject

class MemberReInvitationCodeUseCase
    @Inject
    constructor(
        private val memberRepository: MemberRepository,
    ) {
        suspend operator fun invoke(agencyId: Long): Result<InvitationCodeResponse> {
            return memberRepository.reInvitationCode(agencyId)
        }
    }
