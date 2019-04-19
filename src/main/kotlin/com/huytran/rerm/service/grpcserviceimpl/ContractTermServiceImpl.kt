package com.huytran.rerm.service.grpcserviceimpl

import com.huytran.grpcdemo.generatedproto.ContractTerm
import com.huytran.grpcdemo.generatedproto.ContractTermServiceGrpc
import com.huytran.grpcdemo.generatedproto.GetAllContractTermRequest
import com.huytran.grpcdemo.generatedproto.GetAllContractTermResponse
import com.huytran.rerm.bean.BeanContractTerm
import com.huytran.rerm.bean.core.BeanList
import com.huytran.rerm.constant.ResultCode
import com.huytran.rerm.service.ContractTermService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class ContractTermServiceImpl(private val contractTermService: ContractTermService): ContractTermServiceGrpc.ContractTermServiceImplBase() {

    override fun getAllContractTerm(request: GetAllContractTermRequest?, responseObserver: StreamObserver<GetAllContractTermResponse>?) {
        val getContractTermResult = contractTermService.all

        val response = GetAllContractTermResponse.newBuilder()
                .setResultCode(getContractTermResult.code)

        if (getContractTermResult.code == ResultCode.RESULT_CODE_VALID
                && getContractTermResult.bean != null
                && getContractTermResult.bean is BeanList) {
            val beanList = getContractTermResult.bean as BeanList
            beanList.listBean.forEachIndexed { index, beanBasic ->
                val bean = beanBasic as BeanContractTerm
                response.setContractTermList(
                        index,
                        ContractTerm.newBuilder()
                                .setId(bean.id)
                                .setName(bean.name)
                                .setDescription(bean.description)
                )
            }
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

}