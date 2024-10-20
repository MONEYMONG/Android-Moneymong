package com.moneymong.moneymong.data.repository.version

import com.moneymong.moneymong.data.datasource.version.VersionRemoteDataSource
import com.moneymong.moneymong.domain.repository.version.VersionRepository
import javax.inject.Inject

class VersionRepositoryImpl @Inject constructor(
    private val versionRemoteDataSource: VersionRemoteDataSource
) : VersionRepository {

    override suspend fun checkUpdate(version: String): Result<String> {
        return versionRemoteDataSource.checkUpdate(version = version)
    }
}