package com.huytran.rerm.service.grpcserviceimpl

import com.huytran.grpcdemo.generatedproto.*
import com.huytran.rerm.bean.BeanPayment
import com.huytran.rerm.bean.BeanRoom
import com.huytran.rerm.bean.core.BeanList
import com.huytran.rerm.constant.ResultCode
import com.huytran.rerm.service.PaymentService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class PaymentServiceImpl(val paymentService: PaymentService): PaymentServiceGrpc.PaymentServiceImplBase() {

    override fun getPaymentOfRoom(request: GetPaymentOfRoomRequest, responseObserver: StreamObserver<GetPaymentOfRoomResponse>?) {
        val result = paymentService.getPaymentOfRoom(request.roomId)
        val response = GetPaymentOfRoomResponse.newBuilder()
                .setResultCode(result.code)
        if (result.code == ResultCode.RESULT_CODE_VALID
                && result.bean != null
                && result.bean is BeanList) {
            (result.bean as BeanList).listBean.forEach {
                response.addPayment(
                        beanToPayment(it as BeanPayment)
                )
            }
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun addBill(request: AddBillRequest, responseObserver: StreamObserver<AddBillResponse>?) {
        val result = paymentService.addBill(
                request.paymentId,
                request.electricityBill,
                request.waterBill
        )
        val response = AddBillResponse.newBuilder()
                .setResultCode(result.code)
        if (result.code == ResultCode.RESULT_CODE_VALID
                && result.bean != null
                && result.bean is BeanRoom) {
            response.payment = beanToPayment(result.bean as BeanPayment)
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun requestPaid(request: RequestPaidRequest, responseObserver: StreamObserver<RequestPaidResponse>?) {
        val result = paymentService.requestPaid(request.paymentId)
        val response = RequestPaidResponse.newBuilder()
                .setResultCode(result.code)
        if (result.code == ResultCode.RESULT_CODE_VALID
                && result.bean != null
                && result.bean is BeanRoom) {
            response.payment = beanToPayment(result.bean as BeanPayment)
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun confirmPayment(request: ConfirmPaymentRequest, responseObserver: StreamObserver<ConfirmPaymentResponse>?) {
        val result = paymentService.confirmPayment(request.paymentId)
        val response = ConfirmPaymentResponse.newBuilder()
                .setResultCode(result.code)
        if (result.code == ResultCode.RESULT_CODE_VALID
                && result.bean != null
                && result.bean is BeanRoom) {
            response.payment = beanToPayment(result.bean as BeanPayment)
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    private fun beanToPayment(bean: BeanPayment): Payment {
        return Payment.newBuilder()
                .setAmount(bean.amount)
                .setContractId(bean.contractId)
                .setCurrency(bean.currency)
                .setElectricityBill(bean.electricityBill)
                .setPayerId(bean.payerId)
                .setStatus(bean.status)
                .setTsEnd(bean.tsEnd)
                .setTsStart(bean.tsStart)
                .setWaterBill(bean.waterBill)
                .build()
    }
}