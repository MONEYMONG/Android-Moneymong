package com.moneymong.moneymong.data.datasource.version

interface VersionRemoteDataSource {
    suspend fun checkUpdate(version: String): Result<Unit>
}
