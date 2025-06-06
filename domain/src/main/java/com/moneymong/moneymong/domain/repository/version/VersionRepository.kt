package com.moneymong.moneymong.domain.repository.version

interface VersionRepository {
    suspend fun checkUpdate(version: String): Result<Unit>
}
