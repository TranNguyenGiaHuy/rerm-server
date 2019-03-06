package com.huytran.rerm.controller;

import com.huytran.rerm.controller.core.CoreController;
import com.huytran.rerm.service.PaymentService;
import org.springframework.web.bind.annotation.RestController;

@RestController("/payment")
public class PaymentController extends CoreController<PaymentService, PaymentService.Params> {

    private PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService
    ) {
        super(paymentService);
        this.paymentService = paymentService;
    }

}
