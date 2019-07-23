package com.huytran.rerm.service.grpcserviceimpl

import com.huytran.grpcdemo.generatedproto.Contract
import com.huytran.grpcdemo.generatedproto.ContractServiceGrpc
import com.huytran.grpcdemo.generatedproto.GetAllContractRequest
import com.huytran.grpcdemo.generatedproto.GetAllContractResponse
import com.huytran.rerm.bean.BeanContract
import com.huytran.rerm.service.ContractService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class ContractServiceImpl(private val contractService: ContractService): ContractServiceGrpc.ContractServiceImplBase() {

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