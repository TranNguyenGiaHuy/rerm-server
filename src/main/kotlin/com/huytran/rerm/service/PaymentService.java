package com.huytran.rerm.service;

import com.huytran.rerm.model.Payment;
import com.huytran.rerm.model.Transaction;
import com.huytran.rerm.model.User;
import com.huytran.rerm.repository.PaymentRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

@Service
public class PaymentService extends CoreService<Payment, PaymentRepository, PaymentService.Params> {

    private PaymentRepository paymentRepository;

    PaymentService(
            PaymentRepository paymentRepository
    ) {
        super(paymentRepository);
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createModel() {
        return new Payment();
    }

    @Override
    public void parseParams(Payment payment, Params params) {
        payment.setAmount(params.amount);
        payment.setCurrency(params.currency);
        payment.setSrc(params.src);
        payment.setDes(params.des);
        payment.setTransaction(params.transaction);
        payment.setPayer(params.payer);
    }

    public class Params extends CoreService.AbstractParams {
        Float amount;
        String currency;
        Long src;
        Long des;
        Transaction transaction;
        User payer;
    }

}
