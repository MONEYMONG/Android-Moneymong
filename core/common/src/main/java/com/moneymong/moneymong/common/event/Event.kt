package com.moneymong.moneymong.common.event

enum class Event(val eventName: String, val desc: String) {
    OPERATION_COST_CLICK(
        eventName = "operation_cost_click",
        desc = "운영비 등록하기 버튼 클릭",
    ),
    LEDGER_CLICK(
        eventName = "ledger_click",
        desc = "소속 장부 확인 버튼 클릭",
    ),
    OCR_REGISTER_CLICK(
        eventName = "ocr_register_click",
        desc = "등록하기 버튼 클릭",
    ),
    OCR_MODIFY_CLICK(
        eventName = "ocr_modify_click",
        desc = "수정하기 버튼 클릭",
    ),
    OCR_MODIFY_TO_REGISTER_CLICK(
        eventName = "ocr_modify_to_register_click",
        desc = "등록하기 버튼 클릭",
    ),
    PLUS_CLICK(
        eventName = "plus_click",
        desc = "+ 플로팅 버튼 클릭",
    ),
    HAND_CLICK(
        eventName = "hand_click",
        desc = "수동 등록 플로팅 버튼 클릭",
    ),
    OCR_CLICK(
        eventName = "ocr_click",
        desc = "OCR 등록 플로팅 버튼 클릭",
    ),
}
