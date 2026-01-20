package com.moneymong.moneymong.domain.usecase.ledger

import com.moneymong.moneymong.domain.repository.ledger.LedgerRepository
import javax.inject.Inject

class FetchLedgerReportUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository
){
    suspend operator fun invoke(
        agencyId: Int,
        year: Int,
        month: Int,
    ) = ledgerRepository.fetchLedgerReport(
            agencyId = agencyId,
            startYear = year,
            startMonth = month,
            endYear = year,
            endMonth = month
    )
}