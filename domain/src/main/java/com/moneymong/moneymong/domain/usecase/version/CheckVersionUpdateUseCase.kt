package com.moneymong.moneymong.domain.usecase.version

import javax.inject.Inject

class CheckVersionUpdateUseCase @Inject constructor() {

    suspend operator fun invoke(version: String): Result<String> {
//        return Result.success("1.1.0")
        return Result.failure(Throwable("1.1.0"))
    }
}