package com.moneymong.moneymong.domain.usecase.ledger

import com.moneymong.moneymong.domain.repository.ledger.LedgerRepository
import javax.inject.Inject

class FetchLedgerReportUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository
){
    suspend operator fun invoke(
        agencyId: Int,
        startYear: Int,
        startMonth: Int,
        endYear: Int,
        endMonth: Int
    ) = ledgerRepository.fetchLedgerReport(
            agencyId = agencyId,
            startYear = startYear,
            startMonth = startMonth,
            endYear = endYear,
            endMonth = endMonth
    )
}
