package com.moneymong.moneymong.domain.usecase.version

import javax.inject.Inject

class FetchMinimumUpdateVersionUseCase @Inject constructor() {

    suspend operator fun invoke(): Result<String> {
        return Result.success("1.1.0")
    }
}