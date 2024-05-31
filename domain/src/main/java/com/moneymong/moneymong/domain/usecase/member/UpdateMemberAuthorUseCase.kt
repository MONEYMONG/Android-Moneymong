package com.moneymong.moneymong.domain.usecase.member

import com.moneymong.moneymong.domain.repository.member.MemberRepository
import com.moneymong.moneymong.model.member.UpdateAuthorRequest
import javax.inject.Inject

class UpdateMemberAuthorUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(agencyId : Long, data: UpdateAuthorRequest): Result<Unit> {
        return memberRepository.updateMemberAuthor(agencyId , data)
    }
}