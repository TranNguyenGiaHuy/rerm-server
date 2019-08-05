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

    override fun createRoom(request: CreateRoomRequest, responseObserver: StreamObserver<CreateRoomResponse>?) {
        val createResult = roomService.create(
                roomToCreateParams(request.room)
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
            beanList.listBean.forEach { beanBasic ->
                val bean = beanBasic as BeanRoom
                if (!bean.renting) {
                    response.addRoom(
                            beanToResultRoom(bean)
                    )
                }
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
                    beanList.listBean
                            .filter { beanBasic ->
                                !(beanBasic as BeanRoom).renting
                            }
                            .map {
                                beanToResultRoom(it as BeanRoom).build()
                            }
            )
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun getRoom(request: GetRoomRequest, responseObserver: StreamObserver<GetRoomResponse>?) {
        val getRoomResult = roomService.get(request.roomId)
        val response = GetRoomResponse.newBuilder()
                .setResultCode(getRoomResult.code)
        if (getRoomResult.code == ResultCode.RESULT_CODE_VALID
                && getRoomResult.bean != null
                && getRoomResult.bean is BeanRoom) {
            response.setRoom(
                    beanToResultRoom(getRoomResult.bean as BeanRoom)
            )
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun updateRoom(request: UpdateRoomRequest, responseObserver: StreamObserver<UpdateRoomResponse>?) {
        val updateResult = roomService.update(
                request.room.id,
                roomToCreateParams(request.room)
        )

        val response = UpdateRoomResponse.newBuilder()
                .setResultCode(updateResult.code)
        if (updateResult.code == ResultCode.RESULT_CODE_VALID
                && updateResult.bean != null
                && updateResult.bean is BeanRoom) {
            response.room = beanToResultRoom(updateResult.bean as BeanRoom).build()
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun deleteRoom(request: DeleteRoomRequest, responseObserver: StreamObserver<DeleteRoomResponse>?) {
        val deleteResult = roomService.delete(
                request.id
        )

        val response = DeleteRoomResponse.newBuilder()
                .setResultCode(deleteResult.code)

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun getAllRoomOfUser(request: GetAllRoomOfUserRequest?, responseObserver: StreamObserver<GetAllRoomOfUserResponse>?) {
        val result = roomService.allOfUser
        val response = GetAllRoomOfUserResponse.newBuilder()
                .setResultCode(result.code)

        if (result.code == ResultCode.RESULT_CODE_VALID
                && result.bean != null
                && result.bean is BeanList) {
            (result.bean as BeanList).listBean.forEach { bean ->
                response.addRoom(
                        beanToResultRoom(bean as BeanRoom)
                )
            }
        }

        responseObserver?.onNext(response.build())
        responseObserver?.onCompleted()
    }

    override fun searchRoom(request: SearchRoomRequest, responseObserver: StreamObserver<SearchRoomResponse>?) {
        val result = roomService.search(
                request.keyword,
                request.minPrice,
                request.maxPrice,
                request.type
        )
        val response = SearchRoomResponse.newBuilder()
                .setResultCode(result.code)

        if (result.code == ResultCode.RESULT_CODE_VALID
                && result.bean != null
                && result.bean is BeanList) {
            (result.bean as BeanList).listBean.forEach { bean ->
                response.addRoom(
                        beanToResultRoom(bean as BeanRoom)
                )
            }
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
                .setIsRenting(bean.renting)
    }

    private fun roomToCreateParams(room: Room): RoomService.CreateParams {
        return RoomService.CreateParams(
                room.title,
                room.square,
                room.address,
                room.price,
                room.type,
                room.numberOfFloor,
                room.hasFurniture,
                room.maxMember,
                room.cookingAllowance,
                room.homeType,
                room.prepaid,
                room.description,
                room.term,
                room.electricityPrice,
                room.waterPrice
        )
    }

}