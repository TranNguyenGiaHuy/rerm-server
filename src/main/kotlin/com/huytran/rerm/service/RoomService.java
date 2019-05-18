package com.huytran.rerm.service;

import com.huytran.rerm.bean.BeanGrpcSession;
import com.huytran.rerm.bean.core.BeanList;
import com.huytran.rerm.bean.core.BeanResult;
import com.huytran.rerm.constant.ResultCode;
import com.huytran.rerm.model.Room;
import com.huytran.rerm.model.User;
import com.huytran.rerm.repository.RoomRepository;
import com.huytran.rerm.repository.UserRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService extends CoreService<Room, RoomRepository, RoomService.Params> {

    private RoomRepository roomRepository;
    private GrpcSessionService grpcSessionService;
    private UserRepository userRepository;

    RoomService(
            RoomRepository roomRepository,
            GrpcSessionService grpcSessionService,
            UserRepository userRepository) {
        super(roomRepository);
        this.roomRepository = roomRepository;
        this.grpcSessionService = grpcSessionService;
        this.userRepository = userRepository;
    }

    @Override
    public Room createModel() {
        return new Room();
    }

    @Override
    public void parseParams(Room room, Params params) {
        room.setTitle(params.title);
        room.setSquare(params.square);
        room.setAddress(params.address);
        room.setPrice(params.price);
        room.setType(params.type);
        room.setNumberOfFloor(params.numberOfFloor);
        room.setHasFurniture(params.hasFurniture);
        room.setMaxMember(params.maxMember);
        room.setCookingAllowance(params.cookingAllowance);
        room.setHomeType(params.homeType);
        room.setPrepaid(params.prepaid);
        room.setDescription(params.description);
        room.setOwner(params.owner);
        room.setTerm(params.term);
    }

    public class Params extends CoreService.AbstractParams {
        String title;
        Float square;
        String address;
        Long price;
        Integer type;
        Integer numberOfFloor;
        Boolean hasFurniture;
        Integer maxMember;
        Boolean cookingAllowance;
        Integer homeType;
        Long prepaid;
        String description;
        String term;
        Long electricityPrice;
        Long waterPrice;
        User owner;

        public Params(String title,
                      Float square,
                      String address,
                      Long price,
                      Integer type,
                      Integer numberOfFloor,
                      Boolean hasFurniture,
                      Integer maxMember,
                      Boolean cookingAllowance,
                      Integer homeType,
                      Long prepaid,
                      String description,
                      String term,
                      Long electricityPrice,
                      Long waterPrice,
                      User owner) {
            this.title = title;
            this.square = square;
            this.address = address;
            this.price = price;
            this.type = type;
            this.numberOfFloor = numberOfFloor;
            this.hasFurniture = hasFurniture;
            this.maxMember = maxMember;
            this.cookingAllowance = cookingAllowance;
            this.homeType = homeType;
            this.prepaid = prepaid;
            this.description = description;
            this.term = term;
            this.electricityPrice = electricityPrice;
            this.waterPrice = waterPrice;
            this.owner = owner;
        }
    }

    public static class CreateParams extends CoreService.AbstractParams {
        String title;
        Float square;
        String address;
        Long price;
        Integer type;
        Integer numberOfFloor;
        Boolean hasFurniture;
        Integer maxMember;
        Boolean cookingAllowance;
        Integer homeType;
        Long prepaid;
        String description;
        String term;
        Long electricityPrice;
        Long waterPrice;

        public CreateParams(String title,
                            Float square,
                            String address,
                            Long price,
                            Integer type,
                            Integer numberOfFloor,
                            Boolean hasFurniture,
                            Integer maxMember,
                            Boolean cookingAllowance,
                            Integer homeType,
                            Long prepaid,
                            String description,
                            String term,
                            Long electricityPrice,
                            Long waterPrice) {
            this.title = title;
            this.square = square;
            this.address = address;
            this.price = price;
            this.type = type;
            this.numberOfFloor = numberOfFloor;
            this.hasFurniture = hasFurniture;
            this.maxMember = maxMember;
            this.cookingAllowance = cookingAllowance;
            this.homeType = homeType;
            this.prepaid = prepaid;
            this.description = description;
            this.electricityPrice = electricityPrice;
            this.waterPrice = waterPrice;
            this.term = term;
        }
    }

    public BeanResult create(CreateParams createParams) {
        BeanResult beanResult = new BeanResult();
        BeanResult getGrpcTokenResult = grpcSessionService.getSession();

        if (getGrpcTokenResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getGrpcTokenResult.getBean() == null
                || !(getGrpcTokenResult.getBean() instanceof BeanGrpcSession)) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        Long userId = ((BeanGrpcSession) getGrpcTokenResult.getBean()).getUserId();
        Optional<User> optionalUser = userRepository.findByIdAndAvailable(userId, true);
        if (!optionalUser.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        return create(
                new Params(
                        createParams.title,
                        createParams.square,
                        createParams.address,
                        createParams.price,
                        createParams.type,
                        createParams.numberOfFloor,
                        createParams.hasFurniture,
                        createParams.maxMember,
                        createParams.cookingAllowance,
                        createParams.homeType,
                        createParams.prepaid,
                        createParams.description,
                        createParams.term,
                        createParams.electricityPrice,
                        createParams.waterPrice,
                        optionalUser.get()
                )
        );
    }

}
