package com.huytran.rerm.constant

class AppConstants {

    companion object {

        const val MODE_OF_PAYMENT_CASH = 1
    }

    enum class NotificationType(val raw: Int) {
        MESSAGE_TYPE_MESSAGE(1),
        MESSAGE_TYPE_NOTIFICATION(2),
        MESSAGE_TYPE_VIDEO_CALL(3),
        MESSAGE_TYPE_CHAT(4)
    }

}