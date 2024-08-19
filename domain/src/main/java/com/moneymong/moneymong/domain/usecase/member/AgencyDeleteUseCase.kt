package com.moneymong.moneymong.domain.usecase.member

import com.moneymong.moneymong.domain.repository.member.MemberRepository
import javax.inject.Inject

class DeleteAgencyUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(agencyId: Int) : Result<Unit> {
        return memberRepository.deleteAgency(agencyId)
    }
}