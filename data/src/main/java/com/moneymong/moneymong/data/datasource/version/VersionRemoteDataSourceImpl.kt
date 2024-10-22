package com.moneymong.moneymong.data.datasource.version

import com.moneymong.moneymong.model.version.VersionRequest
import com.moneymong.moneymong.network.api.VersionApi
import javax.inject.Inject

class VersionRemoteDataSourceImpl @Inject constructor(
    private val versionApi: VersionApi
) : VersionRemoteDataSource {
    override suspend fun checkUpdate(version: String): Result<String> {
        return versionApi.checkUpdate(VersionRequest(version = version))
    }
}