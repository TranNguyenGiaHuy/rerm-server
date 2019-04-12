package com.huytran.rerm.service.grpcserviceimpl

import com.google.protobuf.ByteString
import com.huytran.grpcdemo.generatedproto.*
import com.huytran.rerm.bean.BeanAvatar
import com.huytran.rerm.constant.ResultCode
import com.huytran.rerm.service.AvatarService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream

@GRpcService
class AvatarServiceImpl(private val avatarService: AvatarService) : AvatarServiceGrpc.AvatarServiceImplBase() {

    override fun uploadAvatar(responseObserver: StreamObserver<UploadAvatarResponse>?): StreamObserver<UploadAvatarRequest> {
        val result = ByteArrayOutputStream()
        var name: String? = ""
        var userId: Long? = 0L
        return object : StreamObserver<UploadAvatarRequest> {
            override fun onNext(p0: UploadAvatarRequest?) {
                result.write(p0?.content?.toByteArray())
                name = p0?.name
                userId = p0?.userId
            }

            override fun onError(p0: Throwable?) {
                p0?.printStackTrace()
            }

            override fun onCompleted() {
                val createResult = avatarService.create(
                        userId,
                        result.toByteArray(),
                        name
                )
                val response = UploadAvatarResponse.newBuilder()
                        .setResultCode(createResult.code)
                if (createResult.code == ResultCode.RESULT_CODE_VALID
                        && createResult.bean != null
                        && createResult.bean is BeanAvatar) {
                    val bean = createResult.bean as BeanAvatar
                    response.setResultCode(ResultCode.RESULT_CODE_VALID)
                            .setName(bean.name)
                            .setUserId(bean.userId)
                            .setId(bean.id)
                            .setFileName(bean.fileName)
                }

                responseObserver?.onNext(
                        response.build()
                )
                responseObserver?.onCompleted()
            }
        }
    }

    override fun downloadAvatar(request: DownloadAvatarRequest?, responseObserver: StreamObserver<DownloadAvatarResponse>?) {
        val downloadResult = avatarService.download()
        if (downloadResult.code != ResultCode.RESULT_CODE_VALID
                || downloadResult.bean !is BeanAvatar
                || (downloadResult.bean as BeanAvatar).content.isEmpty()) {
            responseObserver?.onNext(
                    DownloadAvatarResponse.newBuilder()
                            .setResultCode(downloadResult.code)
                            .build()
            )
            responseObserver?.onCompleted()
        }

        val beanAvatar = downloadResult.bean as BeanAvatar

        val imageStream = BufferedInputStream(
                beanAvatar.content.inputStream()
        )
        val bufferSize = 256 * 1024 //256k
        val buffer = ByteArray(bufferSize)
        try {
            var length = imageStream.read(buffer, 0, bufferSize)
            while (length != -1) {
                responseObserver?.onNext(
                        DownloadAvatarResponse.newBuilder()
                                .setImage(ByteString.copyFrom(buffer, 0, length))
                                .setResultCode(downloadResult.code)
                                .setFileName(beanAvatar.fileName)
                                .setName(beanAvatar.name)
                                .build()
                )
                length = imageStream.read(buffer, 0, bufferSize)
            }
            responseObserver?.onCompleted()
        } catch (e: Exception) {
            e.printStackTrace()
            responseObserver?.onNext(
                    DownloadAvatarResponse.newBuilder()
                            .setResultCode(ResultCode.RESULT_CODE_INVALID)
                            .build()
            )
            responseObserver?.onCompleted()
        }
    }

}