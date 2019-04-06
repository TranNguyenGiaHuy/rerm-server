package com.huytran.rerm.service;

import com.huytran.rerm.model.Contract;
import com.huytran.rerm.model.ContractContractTerm;
import com.huytran.rerm.model.ContractTerm;
import com.huytran.rerm.repository.ContractContractTermRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

@Service
public class ContractContractTermService extends CoreService<ContractContractTerm, ContractContractTermRepository, ContractContractTermService.Params> {

    private ContractContractTermRepository contractContractTermRepository;

    ContractContractTermService(
            ContractContractTermRepository contractContractTermRepository
    ) {
        super(contractContractTermRepository);
        this.contractContractTermRepository = contractContractTermRepository;
    }

    @Override
    public ContractContractTerm createModel() {
        return new ContractContractTerm();
    }

    @Override
    public void parseParams(ContractContractTerm contractContractTerm, Params params) {
        contractContractTerm.setContract(params.contract);
        contractContractTerm.setContractTerm(params.contractTerm);
    }

    public class Params extends CoreService.AbstractParams {
        Contract contract;
        ContractTerm contractTerm;
    }



}
