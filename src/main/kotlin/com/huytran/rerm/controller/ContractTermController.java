package com.huytran.rerm.controller;

import com.huytran.rerm.controller.core.CoreController;
import com.huytran.rerm.service.ContractTermService;
import org.springframework.web.bind.annotation.RestController;

@RestController("/contractTerm")
public class ContractTermController extends CoreController<ContractTermService, ContractTermService.Params> {

    private ContractTermService contractTermService;

    public ContractTermController(
            ContractTermService contractTermService
    ) {
        super(contractTermService);
        this.contractTermService = contractTermService;
    }

}
