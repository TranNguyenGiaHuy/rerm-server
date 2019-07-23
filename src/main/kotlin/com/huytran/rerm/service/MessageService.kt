package com.huytran.rerm.service

import com.huytran.rerm.bean.BeanGrpcSession
import com.huytran.rerm.bean.core.BeanResult
import com.huytran.rerm.config.PropertyConfig
import com.huytran.rerm.constant.AppConstants
import com.huytran.rerm.constant.ResultCode
import com.huytran.rerm.repository.GrpcSessionRepository
import org.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import javax.xml.crypto.Data


@Service
class MessageService(
        private val grpcSessionRepository: GrpcSessionRepository,
        private val grpcSessionService: GrpcSessionService,
        propertyConfig: PropertyConfig) {

    private val FIREBASE_SERVER_KEY = propertyConfig.firebaseKey
    private val FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send"

    enum class MessageType {
        Data,
        Notification
    }

    fun sendMessageToUser(title: String, message: String, userId: Long): BeanResult {
        return sendNotification(title, message, userId, MessageType.Data, AppConstants.NotificationType.MESSAGE_TYPE_MESSAGE)
    }

    fun sendNotificationToUser(title: String, message: String, userId: Long): BeanResult {
        return sendNotification(title, message, userId, MessageType.Notification, AppConstants.NotificationType.MESSAGE_TYPE_NOTIFICATION)
    }

    fun sendChatToUser(message: String, userId: Long): BeanResult {
        return sendNotification("", message, userId, MessageType.Data, AppConstants.NotificationType.MESSAGE_TYPE_CHAT)
    }

    fun sendRentRequestToOwner(roomId: Long, ownerId: Long, renterId: Long) =
            sendNotificationWithExtraData(
                    renterId,
                    ownerId,
                    roomId,
                    0L,
                    AppConstants.NotificationType.MESSAGE_TYPE_RENT_REQUEST
            )

    fun sendCancelRentRequestToOwner(roomId: Long, ownerId: Long, renterId: Long) =
            sendNotificationWithExtraData(
                    renterId,
                    ownerId,
                    roomId,
                    0L,
                    AppConstants.NotificationType.MESSAGE_TYPE_CANCEL_RENT_REQUEST_TO_OWNER
            )

    fun sendCancelRentRequestToRenter(roomId: Long, ownerId: Long, renterId: Long) =
            sendNotificationWithExtraData(
                    ownerId,
                    renterId,
                    roomId,
                    0L,
                    AppConstants.NotificationType.MESSAGE_TYPE_CANCEL_RENT_REQUEST_TO_RENTER
            )

    fun sendAcceptedAnotherRequest(roomId: Long, ownerId: Long, renterId: Long) =
            sendNotificationWithExtraData(
                    ownerId,
                    renterId,
                    roomId,
                    0L,
                    AppConstants.NotificationType.MESSAGE_TYPE_OWNER_ACCEPTED_ANOTHER_REQUEST
            )

    fun sendRentSuccess(roomId: Long, ownerId: Long, renterId: Long) =
            sendNotificationWithExtraData(
                    ownerId,
                    renterId,
                    roomId,
                    0L,
                    AppConstants.NotificationType.MESSAGE_TYPE_RENT_SUCCESS
            )

    fun sendContractTerminate(roomId: Long, ownerId: Long, renterId: Long) {
        sendNotificationWithExtraData(
                ownerId,
                renterId,
                roomId,
                0L,
                AppConstants.NotificationType.MESSAGE_TYPE_CONTRACT_TERMINATE
        )
        sendNotificationWithExtraData(
                renterId,
                ownerId,
                roomId,
                0L,
                AppConstants.NotificationType.MESSAGE_TYPE_CONTRACT_TERMINATE
        )
    }

    // request owner add payment
    fun sendAddPayment(roomId: Long, ownerId: Long, renterId: Long, tsStart: Long, tsEnd: Long) =
            sendNotificationWithExtraData(
                    renterId,
                    ownerId,
                    roomId,
                    0L,
                    AppConstants.NotificationType.MESSAGE_TYPE_ADD_PAYMENT,
                    tsStart,
                    tsEnd
            )

    // request renter pay bill
    fun sendBill(roomId: Long, ownerId: Long, renterId: Long, value: Long, tsStart: Long, tsEnd: Long) =
            sendNotificationWithExtraData(
                    ownerId,
                    renterId,
                    roomId,
                    value,
                    AppConstants.NotificationType.MESSAGE_TYPE_BILL,
                    tsStart,
                    tsEnd
            )

    // request owner confirm bill
    fun sendPaymentRequest(roomId: Long, ownerId: Long, renterId: Long, value: Long, tsStart: Long, tsEnd: Long) =
            sendNotificationWithExtraData(
                    renterId,
                    ownerId,
                    roomId,
                    value,
                    AppConstants.NotificationType.MESSAGE_TYPE_PAYMENT_REQUEST,
                    tsStart,
                    tsEnd
            )

    // send to renter, owner confirmed payment
    fun sendConfirmPayment(roomId: Long, ownerId: Long, renterId: Long, value: Long, tsStart: Long, tsEnd: Long) =
            sendNotificationWithExtraData(
                    ownerId,
                    renterId,
                    roomId,
                    value,
                    AppConstants.NotificationType.MESSAGE_TYPE_CONFIRM_PAYMENT,
                    tsStart,
                    tsEnd
            )

    fun sendNotificationWithExtraData(
            from: Long,
            to: Long,
            roomId: Long,
            value: Long,
            notificationType: AppConstants.NotificationType,
            tsStart: Long? = null,
            tsEnd: Long? = null): BeanResult {
        val extraData = mutableMapOf<String, Any>(
                "from" to from,
                "room" to roomId,
                "value" to value
        )
        tsStart?.let {
            extraData.put("tsStart", tsStart)
        }
        tsEnd?.let {
            extraData.put("tsEnd", tsEnd)
        }
        return sendNotification("", "notification", to, MessageType.Data, notificationType, extraData)
    }

    fun sendNotification(title: String, message: String, userId: Long, messageType: MessageType, notificationType: AppConstants.NotificationType, extraData: Map<String, Any>? = null): BeanResult {
        val beanResult = BeanResult()

        val grpcSessionList = grpcSessionRepository.findByUser_IdAndAvailable(userId, true)
        if (grpcSessionList.isEmpty()) {
            beanResult.code = ResultCode.RESULT_CODE_NOT_FOUND
            return beanResult
        }

        val getSessionResult = grpcSessionService.session
//        if (getSessionResult.code != ResultCode.RESULT_CODE_VALID
//                || getSessionResult.bean == null
//                || getSessionResult.bean !is BeanGrpcSession) {
//            beanResult.code = ResultCode.RESULT_CODE_NOT_LOGIN
//            return beanResult
//        }
//        val senderUserId = (getSessionResult.bean as BeanGrpcSession).userId

        val restTemplate = RestTemplate()

        val header = HttpHeaders()
        header.set("Authorization", "key=$FIREBASE_SERVER_KEY")
        header.set("Content-Type", "application/json")

        val messageJSON = JSONObject()
        val dataJSON = JSONObject()

        messageJSON.put("title", title)
        messageJSON.put("body", message)
        messageJSON.put("notificationType", notificationType.raw)
        if (getSessionResult.code == ResultCode.RESULT_CODE_VALID
                && getSessionResult.bean != null
                && getSessionResult.bean is BeanGrpcSession) {
            messageJSON.put("sender", (getSessionResult.bean as BeanGrpcSession).userId)
        }

        extraData?.let {extra ->
            messageJSON.put("extraData", extra)
//            extra.forEach {map ->
//                messageJSON.put(
//                        map.key,
//                        map.value
//                )
//            }
        }

        dataJSON.put(
                when (messageType) {
                    MessageType.Data -> "data"
                    MessageType.Notification -> "notification"

                },
                messageJSON)
        dataJSON.put(
                "registration_ids",
                grpcSessionList.map {
                    it.firebaseToken
                }.toTypedArray()
        )

        val httpEntity = HttpEntity(dataJSON.toString(), header)
        val response = restTemplate.postForObject(FIREBASE_API_URL, httpEntity, String::class.java)

        beanResult.code = ResultCode.RESULT_CODE_VALID
        return beanResult
    }

}