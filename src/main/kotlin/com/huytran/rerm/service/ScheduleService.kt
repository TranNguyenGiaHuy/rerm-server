package com.huytran.rerm.service

import com.huytran.rerm.constant.AppConstants
import com.huytran.rerm.constant.ResultCode
import com.huytran.rerm.model.Contract
import com.huytran.rerm.repository.ContractRepository
import com.huytran.rerm.repository.PaymentRepository
import com.huytran.rerm.repository.RoomRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScheduleService(
        private val contractRepository: ContractRepository,
        private val roomRepository: RoomRepository,
        private val paymentRepository: PaymentRepository,
        private val paymentService: PaymentService,
        private val messageService: MessageService,
        private val contractService: ContractService,
        private val smartContractService: SmartContractService
) {

//    private val tsSequence : Long = 2592000000 // 30 days
    private val tsSequence : Long = 240000 // 4 minutes

    @Scheduled(cron = "0 0/2 * 1/1 * ? *") // per 2 minutes
//    @Scheduled(cron = "0 0 0 1/1 * ? *") // start of date, per date
    fun schedule() {
        // get all available contract
        val contractList = contractRepository.findAllByAvailable(true)
        contractList.forEach {
            processContract(it)
        }
    }

    fun processContract(contract: Contract) {
        val currentTime = System.currentTimeMillis()
        // process not start contract
        if (currentTime < contract.tsStart) return

        // process in-progress contract
        if (contract.tsStart <= currentTime
                && currentTime <= contract.tsEnd) {
            // get latest payment of contract
            val latestPayment = paymentRepository.findTopByContract_IdOrderByTsEndDesc(contract.id)

            // latest timestamp from previous payment or tsStart
            val tsLatest = if (latestPayment.isPresent) latestPayment.get().tsEnd else contract.tsStart

            // check payment time is come
            if (currentTime - tsLatest < tsSequence) return

            // add payment and notify owner send bill
            val tsEnd = tsLatest + tsSequence
            // get room
            val  room = roomRepository.findByIdAndAvailable(contract.room?.id!!, true)
            if (!room.isPresent) return

            val addPaymentResult = paymentService.create(
                    PaymentService.Params(
                            room.get().price,
                            "VND",
                            contract.renter,
                            AppConstants.PaymentStatus.WAITING_BILL.raw,
                            tsLatest,
                            tsEnd,
                            contract
                    )
            )
            if (addPaymentResult.code == ResultCode.RESULT_CODE_VALID) {
                messageService.sendAddPayment(
                        contract.room?.id!!,
                        contract.owner?.id!!,
                        contract.renter?.id!!,
                        tsLatest,
                        tsEnd
                )
            }
        }

        // process out date contract
        if (currentTime >= contract.tsEnd) {
            // deactive smart contract
            smartContractService.terminateContract(
                    smartContractService.getContract(contract.address)
            ).thenApply {
                // notify users
                messageService.sendContractTerminate(
                        contract.room?.id!!,
                        contract.owner?.id!!,
                        contract.renter?.id!!
                )

                // deactive contract
                contractService.delete(contract.id)
            }
        }
    }

}