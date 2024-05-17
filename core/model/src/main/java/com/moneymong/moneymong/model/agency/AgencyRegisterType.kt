package com.moneymong.moneymong.model.agency

enum class AgencyRegisterType {
    CLUB,
    COUNCIL;

    fun AgencyRegisterType.agencyRegisterTypeToString() = when (this) {
        CLUB -> "IN_SCHOOL_CLUB"
        COUNCIL -> "STUDENT_COUNCIL"
    }
}