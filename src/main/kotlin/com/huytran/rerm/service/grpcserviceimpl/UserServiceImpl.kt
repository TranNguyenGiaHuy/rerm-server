package com.huytran.rerm.service.grpcserviceimpl

import com.huytran.grpcdemo.generatedproto.*
import com.huytran.rerm.bean.BeanGrpcSession
import com.huytran.rerm.bean.BeanToken
import com.huytran.rerm.bean.BeanUser
import com.huytran.rerm.constant.ResultCode
import com.huytran.rerm.service.UserService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class UserServiceImpl(private val userService: UserService) : UserServiceGrpc.UserServiceImplBase() {

    override fun signUp(request: SignUpRequest?, responseObserver: StreamObserver<SignUpResponse>?) {
        val signupResult = userService.signup(
                UserService.Params(
                        request?.name,
                        request?.password
                )
        )
        val response = SignUpResponse.newBuilder()
                .setResultCode(signupResult.code)

        if (signupResult.code == ResultCode.RESULT_CODE_VALID
                && signupResult.bean != null
                && signupResult.bean is BeanGrpcSession) {
            response.token = (signupResult.bean as BeanGrpcSession).token
        }

        responseObserver?.onNext(response.build())
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
        if (loginResult.code == ResultCode.RESULT_CODE_VALID
                && loginResult.bean != null
                && loginResult.bean is BeanGrpcSession) {
            response.token = (loginResult.bean as BeanGrpcSession).token
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun getInfo(request: GetInfoRequest?, responseObserver: StreamObserver<GetInfoResponse>?) {
        val getInfoResult = userService.get()
        val response = GetInfoResponse.newBuilder()
                .setResultCode(getInfoResult.code)

        if (getInfoResult.code == ResultCode.RESULT_CODE_VALID
                && getInfoResult.bean != null
                && getInfoResult.bean is BeanUser) {
            val bean = getInfoResult.bean as BeanUser
            response
                    .setName(bean.name)
                    .setUserName(bean.userName)
                    .setAvatarId(bean.avatarId)
                    .setPhoneNumber(bean.phoneNumber)
                    .setIdCard(bean.idCard)
                    .setTsCardDated(bean.tsCardDated)
                    .setTsDateOfBirth(bean.tsDateOfBirth)
                    .setId(bean.id)
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun logout(request: LogoutRequest?, responseObserver: StreamObserver<LogoutResponse>?) {
        userService.logout()
        val response = LogoutResponse.newBuilder()
                .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    override fun loginWithToken(request: LoginWithTokenRequest?, responseObserver: StreamObserver<LoginWithTokenResponse>?) {
        val checkLoginResult = userService.loginWithToken()
        val response = LoginWithTokenResponse.newBuilder()
                .setResultCode(checkLoginResult.code)
                .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

}