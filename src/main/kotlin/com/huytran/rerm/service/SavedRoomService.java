package com.huytran.rerm.service;

import com.huytran.rerm.bean.BeanGrpcSession;
import com.huytran.rerm.bean.core.BeanList;
import com.huytran.rerm.bean.core.BeanResult;
import com.huytran.rerm.constant.ResultCode;
import com.huytran.rerm.model.*;
import com.huytran.rerm.repository.RoomRepository;
import com.huytran.rerm.repository.SavedRoomRepository;
import com.huytran.rerm.repository.UserRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SavedRoomService extends CoreService<SavedRoom, SavedRoomRepository, SavedRoomService.Params> {

    private SavedRoomRepository savedRoomRepository;
    private GrpcSessionService grpcSessionService;
    private UserRepository userRepository;
    private RoomRepository roomRepository;

    SavedRoomService(
            SavedRoomRepository savedRoomRepository,
            GrpcSessionService grpcSessionService, UserRepository userRepository, RoomRepository roomRepository) {
        super(savedRoomRepository);
        this.savedRoomRepository = savedRoomRepository;
        this.grpcSessionService = grpcSessionService;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public SavedRoom createModel() {
        return new SavedRoom();
    }

    @Override
    public void parseParams(SavedRoom savedRoom, Params params) {
        savedRoom.setUser(params.user);
        savedRoom.setRoom(params.room);
    }

    public class Params extends CoreService.AbstractParams {
        User user;
        Room room;

        public Params(User user, Room room) {
            this.user = user;
            this.room = room;
        }
    }

    public BeanResult saveRoom(long roomId) {
        BeanResult beanResult = new BeanResult();

        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getSessionResult.getBean() == null
                || !(getSessionResult.getBean() instanceof BeanGrpcSession)) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            return beanResult;
        }

        Optional<User> optionalUser = userRepository.findById(
                ((BeanGrpcSession) getSessionResult.getBean()).getUserId()
        );
        if (!optionalUser.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        Optional<Room> optionalRoom = roomRepository.findByIdAndAvailable(roomId, true);
        if (!optionalRoom.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        Optional<SavedRoom> optionalSavedRoom = savedRoomRepository.findByRoomAndUser(optionalRoom.get(), optionalUser.get());
        if (optionalSavedRoom.isPresent()) {
            SavedRoom savedRoom = optionalSavedRoom.get();
            savedRoom.setAvailable(true);
            savedRoom.setTsLastModified(System.currentTimeMillis());

            savedRoomRepository.save(savedRoom);
            beanResult.setBean(savedRoom.createBean());
            beanResult.setCode(ResultCode.RESULT_CODE_VALID);
            return beanResult;
        } else {
            return create(
                    new Params(
                            optionalUser.get(),
                            optionalRoom.get()
                    )
            );
        }
    }

    public BeanResult unsaveRoom(long roomId) {
        BeanResult beanResult = new BeanResult();

        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getSessionResult.getBean() == null
                || !(getSessionResult.getBean() instanceof BeanGrpcSession)) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            return beanResult;
        }

        Optional<User> optionalUser = userRepository.findById(
                ((BeanGrpcSession) getSessionResult.getBean()).getUserId()
        );
        if (!optionalUser.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        Optional<SavedRoom> optionalSavedRoom = savedRoomRepository.findByUser_IdAndRoom_IdAndAvailable(
                optionalUser.get().getId(),
                roomId,
                true
        );
        if (optionalSavedRoom.isPresent()) {
            SavedRoom savedRoom = optionalSavedRoom.get();
            savedRoom.setAvailable(false);
            savedRoom.setTsLastModified(System.currentTimeMillis());

            savedRoomRepository.save(savedRoom);
        }

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    public BeanResult getAllOfCurrentUser() {
        BeanResult beanResult = new BeanResult();

        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getSessionResult.getBean() == null
                || !(getSessionResult.getBean() instanceof BeanGrpcSession)) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            return beanResult;
        }

        Optional<User> optionalUser = userRepository.findById(
                ((BeanGrpcSession) getSessionResult.getBean()).getUserId()
        );
        if (!optionalUser.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        List<SavedRoom> savedRoomList = savedRoomRepository.findByAvailableAndUser(true, optionalUser.get());
        List<Room> roomList = roomRepository.findAllById(
                savedRoomList.stream()
                        .filter(savedRoom -> savedRoom.getRoom() != null)
                        .map(savedRoom -> savedRoom.getRoom().getId())
                        .collect(Collectors.toList())
        );
        roomList = roomList.stream()
                .filter(room -> room.getAvailable() &&  !room.isRenting())
                .collect(Collectors.toList());

        beanResult.setBean(new BeanList(roomList));
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    public BeanResult getAllRoomIdOfUser() {
        BeanResult beanResult = new BeanResult();

        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getSessionResult.getBean() == null
                || !(getSessionResult.getBean() instanceof BeanGrpcSession)) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            return beanResult;
        }

        Optional<User> optionalUser = userRepository.findById(
                ((BeanGrpcSession) getSessionResult.getBean()).getUserId()
        );
        if (!optionalUser.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        List<SavedRoom> savedRoomList = savedRoomRepository.findByAvailableAndUser(true, optionalUser.get());
        beanResult.setBean(new BeanList(savedRoomList));
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

}
