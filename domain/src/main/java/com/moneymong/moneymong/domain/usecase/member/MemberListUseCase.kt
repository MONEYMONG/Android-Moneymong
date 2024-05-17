package com.moneymong.moneymong.domain.usecase.member

import com.moneymong.moneymong.domain.base.BaseUseCase
import com.moneymong.moneymong.domain.repository.member.MemberRepository
import com.moneymong.moneymong.model.member.MemberListResponse
import javax.inject.Inject

class MemberListUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) : BaseUseCase<Long, Result<MemberListResponse>>() {

    override suspend fun invoke(data: Long): Result<MemberListResponse> {
        return memberRepository.getMemberLists(data)
    }
}