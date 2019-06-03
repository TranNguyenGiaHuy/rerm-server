package com.huytran.rerm.service;

import com.huytran.rerm.bean.BeanGrpcSession;
import com.huytran.rerm.bean.core.BeanList;
import com.huytran.rerm.bean.core.BeanResult;
import com.huytran.rerm.constant.AppConstants;
import com.huytran.rerm.constant.ResultCode;
import com.huytran.rerm.model.RentRequest;
import com.huytran.rerm.model.Room;
import com.huytran.rerm.model.User;
import com.huytran.rerm.repository.RentRequestRepository;
import com.huytran.rerm.repository.RoomRepository;
import com.huytran.rerm.repository.UserRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentRequestService extends CoreService<RentRequest, RentRequestRepository, RentRequestService.Params> {

    private RentRequestRepository rentRequestRepository;
    private GrpcSessionService grpcSessionService;
    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private MessageService messageService;
    private ContractService contractService;

    RentRequestService(
            RentRequestRepository rentRequestRepository,
            GrpcSessionService grpcSessionService, UserRepository userRepository, RoomRepository roomRepository, MessageService messageService, ContractService contractService) {
        super(rentRequestRepository);
        this.rentRequestRepository = rentRequestRepository;
        this.grpcSessionService = grpcSessionService;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.messageService = messageService;
        this.contractService = contractService;
    }

    @Override
    public RentRequest createModel() {
        return new RentRequest();
    }

    @Override
    public void parseParams(RentRequest rentRequest, Params params) {
        rentRequest.setRenter(params.renter);
        rentRequest.setTsStart(params.tsStart);
        rentRequest.setTsEnd(params.tsEnd);
        rentRequest.setRoom(params.room);
        rentRequest.setConfirm(params.isConfirm);
    }

    public class Params extends CoreService.AbstractParams {
        User renter;
        Long tsStart;
        Long tsEnd;
        Boolean isConfirm;
        Room room;

        public Params(User renter, Long tsStart, Long tsEnd, Boolean isConfirm, Room room) {
            this.renter = renter;
            this.tsStart = tsStart;
            this.tsEnd = tsEnd;
            this.isConfirm = isConfirm;
            this.room = room;
        }
    }

    public static class CreateParams {
        Long tsStart;
        Long tsEnd;
        Long roomId;

        public CreateParams(Long tsStart, Long tsEnd, Long roomId) {
            this.tsStart = tsStart;
            this.tsEnd = tsEnd;
            this.roomId = roomId;
        }
    }

    public BeanResult addRequest(CreateParams createParams) {
        BeanResult beanResult = new BeanResult();

        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getSessionResult.getBean() == null) {
            beanResult.setCode(getSessionResult.getCode());
            return beanResult;
        }

        BeanGrpcSession beanGrpcSession = (BeanGrpcSession) getSessionResult.getBean();
        Optional<User> optionalUser = userRepository.findByIdAndAvailable(beanGrpcSession.getUserId(), true);
        if (!optionalUser.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        Optional<Room> roomOptional = roomRepository.findByIdAndAvailable(createParams.roomId, true);
        if (!roomOptional.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        String title = "Rent Request!";
        String message = optionalUser.get().getName() + " want to rent your " + roomOptional.get().getTitle();
        try {
            messageService.sendNotificationToUser(
                    title,
                    message,
                    roomOptional.get().getOwner().getId()
            );
        } catch (NullPointerException e) {
            e.printStackTrace();
            return beanResult;
        }

        return create(
                new Params(
                        optionalUser.get(),
                        createParams.tsStart,
                        createParams.tsEnd,
                        false,
                        roomOptional.get()
                )
        );
    }

    public BeanResult confirmRentRequest(Long rentRequestId) {
        BeanResult beanResult = new BeanResult();

        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getSessionResult.getBean() == null) {
            beanResult.setCode(getSessionResult.getCode());
            return beanResult;
        }
//        Optional<User> currentUserOptional = userRepository.findByIdAndAvailable(
//                ((BeanGrpcSession) getSessionResult.getBean()).getUserId(),
//                true
//        );

        Optional<RentRequest> rentRequestOptional = rentRequestRepository.findByIdAndAvailable(rentRequestId, true);
        if (!rentRequestOptional.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        Optional<Room> roomOptional = roomRepository.findByIdAndAvailable(rentRequestOptional.get().getRoom().getId(), true);
        if (!roomOptional.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        Optional<User> ownerOptional = userRepository.findByIdAndAvailable(roomOptional.get().getOwner().getId(), true);
        if (!ownerOptional.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        if (((BeanGrpcSession) getSessionResult.getBean()).getUserId() != ownerOptional.get().getId()) {
            beanResult.setCode(ResultCode.RESULT_CODE_PERMISSION_LIMITED);
            return beanResult;
        }

        RentRequest rentRequest = rentRequestOptional.get();
        rentRequest.setConfirm(true);
        rentRequestRepository.save(rentRequest);

        String title = "Rent Request Confirm!";
        String message = ownerOptional.get().getName() + " agree to let you to rent " + roomOptional.get().getTitle();
        messageService.sendNotificationToUser(
                title,
                message,
                rentRequest.getRenter().getId()
        );

        List<RentRequest> rentRequestList = rentRequestRepository.findByRoomAndAvailable(roomOptional.get(), true);
        rentRequestList.stream().filter(rr -> rr.getId() != rentRequest.getId())
                .forEach(rr -> rr.setAvailable(false));
        rentRequestRepository.saveAll(rentRequestList);

        Room room = roomOptional.get();
        room.setAvailable(false);
        roomRepository.save(room);

//        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
//        beanResult.setBean(rentRequest.createBean());
//        return beanResult;
        return contractService.create(
                new ContractService.Params(
                        ownerOptional.get(),
                        rentRequest.getRenter(),
                        rentRequest.getTsStart(),
                        rentRequest.getTsEnd(),
                        AppConstants.MODE_OF_PAYMENT_CASH,
                        0L,
                        rentRequest.getRoom()
                )
        );
    }

    public BeanResult getRentRequestOfRoom(Long id) {
        BeanResult beanResult = new BeanResult();

        Optional<Room> roomOptional = roomRepository.findByIdAndAvailable(id, true);
        if (!roomOptional.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        List<RentRequest> rentRequestList = rentRequestRepository.findByRoomAndAvailable(roomOptional.get(), true);
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        beanResult.setBean(new BeanList(rentRequestList));
        return beanResult;
    }
}
