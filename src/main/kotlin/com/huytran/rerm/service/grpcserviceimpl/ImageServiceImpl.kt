package com.huytran.rerm.service.grpcserviceimpl

import com.google.protobuf.ByteString
import com.huytran.grpcdemo.generatedproto.*
import com.huytran.rerm.bean.BeanImage
import com.huytran.rerm.bean.core.BeanList
import com.huytran.rerm.constant.ResultCode
import com.huytran.rerm.service.ImageService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream

@GRpcService
class ImageServiceImpl(private val imageService: ImageService) : ImageServiceGrpc.ImageServiceImplBase() {

    override fun uploadFile(responseObserver: StreamObserver<UploadFileResponse>?): StreamObserver<UploadFileRequest> {
        val result = ByteArrayOutputStream()
        var name: String? = ""
        var roomId: Long? = 0L
        return object : StreamObserver<UploadFileRequest> {
            override fun onNext(p0: UploadFileRequest?) {
                result.write(p0?.content?.toByteArray())
                name = p0?.name
                roomId = p0?.roomId
            }

            override fun onError(p0: Throwable?) {

            }

            override fun onCompleted() {
                val createResult = imageService.create(
                        roomId,
                        result.toByteArray(),
                        name
                )
                val response = UploadFileResponse.newBuilder()
                        .setResultCode(createResult.code)
                if (createResult.code == ResultCode.RESULT_CODE_VALID
                        && createResult.bean != null
                        && createResult.bean is BeanImage) {
                    val bean = createResult.bean as BeanImage
                    response.setName(bean.name)
                            .setRoomId(bean.roomId)
                }

                responseObserver?.onNext(
                        response.build()
                )
            }
        }
    }

    override fun downloadFile(request: DownloadRequest?, responseObserver: StreamObserver<DownloadResponse>?) {
        val downloadResult = imageService.download(
                request?.id
        )
        if (downloadResult.isEmpty()) {
            responseObserver?.onNext(
                    DownloadResponse.newBuilder()
                            .setResultCode(ResultCode.RESULT_CODE_INVALID)
                            .build()
            )
            responseObserver?.onCompleted()
        }
        val imageStream = BufferedInputStream(
                downloadResult.inputStream()
        )
        val bufferSize = 256 * 1024 //256k
        val buffer = ByteArray(bufferSize)
        try {
            var length = imageStream.read(buffer, 0, bufferSize)
            while (length != -1) {
                responseObserver?.onNext(
                        DownloadResponse.newBuilder()
                                .setImage(ByteString.copyFrom(buffer, 0, length))
                                .build()
                )
                length = imageStream.read(buffer, 0, bufferSize)
            }
            responseObserver?.onCompleted()
        } catch (e: Exception) {
            e.printStackTrace()
            responseObserver?.onNext(
                    DownloadResponse.newBuilder()
                            .setResultCode(ResultCode.RESULT_CODE_INVALID)
                            .build()
            )
            responseObserver?.onCompleted()
        }
    }

    override fun getFileOfRoom(request: GetFileOfRoomRequest?, responseObserver: StreamObserver<GetFileOfRoomResponse>?) {
        val getFileListResult = imageService.getAllOfRoom(
            request?.roomId
        )
        val response = GetFileOfRoomResponse.newBuilder()
                .setResultCode(getFileListResult.code)
        if (getFileListResult.code == ResultCode.RESULT_CODE_VALID
                && getFileListResult.bean != null
                && getFileListResult.bean is BeanList) {
            response.addAllFileIdList(
                    (getFileListResult.bean as BeanList).listBean.map {
                        (it as BeanImage).id
                    }
            )
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

}