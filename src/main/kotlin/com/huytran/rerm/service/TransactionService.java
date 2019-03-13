package com.huytran.rerm.service;

import com.huytran.rerm.model.Image;
import com.huytran.rerm.model.Transaction;
import com.huytran.rerm.repository.ImageRepository;
import com.huytran.rerm.repository.TransactionRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends CoreService<Transaction, TransactionRepository, TransactionService.Params> {

    private TransactionRepository transactionRepository;

    TransactionService(
            TransactionRepository transactionRepository
    ) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction createModel() {
        return new Transaction();
    }

    @Override
    public void parseParams(Transaction transaction, Params params) {
        transaction.setBlockchainHash(params.blockchainHash);
        transaction.setSrc(params.src);
        transaction.setDes(params.des);
        transaction.setBlockchainFee(params.blockchainFee);
        transaction.setStatus(params.status);
        transaction.setType(params.type);
    }

    public class Params extends CoreService.AbstractParams {
        String blockchainHash;
        String src;
        String des;
        Double blockchainFee;
        Long status;
        Long type;
    }

}
