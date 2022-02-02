package com.nkuskov.epam_hw.presentation.common

class Enums {
    companion object {
        enum class CheckPermissionResult {
            GRANTED,
            DENIED,
            NEED_TO_REQUEST,
            NEED_TO_EXPLAIN,
            DONT_NEED_TO_REQUEST
        }
    }
}