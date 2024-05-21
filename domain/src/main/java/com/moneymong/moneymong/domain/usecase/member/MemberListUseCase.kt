package com.moneymong.moneymong.domain.usecase.member

import com.moneymong.moneymong.domain.repository.member.MemberRepository
import com.moneymong.moneymong.model.member.MemberListResponse
import javax.inject.Inject

class MemberListUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {

    suspend operator fun invoke(agencyId: Long): Result<MemberListResponse> {
        return memberRepository.getMemberLists(agencyId)
    }
}