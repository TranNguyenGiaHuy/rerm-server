package com.huytran.rerm.service.grpcserviceimpl

import com.huytran.grpcdemo.generatedproto.*
import com.huytran.rerm.bean.BeanRoom
import com.huytran.rerm.bean.core.BeanList
import com.huytran.rerm.constant.ResultCode
import com.huytran.rerm.service.RoomService
import com.huytran.rerm.service.SavedRoomService
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class RoomServiceImpl(private val roomService: RoomService, private val savedRoomService: SavedRoomService) : RoomServiceGrpc.RoomServiceImplBase() {

    override fun createRoom(request: CreateRoomRequest?, responseObserver: StreamObserver<CreateRoomResponse>?) {
        val createResult = roomService.create(
                RoomService.CreateParams(
                        request?.room?.title,
                        request?.room?.square,
                        request?.room?.address,
                        request?.room?.price,
                        request?.room?.type,
                        request?.room?.numberOfFloor,
                        request?.room?.hasFurniture,
                        request?.room?.maxMember,
                        request?.room?.cookingAllowance,
                        request?.room?.homeType,
                        request?.room?.prepaid,
                        request?.room?.description,
                        request?.room?.term,
                        request?.room?.electricityPrice,
                        request?.room?.waterPrice
                )
        )

        val response = CreateRoomResponse.newBuilder()
                .setResultCode(createResult.code)
        if (createResult.code == ResultCode.RESULT_CODE_VALID
                && createResult.bean != null
                && createResult.bean is BeanRoom) {
            response.roomId = (createResult.bean as BeanRoom).id
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun getAllRoom(request: GetAllRoomRequest?, responseObserver: StreamObserver<GetAllRoomResponse>?) {
        val getAllResult = roomService.all

        val response = GetAllRoomResponse.newBuilder()
                .setResultCode(getAllResult.code)

        if (getAllResult.code == ResultCode.RESULT_CODE_VALID
                && getAllResult.bean != null
                && getAllResult.bean is BeanList) {
            val beanList = getAllResult.bean as BeanList
            beanList.listBean.forEachIndexed { _, beanBasic ->
                val bean = beanBasic as BeanRoom
                response.addRoom(
                        beanToResultRoom(bean)
                )
            }
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun getAllSavedRoom(request: GetAllSavedRoomRequest?, responseObserver: StreamObserver<GetAllSavedRoomResponse>?) {
        val getAllSavedRoomResult = savedRoomService.allOfCurrentUser
        val response = GetAllSavedRoomResponse.newBuilder()
                .setResultCode(getAllSavedRoomResult.code)

        if (getAllSavedRoomResult.code == ResultCode.RESULT_CODE_VALID
                && getAllSavedRoomResult.bean != null
                && getAllSavedRoomResult.bean is BeanList) {
            val beanList = getAllSavedRoomResult.bean as BeanList
            response.addAllRoom(
                    beanList.listBean.map {
                        beanToResultRoom(it as BeanRoom).build()
                    }
            )
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    private fun beanToResultRoom(bean: BeanRoom): Room.Builder {
        return Room.newBuilder()
                .setId(bean.id)
                .setTitle(bean.title)
                .setSquare(bean.square)
                .setAddress(bean.address)
                .setPrice(bean.price)
                .setType(bean.type)
                .setNumberOfFloor(bean.numberOfFloor)
                .setHasFurniture(bean.hasFurniture)
                .setMaxMember(bean.maxMember)
                .setCookingAllowance(bean.cookingAllowance)
                .setHomeType(bean.homeType)
                .setPrepaid(bean.prepaid)
                .setDescription(bean.description)

                .setOwnerId(bean.owner)
                .setOwnerName(bean.ownerName)
                .setTerm(bean.term)
                .setElectricityPrice(bean.electricityPrice)
                .setWaterPrice(bean.waterPrice)
    }

}