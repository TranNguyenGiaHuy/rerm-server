package com.huytran.rerm.service;

import com.huytran.rerm.model.ContractTerm;
import com.huytran.rerm.repository.ContractTermRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

@Service
public class ContractTermService extends CoreService<ContractTerm, ContractTermRepository, ContractTermService.Params> {

    private ContractTermRepository contractTermRepository;

    ContractTermService(
            ContractTermRepository contractTermRepository
    ) {
        super(contractTermRepository);
        this.contractTermRepository = contractTermRepository;
    }

    @Override
    public ContractTerm createModel() {
        return new ContractTerm();
    }

    @Override
    public void parseParams(ContractTerm contractTerm, Params params) {
        contractTerm.setName(params.name);
        contractTerm.setDescription(params.description);
    }

    public class Params extends CoreService.AbstractParams {
        String name;
        String description;
    }

}
