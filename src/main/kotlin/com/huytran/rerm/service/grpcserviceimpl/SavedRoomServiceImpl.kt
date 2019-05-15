package com.huytran.rerm.service.grpcserviceimpl

import com.huytran.grpcdemo.generatedproto.*
import com.huytran.rerm.bean.BeanGrpcSession
import com.huytran.rerm.bean.BeanSavedRoom
import com.huytran.rerm.bean.BeanToken
import com.huytran.rerm.bean.BeanUser
import com.huytran.rerm.bean.core.BeanList
import com.huytran.rerm.constant.ResultCode
import com.huytran.rerm.service.SavedRoomService
import com.huytran.rerm.service.UserService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class SavedRoomServiceImpl(private val savedRoomService: SavedRoomService) : SavedRoomServiceGrpc.SavedRoomServiceImplBase() {

    override fun saveRoom(request: SaveRoomRequest, responseObserver: StreamObserver<SaveRoomResponse>) {
        val saveRoomResult = savedRoomService.saveRoom(request.roomId)
        val response = SaveRoomResponse.newBuilder()
                .setResultCode(saveRoomResult.code)
                .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun unsaveRoom(request: UnsaveRoomRequest, responseObserver: StreamObserver<UnsaveRoomResponse>) {
        val saveRoomResult = savedRoomService.unsaveRoom(request.roomId)
        val response = UnsaveRoomResponse.newBuilder()
                .setResultCode(saveRoomResult.code)
                .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getAllSavedRoomId(request: GetAllSavedRoomIdRequest, responseObserver: StreamObserver<GetAllSavedRoomIdResponse>) {
        val getAllRoomIdResult = savedRoomService.allRoomIdOfUser
        val response = GetAllSavedRoomIdResponse.newBuilder()
                .setResultCode(getAllRoomIdResult.code)

        if (getAllRoomIdResult.code == ResultCode.RESULT_CODE_VALID
                && getAllRoomIdResult.bean != null
                && getAllRoomIdResult.bean is BeanList) {
            val beanList = getAllRoomIdResult.bean as BeanList
            response.addAllRoomId(
                    beanList.listBean.map {
                        (it as BeanSavedRoom).roomId
                    }
            )
        }
        responseObserver.onNext(response.build())
        responseObserver.onCompleted()
    }
}