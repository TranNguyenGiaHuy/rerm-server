package com.huytran.rerm.constant

class AppConstants {

    companion object {

        const val MODE_OF_PAYMENT_CASH = 1
    }

    enum class NotificationType(val raw: Int) {
        MESSAGE_TYPE_MESSAGE(1),
        MESSAGE_TYPE_NOTIFICATION(2),
        MESSAGE_TYPE_VIDEO_CALL(3),
        MESSAGE_TYPE_CHAT(4),
        MESSAGE_TYPE_RENT_REQUEST(5),
        MESSAGE_TYPE_CANCEL_RENT_REQUEST_TO_OWNER(6),
        MESSAGE_TYPE_CANCEL_RENT_REQUEST_TO_RENTER(7),
        MESSAGE_TYPE_OWNER_ACCEPTED_ANOTHER_REQUEST(8),
        MESSAGE_TYPE_RENT_SUCCESS(9),
        MESSAGE_TYPE_CONTRACT_TERMINATE(10),
        MESSAGE_TYPE_ADD_PAYMENT(11),
        MESSAGE_TYPE_BILL(12),
        MESSAGE_TYPE_PAYMENT_REQUEST(13),
        MESSAGE_TYPE_CONFIRM_PAYMENT(14),
    }

    enum class PaymentStatus(val raw: Int) {
        WAITING_BILL(0),
        WAITING_PAYMENT(1),
        WAITING_CONFIRM(2),
        DONE(3),
    }

}