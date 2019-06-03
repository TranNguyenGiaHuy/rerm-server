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
class MessageService(private val grpcSessionRepository: GrpcSessionRepository, private val grpcSessionService: GrpcSessionService, private val propertyConfig: PropertyConfig) {

    private val FIREBASE_SERVER_KEY = propertyConfig.firebaseKey
    private val FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send"

    enum class MessageType {
        Data,
        Notification
    }

    fun sendMessageToUser(title: String, message: String, userId: Long): BeanResult {
        return sendNotification(title, message, userId, MessageType.Data)
    }

    fun sendNotificationToUser(title: String, message: String, userId: Long): BeanResult {
        return sendNotification(title, message, userId, MessageType.Notification)
    }

    fun sendNotification(title: String, message: String, userId: Long, messageType: MessageType): BeanResult {
        val beanResult = BeanResult()

        val grpcSessionList = grpcSessionRepository.findByUser_IdAndAvailable(userId, true)
        if (grpcSessionList.isEmpty()) {
            beanResult.code = ResultCode.RESULT_CODE_NOT_FOUND
            return beanResult
        }

        val getSessionResult = grpcSessionService.session
        if (getSessionResult.code != ResultCode.RESULT_CODE_VALID
                || getSessionResult.bean == null
                || getSessionResult.bean !is BeanGrpcSession) {
            beanResult.code = ResultCode.RESULT_CODE_NOT_LOGIN
            return beanResult
        }
        val senderUserId = (getSessionResult.bean as BeanGrpcSession).userId

        val restTemplate = RestTemplate()

        val header = HttpHeaders()
        header.set("Authorization", "key=$FIREBASE_SERVER_KEY")
        header.set("Content-Type", "application/json")

        val messageJSON = JSONObject()
        val dataJSON = JSONObject()

        messageJSON.put("title", title)
        messageJSON.put("body", message)
        messageJSON.put("sender", senderUserId)
        messageJSON.put("notificationType", AppConstants.MESSAGE_TYPE_MESSAGE)

        dataJSON.put(
                when(messageType){
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