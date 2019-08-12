package com.huytran.rerm.service.grpcserviceimpl

import com.huytran.grpcdemo.generatedproto.*
import com.huytran.rerm.bean.BeanContract
import com.huytran.rerm.service.ContractService
import com.huytran.rerm.service.UserService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class ContractServiceImpl(private val contractService: ContractService, private val userService: UserService): ContractServiceGrpc.ContractServiceImplBase() {

    override fun getAllContract(request: GetAllContractRequest?, responseObserver: StreamObserver<GetAllContractResponse>?) {
        val beanContractList = contractService.allOfUser
        val response = GetAllContractResponse.newBuilder()
        beanContractList.forEach {
            response.addContract(
                    beanToContract(it)
            )
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun terminateContract(request: TerminateContractRequest, responseObserver: StreamObserver<TerminateContractResponse>?) {
        val result = contractService.terminateContract(request.id)
        val response = TerminateContractResponse.newBuilder()
                .setResultCode(result.code)
                .build()

        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    override fun getAllContractForAdmin(request: GetAllContractRequest?, responseObserver: StreamObserver<GetAllContractResponse>?) {
        if (!userService.isAdmin) {
            responseObserver?.onError(
                    Throwable("Permission Denied")
            )
            return
        }
        val beanContractList = contractService.allForAdmin
        val response = GetAllContractResponse.newBuilder()
        beanContractList.forEach {
            response.addContract(
                    beanToContract(it)
            )
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun getAllContractOfUserForAdmin(request: GetAllContractOfUserRequest, responseObserver: StreamObserver<GetAllContractResponse>?) {
        if (!userService.isAdmin) {
            responseObserver?.onError(
                    Throwable("Permission Denied")
            )
            return
        }
        val result = contractService.getAllOfUserForAdmin(request.id)
        val response = GetAllContractResponse.newBuilder()
        result.forEach {
            response.addContract(
                    beanToContract(it)
            )
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    private fun beanToContract(beanContract: BeanContract): Contract {
        return Contract.newBuilder()
                .setId(beanContract.id)
                .setOwner(beanContract.owner)
                .setRenter(beanContract.renter)
                .setTsEnd(beanContract.tsEnd)
                .setTsStart(beanContract.tsStart)
                .setModeOPayment(beanContract.modeOPayment)
                .setRoomId(beanContract.roomId)
                .setOwnerName(beanContract.ownerName)
                .setRenterName(beanContract.renterName)
                .setRoomName(beanContract.roomName)
                .build()
    }
}