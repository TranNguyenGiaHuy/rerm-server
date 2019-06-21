package com.huytran.rerm.service.grpcserviceimpl

import com.huytran.grpcdemo.generatedproto.*
import com.huytran.rerm.bean.BeanGrpcSession
import com.huytran.rerm.bean.BeanSavedRoom
import com.huytran.rerm.bean.BeanToken
import com.huytran.rerm.bean.BeanUser
import com.huytran.rerm.bean.core.BeanList
import com.huytran.rerm.constant.ResultCode
import com.huytran.rerm.service.GrpcSessionService
import com.huytran.rerm.service.MessageService
import com.huytran.rerm.service.SavedRoomService
import com.huytran.rerm.service.UserService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class MessageServiceImpl(private val messageService: MessageService, private val grpcSessionService: GrpcSessionService) : MessageServiceGrpc.MessageServiceImplBase() {

    override fun sendMessage(request: SendMessageRequest, responseObserver: StreamObserver<SendMessageResponse>?) {
        val sendMessageResult = messageService.sendChatToUser(
                request.message,
                request.to
        )

        val response = SendMessageResponse.newBuilder()
                .setResultCode(sendMessageResult.code)
                .build()

        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    override fun addFirebaseToken(request: AddFirebaseTokenRequest, responseObserver: StreamObserver<AddFirebaseTokenResponse>?) {
        val addTokenResult = grpcSessionService.addFirebaseToken(request.token)

        val response = AddFirebaseTokenResponse.newBuilder()
                .setResultCode(addTokenResult.code)
                .build()

        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

}