package com.moneymong.moneymong.domain.usecase.member

import com.moneymong.moneymong.domain.repository.member.MemberRepository
import com.moneymong.moneymong.model.member.MemberBlockRequest
import javax.inject.Inject

class MemberBlockUseCase
    @Inject
    constructor(
        private val memberRepository: MemberRepository,
    ) {
        suspend operator fun invoke(
            agencyId: Long,
            data: MemberBlockRequest,
        ): Result<Unit> {
            return memberRepository.blockMemberAuthor(agencyId, data)
        }
    }
