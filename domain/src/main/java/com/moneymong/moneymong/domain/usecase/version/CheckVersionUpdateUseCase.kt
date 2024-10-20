package com.moneymong.moneymong.domain.usecase.version

import com.moneymong.moneymong.domain.repository.version.VersionRepository
import javax.inject.Inject

class CheckVersionUpdateUseCase @Inject constructor(
    private val versionRepository: VersionRepository
) {

    suspend operator fun invoke(version: String): Result<String> {
        return versionRepository.checkUpdate(version = version)
    }
}