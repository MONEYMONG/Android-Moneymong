package com.moneymong.moneymong.data.di

import com.moneymong.moneymong.data.repository.agency.AgencyRepositoryImpl
import com.moneymong.moneymong.data.repository.ledger.LedgerRepositoryImpl
import com.moneymong.moneymong.data.repository.ledgerdetail.LedgerDetailRepositoryImpl
import com.moneymong.moneymong.data.repository.member.MemberRepositoryImpl
import com.moneymong.moneymong.data.repository.ocr.OCRRepositoryImpl
import com.moneymong.moneymong.data.repository.signup.UnivRepositoryImpl
import com.moneymong.moneymong.data.repository.token.TokenRepositoryImpl
import com.moneymong.moneymong.data.repository.user.UserRepositoryImpl
import com.moneymong.moneymong.data.repository.version.VersionRepositoryImpl
import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import com.moneymong.moneymong.domain.repository.ledger.LedgerRepository
import com.moneymong.moneymong.domain.repository.ledgerdetail.LedgerDetailRepository
import com.moneymong.moneymong.domain.repository.member.MemberRepository
import com.moneymong.moneymong.domain.repository.ocr.OCRRepository
import com.moneymong.moneymong.domain.repository.token.TokenRepository
import com.moneymong.moneymong.domain.repository.univ.UnivRepository
import com.moneymong.moneymong.domain.repository.user.UserRepository
import com.moneymong.moneymong.domain.repository.version.VersionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindUnivRepository(
        univRepositoryImpl: UnivRepositoryImpl
    ): UnivRepository

    @Singleton
    @Binds
    fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository

    @Binds
    fun bindAgencyRepository(
        agencyRepositoryImpl: AgencyRepositoryImpl
    ): AgencyRepository

    @Binds
    fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    fun bindOCRRepository(
        ocrRepositoryImpl: OCRRepositoryImpl
    ): OCRRepository

    @Binds
    fun bindLedgerRepository(
        ledgerRepositoryImpl: LedgerRepositoryImpl
    ): LedgerRepository

    @Binds
    fun bindLedgerDetailRepository(
        ledgerDetailRepositoryImpl: LedgerDetailRepositoryImpl
    ): LedgerDetailRepository

    @Binds
    fun bindMemberRepository(
        memberRepositoryImpl: MemberRepositoryImpl
    ): MemberRepository

    @Binds
    fun bindVersionRepository(
        versionRepositoryImpl: VersionRepositoryImpl
    ): VersionRepository
}