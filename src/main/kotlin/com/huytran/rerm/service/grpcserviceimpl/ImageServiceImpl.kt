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
                p0?.printStackTrace()
            }

            override fun onCompleted() {
                val createResult = imageService.create(
                        roomId,
                        ImageService.ImageParams(
                                result.toByteArray(),
                                name
                        )
                )
                val response = UploadFileResponse.newBuilder()
                        .setResultCode(createResult.code)
//                if (createResult.code == ResultCode.RESULT_CODE_VALID
//                        && createResult.bean != null
//                        && createResult.bean is BeanImage) {
//                    val bean = createResult.bean as BeanImage
//                    response.setName(bean.name)
//                            .setRoomId(bean.roomId)
//                            .setResultCode(ResultCode.RESULT_CODE_VALID)
//                            .setId(bean.id)
//                }

                responseObserver?.onNext(
                        response.build()
                )
                responseObserver?.onCompleted()
            }
        }
    }

    override fun downloadFile(request: DownloadRequest?, responseObserver: StreamObserver<DownloadResponse>?) {
        val downloadResult = imageService.download(
                request?.id
        )
        if (downloadResult.code != ResultCode.RESULT_CODE_VALID
                || downloadResult.bean == null
                || downloadResult.bean !is BeanImage
                || (downloadResult.bean as BeanImage).content.isEmpty()) {
            responseObserver?.onNext(
                    DownloadResponse.newBuilder()
                            .setResultCode(downloadResult.code)
                            .build()
            )
            responseObserver?.onCompleted()
            return
        }

        val beanImage = downloadResult.bean as BeanImage

        val imageStream = BufferedInputStream(
                beanImage.content.inputStream()
        )
        val bufferSize = 256 * 1024 //256k
        val buffer = ByteArray(bufferSize)
        try {
            var length = imageStream.read(buffer, 0, bufferSize)
            while (length != -1) {
                responseObserver?.onNext(
                        DownloadResponse.newBuilder()
                                .setImage(ByteString.copyFrom(buffer, 0, length))
                                .setResultCode(downloadResult.code)
                                .setName(beanImage.name)
                                .setFileName(beanImage.fileName)
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
            response.addAllFileId(
                    (getFileListResult.bean as BeanList).listBean.map {
                        (it as BeanImage).id
                    }
            )
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun uploadMultiImage(responseObserver: StreamObserver<UploadMultiImageResponse>?): StreamObserver<UploadMultiImageRequest> {
        return object: StreamObserver<UploadMultiImageRequest> {

            val content = ByteArrayOutputStream()
            val imageParamsList = arrayListOf<ImageService.ImageParams>()
            var roomId : Long? = null

            override fun onNext(p0: UploadMultiImageRequest?) {
                roomId = p0?.roomId

                content.write(p0?.image?.content?.toByteArray())
                if (p0?.image?.isLast!!) {
                    imageParamsList.add(
                            ImageService.ImageParams(
                                    content.toByteArray(),
                                    p0.image.name
                            )
                    )

                    content.reset()
                }
            }

            override fun onError(p0: Throwable?) {
            }

            override fun onCompleted() {
                content.close()
                val createResult = imageService.create(
                        roomId,
                        imageParamsList
                )
                val response = UploadMultiImageResponse.newBuilder()
                        .setResultCode(createResult.code)
                        .build()

                responseObserver?.onNext(response)
                responseObserver?.onCompleted()
            }

        }
    }

}