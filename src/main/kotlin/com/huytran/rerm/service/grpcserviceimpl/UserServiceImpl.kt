package com.huytran.rerm.service.grpcserviceimpl

import com.huytran.grpcdemo.generatedproto.*
import com.huytran.rerm.bean.BeanToken
import com.huytran.rerm.service.UserService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class UserServiceImpl(private val userService: UserService): UserServiceGrpc.UserServiceImplBase() {

    override fun signUp(request: SignUpRequest?, responseObserver: StreamObserver<SignUpResponse>?) {
        val signupResult = userService.signup(
                UserService.Params(
                        request?.name,
                        request?.password
                )
        )
        val response = SignUpResponse.newBuilder()
                .setResultCode(signupResult.code)
                .setToken(
                        (signupResult.bean as BeanToken).token
                )
                .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    override fun login(request: LoginRequest?, responseObserver: StreamObserver<LoginResponse>?) {
        val loginResult = userService.login(
                UserService.Params(
                        request?.name,
                        request?.password
                )
        )
        val response = LoginResponse.newBuilder()
                .setResultCode(loginResult.code)
                .setToken(
                        (loginResult.bean as BeanToken).token
                )
                .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

}