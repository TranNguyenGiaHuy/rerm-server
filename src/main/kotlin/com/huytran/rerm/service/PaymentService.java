package com.huytran.rerm.service;

import com.huytran.rerm.bean.BeanGrpcSession;
import com.huytran.rerm.bean.core.BeanList;
import com.huytran.rerm.bean.core.BeanResult;
import com.huytran.rerm.constant.AppConstants;
import com.huytran.rerm.constant.ResultCode;
import com.huytran.rerm.model.Contract;
import com.huytran.rerm.model.Payment;
import com.huytran.rerm.model.User;
import com.huytran.rerm.repository.ContractRepository;
import com.huytran.rerm.repository.PaymentRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService extends CoreService<Payment, PaymentRepository, PaymentService.Params> {

    private PaymentRepository paymentRepository;
    private ContractRepository contractRepository;
    private GrpcSessionService grpcSessionService;
    private MessageService messageService;
    private SmartContractService smartContractService;

    PaymentService(
            PaymentRepository paymentRepository,
            ContractRepository contractRepository,
            GrpcSessionService grpcSessionService,
            MessageService messageService,
            SmartContractService smartContractService) {
        super(paymentRepository);
        this.paymentRepository = paymentRepository;
        this.contractRepository = contractRepository;
        this.grpcSessionService = grpcSessionService;
        this.messageService = messageService;
        this.smartContractService = smartContractService;
    }

    @Override
    public Payment createModel() {
        return new Payment();
    }

    @Override
    public void parseParams(Payment payment, Params params) {
        payment.setAmount(params.amount);
        payment.setCurrency(params.currency);
//        payment.setSrc(params.src);
//        payment.setDes(params.des);
//        payment.setTransaction(params.transaction);
        payment.setPayer(params.payer);
        payment.setStatus(params.status);
        payment.setTsStart(params.tsStart);
        payment.setTsEnd(params.tsEnd);
        payment.setContract(params.contract);
    }

    public static class Params extends CoreService.AbstractParams {
        Long amount;
        String currency;
//        Long src;
//        Long des;
//        Transaction transaction;
        User payer;
        Integer status;
        Long tsStart;
        Long tsEnd;
        Contract contract;

        public Params(Long amount, String currency, User payer, Integer status, Long tsStart, Long tsEnd, Contract contract) {
            this.amount = amount;
            this.currency = currency;
            this.payer = payer;
            this.status = status;
            this.tsStart = tsStart;
            this.tsEnd = tsEnd;
            this.contract = contract;
        }
    }

    public BeanResult getPaymentOfRoom(Long roomId) {
        BeanResult beanResult = new BeanResult();

        Optional<Contract> optionalContract = contractRepository.findByRoom_IdAndAvailableOrderByTsLastModifiedDesc(roomId, true);
        if (!optionalContract.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        List<Payment> paymentList = paymentRepository.findAllByContract_IdAndStatusNot(
                optionalContract.get().getId(),
                AppConstants.PaymentStatus.DONE.getRaw()
        );

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        beanResult.setBean(new BeanList(paymentList));
        return beanResult;
    }

    public BeanResult addBill(Long paymentId, Long electricityBill, Long waterBill) {
        BeanResult beanResult = new BeanResult();

        Optional<Payment> optionalPayment = paymentRepository.findByIdAndAvailable(paymentId, true);
        if (!optionalPayment.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }
        Payment payment = optionalPayment.get();

        // only owner can add bill
        Optional<Contract> optionalContract = contractRepository.findById(payment.getContract().getId());
        if (!optionalContract.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        // check owner
        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getSessionResult.getBean() == null) {
            beanResult.setCode(getSessionResult.getCode());
            return beanResult;
        }
        BeanGrpcSession beanGrpcSession = (BeanGrpcSession) getSessionResult.getBean();
        if (beanGrpcSession.getUserId() != optionalContract.get().getOwner().getId()) {
            beanResult.setCode(ResultCode.RESULT_CODE_PERMISSION_LIMITED);
            return beanResult;
        }

        // update payment
        payment.setTsLastModified(System.currentTimeMillis());
        payment.setElectricityBill(electricityBill);
        payment.setWaterBill(waterBill);
        payment.setStatus(AppConstants.PaymentStatus.WAITING_PAYMENT.getRaw());
        paymentRepository.save(payment);

        // message to renter
        messageService.sendBill(
                optionalContract.get().getRoom().getId(),
                optionalContract.get().getOwner().getId(),
                optionalContract.get().getRenter().getId(),
                payment.getAmount() + payment.getElectricityBill() + payment.getWaterBill(),
                payment.getTsStart(),
                payment.getTsEnd()
        );

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        beanResult.setBean(payment.createBean());
        return beanResult;
    }

    public BeanResult requestPaid(Long paymentId) {
        BeanResult beanResult = new BeanResult();

        Optional<Payment> optionalPayment = paymentRepository.findByIdAndAvailable(paymentId, true);
        if (!optionalPayment.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }
        Payment payment = optionalPayment.get();

        // only renter can request paid
        BeanResult checkUserResult = checkCurrentUserIsRenter(payment);
        if (checkUserResult.getCode() != ResultCode.RESULT_CODE_VALID) {
            beanResult.setCode(checkUserResult.getCode());
            return beanResult;
        }

        payment.setTsLastModified(System.currentTimeMillis());
        payment.setStatus(AppConstants.PaymentStatus.WAITING_CONFIRM.getRaw());
        paymentRepository.save(payment);

        // get contract
        Optional<Contract> optionalContract = contractRepository.findById(payment.getContract().getId());
        if (!optionalContract.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        // message to owner
        messageService.sendPaymentRequest(
                optionalContract.get().getRoom().getId(),
                optionalContract.get().getOwner().getId(),
                optionalContract.get().getRenter().getId(),
                payment.getAmount() + payment.getElectricityBill() + payment.getWaterBill(),
                payment.getTsStart(),
                payment.getTsEnd()
        );

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        beanResult.setBean(payment.createBean());
        return beanResult;
    }

    public BeanResult confirmPayment(Long paymentId) {
        BeanResult beanResult = new BeanResult();

        Optional<Payment> optionalPayment = paymentRepository.findByIdAndAvailable(paymentId, true);
        if (!optionalPayment.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }
        Payment payment = optionalPayment.get();

        // only owner can confirm payment
        Optional<Contract> optionalContract = contractRepository.findById(payment.getContract().getId());
        if (!optionalContract.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        // check owner
        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getSessionResult.getBean() == null) {
            beanResult.setCode(getSessionResult.getCode());
            return beanResult;
        }
        BeanGrpcSession beanGrpcSession = (BeanGrpcSession) getSessionResult.getBean();
        if (beanGrpcSession.getUserId() != optionalContract.get().getOwner().getId()) {
            beanResult.setCode(ResultCode.RESULT_CODE_PERMISSION_LIMITED);
            return beanResult;
        }

        payment.setTsLastModified(System.currentTimeMillis());
        payment.setStatus(AppConstants.PaymentStatus.DONE.getRaw());
        paymentRepository.save(payment);

        // message to renter
        messageService.sendConfirmPayment(
                optionalContract.get().getRoom().getId(),
                optionalContract.get().getOwner().getId(),
                optionalContract.get().getRenter().getId(),
                payment.getAmount() + payment.getElectricityBill() + payment.getWaterBill(),
                optionalContract.get().getTsStart(),
                optionalContract.get().getTsEnd()
        );

        // add payment to smart contract
        Contract contract = optionalContract.get();
        smartContractService.addPayment(
                smartContractService.getContract(contract.getAddress()),
                contract.getTsStart(),
                contract.getTsEnd(),
                System.currentTimeMillis(),
                BigInteger.valueOf(payment.getAmount()),
                BigInteger.valueOf(payment.getElectricityBill()),
                BigInteger.valueOf(payment.getWaterBill())
        );

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        beanResult.setBean(payment.createBean());
        return beanResult;
    }

    private BeanResult checkCurrentUserIsOwner(Payment payment)  {
        BeanResult beanResult = new BeanResult();
        Optional<Contract> optionalContract = contractRepository.findById(payment.getContract().getId());
        if (!optionalContract.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        // check owner
        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getSessionResult.getBean() == null) {
            beanResult.setCode(getSessionResult.getCode());
            return beanResult;
        }
        BeanGrpcSession beanGrpcSession = (BeanGrpcSession) getSessionResult.getBean();
        if (beanGrpcSession.getUserId() != optionalContract.get().getOwner().getId()) {
            beanResult.setCode(ResultCode.RESULT_CODE_PERMISSION_LIMITED);
            return beanResult;
        }
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    private BeanResult checkCurrentUserIsRenter(Payment payment)  {
        BeanResult beanResult = new BeanResult();

        // check renter
        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getSessionResult.getBean() == null) {
            beanResult.setCode(getSessionResult.getCode());
            return beanResult;
        }
        BeanGrpcSession beanGrpcSession = (BeanGrpcSession) getSessionResult.getBean();
        if (beanGrpcSession.getUserId() != payment.getPayer().getId()) {
            beanResult.setCode(ResultCode.RESULT_CODE_PERMISSION_LIMITED);
            return beanResult;
        }
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

}
