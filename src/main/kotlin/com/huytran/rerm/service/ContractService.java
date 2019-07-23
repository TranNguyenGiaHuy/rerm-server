package com.huytran.rerm.service;

import com.huytran.rerm.bean.BeanContract;
import com.huytran.rerm.bean.BeanGrpcSession;
import com.huytran.rerm.bean.core.BeanList;
import com.huytran.rerm.bean.core.BeanResult;
import com.huytran.rerm.constant.ResultCode;
import com.huytran.rerm.model.Contract;
import com.huytran.rerm.model.Room;
import com.huytran.rerm.model.User;
import com.huytran.rerm.repository.ContractRepository;
import com.huytran.rerm.repository.RoomRepository;
import com.huytran.rerm.repository.UserRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService extends CoreService<Contract, ContractRepository, ContractService.Params> {

    private ContractRepository contractRepository;
    private GrpcSessionService grpcSessionService;
    private RoomRepository roomRepository;
    private UserRepository userRepository;

    ContractService(
            ContractRepository contractRepository,
            GrpcSessionService grpcSessionService,
            RoomRepository roomRepository,
            UserRepository userRepository) {
        super(contractRepository);
        this.contractRepository = contractRepository;
        this.grpcSessionService = grpcSessionService;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Contract createModel() {
        return new Contract();
    }

    @Override
    public void parseParams(Contract contract, Params params) {
        contract.setOwner(params.owner);
        contract.setRenter(params.renter);
        contract.setTsStart(params.tsStart);
        contract.setTsEnd(params.tsEnd);
//        contract.setPrepaid(params.prepaid);
        contract.setModeOPayment(params.modeOPayment);
//        contract.setNumberOfRoom(params.numberOfRoom);
        contract.setAddress(params.address);
        contract.setRoom(params.room);
    }

    public static class Params extends CoreService.AbstractParams {
        User owner;
        User renter;
        Long tsStart;
        Long tsEnd;
//        Long prepaid;
        Integer modeOPayment;
//        Long numberOfRoom;
//        String transactionId;
        String address;
        Room room;

        public Params(User owner, User renter, Long tsStart, Long tsEnd, Integer modeOPayment, String address, Room room) {
            this.owner = owner;
            this.renter = renter;
            this.tsStart = tsStart;
            this.tsEnd = tsEnd;
            this.modeOPayment = modeOPayment;
            this.address = address;
            this.room = room;
        }
    }

    public BeanResult updateAddress(Long contractId, String address) {
        BeanResult beanResult = new BeanResult();
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (!contractOptional.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        Contract contract = contractOptional.get();
        contract.setAddress(address);
        contract.setTsLastModified(System.currentTimeMillis());
        contractRepository.save(contract);

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    public List<BeanContract> getAllOfUser() {
        BeanResult beanResult = new BeanResult();
        BeanResult getGrpcTokenResult = grpcSessionService.getSession();

        if (getGrpcTokenResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getGrpcTokenResult.getBean() == null
                || !(getGrpcTokenResult.getBean() instanceof BeanGrpcSession)) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return null;
        }

        Long userId = ((BeanGrpcSession) getGrpcTokenResult.getBean()).getUserId();
        List<Contract> contractList = contractRepository.findAllByRenter_IdOrOwner_Id(userId, userId);
        List<BeanContract> beanContractList = new ArrayList<>();

        // get all room within contract list
        List<Room> roomList = roomRepository.findAllById(
                contractList.stream().mapToLong(value -> value.getRoom().getId())
                        .distinct()
                        .boxed().collect(Collectors.toList())
        );

        // get all owner and renter
        List<User> ownerList = userRepository.findAllById(
                contractList.stream().mapToLong(value -> value.getOwner().getId())
                        .distinct()
                        .boxed().collect(Collectors.toList())
        );
        List<User> renterList = userRepository.findAllById(
                contractList.stream().mapToLong(value -> value.getRenter().getId())
                        .distinct()
                        .boxed().collect(Collectors.toList())
        );

        contractList.forEach(contract -> {
            BeanContract beanContract = (BeanContract) contract.createBean();
            roomList.stream().filter(room -> room.getId() == beanContract.getRoomId())
                    .findFirst().ifPresent(room -> {
                        beanContract.setRoomName(room.getTitle());
            });

            ownerList.stream().filter(user -> user.getId() == beanContract.getOwner())
                    .findFirst().ifPresent(user -> {
                        beanContract.setOwnerName(user.getUserName());
            });

            renterList.stream().filter(user -> user.getId() == beanContract.getRenter())
                    .findFirst().ifPresent(user -> {
                        beanContract.setRenterName(user.getUserName());
            });

            beanContractList.add(beanContract);
        });

        return beanContractList;
    }

}
