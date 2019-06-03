package com.huytran.rerm.service.grpcserviceimpl

import com.huytran.grpcdemo.generatedproto.*
import com.huytran.rerm.bean.BeanRentRequest
import com.huytran.rerm.bean.core.BeanList
import com.huytran.rerm.constant.ResultCode
import com.huytran.rerm.service.RentRequestService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class RentRequestServiceImpl(private val rentRequestService: RentRequestService) : RentRequestServiceGrpc.RentRequestServiceImplBase() {

    override fun addRentRequest(request: AddRentRequestRequest, responseObserver: StreamObserver<AddRentRequestResponse>?) {
        val addRentRequestResult = rentRequestService.addRequest(
                RentRequestService.CreateParams(
                        request.tsStart,
                        request.tsEnd,
                        request.roomId
                )
        )

        val response = AddRentRequestResponse.newBuilder()
                .setResultCode(addRentRequestResult.code)
                .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    override fun confirmRentRequest(request: ConfirmRentRequestRequest, responseObserver: StreamObserver<ConfirmRentRequestResponse>?) {
        val confirmRentRequestResult = rentRequestService.confirmRentRequest(request.id)

        val response = ConfirmRentRequestResponse.newBuilder()
                .setResultCode(confirmRentRequestResult.code)
                .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    override fun getRentRequestOfRoom(request: GetRentRequestOfRoomRequest, responseObserver: StreamObserver<GetRentRequestOfRoomResponse>?) {
        val getRentRequestOfRoomResult = rentRequestService.getRentRequestOfRoom(request.id)

        val response = GetRentRequestOfRoomResponse.newBuilder()
                .setResultCode(getRentRequestOfRoomResult.code)
        if (getRentRequestOfRoomResult.code == ResultCode.RESULT_CODE_VALID
                && getRentRequestOfRoomResult.bean != null
                && getRentRequestOfRoomResult.bean is BeanList) {
            (getRentRequestOfRoomResult.bean as BeanList).listBean.forEachIndexed { _, bean ->
                val rentRequestBean = bean as BeanRentRequest
                response.addRentRequest(
                        RentRequest.newBuilder()
                                .setTsStart(rentRequestBean.tsStart)
                                .setTsEnd(rentRequestBean.tsEnd)
                                .setRoomId(rentRequestBean.roomId)
                                .setRenter(rentRequestBean.renter)
                                .setId(rentRequestBean.id)
                                .build()
                )
            }
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun cancelRentRequest(request: CancelRentRequestRequest, responseObserver: StreamObserver<CancelRentRequestResponse>?) {
        val result = rentRequestService.delete(request.requestId)

        val response = CancelRentRequestResponse.newBuilder()
                .setResultCode(result.code)
                .build()

        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }
}