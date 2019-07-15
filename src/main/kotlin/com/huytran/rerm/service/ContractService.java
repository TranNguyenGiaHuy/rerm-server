package com.huytran.rerm.service;

import com.huytran.rerm.model.Contract;
import com.huytran.rerm.model.Room;
import com.huytran.rerm.model.User;
import com.huytran.rerm.repository.ContractRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

@Service
public class ContractService extends CoreService<Contract, ContractRepository, ContractService.Params> {

    private ContractRepository contractRepository;

    ContractService(
            ContractRepository contractRepository
    ) {
        super(contractRepository);
        this.contractRepository = contractRepository;
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
        contract.setTransactionId(params.transactionId);
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
        String transactionId;
        Room room;

        public Params(User owner, User renter, Long tsStart, Long tsEnd, Integer modeOPayment, String transactionId, Room room) {
            this.owner = owner;
            this.renter = renter;
            this.tsStart = tsStart;
            this.tsEnd = tsEnd;
            this.modeOPayment = modeOPayment;
            this.transactionId = transactionId;
            this.room = room;
        }
    }

}
