package com.huytran.rerm.service;

import com.huytran.rerm.bean.BeanContract;
import com.huytran.rerm.bean.BeanGrpcSession;
import com.huytran.rerm.bean.core.BeanResult;
import com.huytran.rerm.constant.ResultCode;
import com.huytran.rerm.ethcontract.RentHouseContract;
import com.huytran.rerm.model.Contract;
import com.huytran.rerm.model.Room;
import com.huytran.rerm.model.User;
import com.huytran.rerm.repository.ContractRepository;
import com.huytran.rerm.repository.RoomRepository;
import com.huytran.rerm.repository.UserRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
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

    public List<BeanContract> getAllForAdmin() {
        BeanResult beanResult = new BeanResult();
        BeanResult getGrpcTokenResult = grpcSessionService.getSession();

        if (getGrpcTokenResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getGrpcTokenResult.getBean() == null
                || !(getGrpcTokenResult.getBean() instanceof BeanGrpcSession)) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return null;
        }

        List<Contract> contractList = contractRepository.findAllByAvailable(true);
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

    public BeanResult terminateContract(Long id) {
        BeanResult beanResult = new BeanResult();

        // get current user
        BeanResult getGrpcTokenResult = grpcSessionService.getSession();

        if (getGrpcTokenResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getGrpcTokenResult.getBean() == null
                || !(getGrpcTokenResult.getBean() instanceof BeanGrpcSession)) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return null;
        }
        Long userId = ((BeanGrpcSession) getGrpcTokenResult.getBean()).getUserId();

        Optional<Contract> optionalContract = contractRepository.findById(id);
        if (!optionalContract.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return null;
        }

        Contract contract = optionalContract.get();
        terminateContract(
                getContract(
                        optionalContract.get().getAddress()
                )
        );

        contract.setAvailable(false);
        contractRepository.save(contract);
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    /* smart contract */

    private String address = "0xC1CC5A438F755712CD77E4AF24d9Ca74C604f590";
    private String privateKey = "7aaa7f144ee8d930289367a4005f3c43ea01078364606585e7425dab6a223093";
    private Web3j web3j = Web3j.build(new HttpService("http://localhost:8545/"));
    private Credentials credentials = Credentials.create(privateKey);

    @Async
    public void assignContract(
            Room room,
            User owner,
            User renter,
            Long tsStart,
            Long tsEnd,
            Long contractId) {
        CompletableFuture<RentHouseContract> smartContract = RentHouseContract.deploy(
                web3j,
                credentials,
                new DefaultGasProvider(),
                BigInteger.valueOf(tsStart),
                BigInteger.valueOf(tsEnd)
        ).sendAsync();
        smartContract.thenApplyAsync(rentHouseContract -> {
            addRoomToContract(rentHouseContract, room);
            addUserToContract(rentHouseContract, true, owner);
            addUserToContract(rentHouseContract, false, renter);

            return updateAddress(
                    contractId,
                    rentHouseContract.getContractAddress()
            );
        });
    }

    private void addRoomToContract(RentHouseContract rentHouseContract, Room room) {
        rentHouseContract.setRoom(
                room.getAddress(),
                BigInteger.valueOf(room.getPrice()),
                BigInteger.valueOf(room.getElectricityPrice()),
                BigInteger.valueOf(room.getWaterPrice()),
                BigInteger.valueOf(Math.round(room.getSquare())),
                room.getTerm(),
                BigInteger.valueOf(room.getPrepaid())
        ).sendAsync();
    }

    private void addUserToContract(RentHouseContract rentHouseContract, Boolean isOwner, User user) {
        rentHouseContract.setAccount(
                isOwner,
                user.getUserName(),
                BigInteger.valueOf(user.getTsDateOfBirth()),
                user.getPlaceOfPermanent(),
                user.getIdCard(),
                BigInteger.valueOf(user.getTsCardDated()),
                user.getPhoneNumber(),
                user.getPlaceOfIssueOfIdentityCard()
        ).sendAsync();
    }

    public void addPayment(
            RentHouseContract rentHouseContract,
            Long tsStart,
            Long tsEnd,
            Long tsPaid,
            BigInteger roomBill,
            BigInteger electricityBill,
            BigInteger waterBill
    ) {
        rentHouseContract.addPayment(
                BigInteger.valueOf(tsStart),
                BigInteger.valueOf(tsEnd),
                BigInteger.valueOf(tsPaid),
                roomBill,
                electricityBill,
                waterBill
        ).sendAsync();
    }

    public CompletableFuture<TransactionReceipt> terminateContract(RentHouseContract rentHouseContract) {
        return rentHouseContract.terminateContract().sendAsync();
    }

    public RentHouseContract getContract(String address)  {
        return RentHouseContract.load(
                address,
                web3j,
                credentials,
                new DefaultGasProvider()
        );
    }

}
