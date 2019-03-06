package com.huytran.rerm.controller;

import com.huytran.rerm.controller.core.CoreController;
import com.huytran.rerm.service.ContractService;
import org.springframework.web.bind.annotation.RestController;

@RestController("/contract")
public class ContractController extends CoreController<ContractService, ContractService.Params> {

    private ContractService contractService;

    public ContractController(
            ContractService contractService
    ) {
        super(contractService);
        this.contractService = contractService;
    }

}
