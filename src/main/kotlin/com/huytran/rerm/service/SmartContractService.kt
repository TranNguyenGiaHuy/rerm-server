package com.huytran.rerm.service

import com.huytran.rerm.ethcontract.RentHouseContract
import com.huytran.rerm.model.Room
import com.huytran.rerm.model.User
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.RemoteCall
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import java.util.concurrent.CompletableFuture
import kotlin.math.roundToLong

@Service
class SmartContractService(val contractService: ContractService) {

    private val address = "0xC1CC5A438F755712CD77E4AF24d9Ca74C604f590"
    private val privateKey = "7aaa7f144ee8d930289367a4005f3c43ea01078364606585e7425dab6a223093"
    private val web3j = Web3j.build(HttpService("http://localhost:8545/"))
    private val credentials = Credentials.create(privateKey)

    @Async
    fun assignContract(
            room: Room,
            owner: User,
            renter: User,
            tsStart: Long,
            tsEnd: Long,
            contractId: Long): RemoteCall<String> {
        val smartContract = RentHouseContract.deploy(
                web3j,
                credentials,
                DefaultGasProvider(),
                tsStart.toBigInteger(),
                tsEnd.toBigInteger()
        ).sendAsync()
        smartContract.thenApplyAsync {
            addRoomToContract(it, room).sendAsync()
            addUserToContract(it, true, owner).sendAsync()
            addUserToContract(it, false, renter).sendAsync()

            contractService.updateAddress(
                    contractId,
                    it.contractAddress
            )
        }

        return smartContract.get().address
    }

    private fun addRoomToContract(rentHouseContract: RentHouseContract, room: Room): RemoteCall<TransactionReceipt> {
        return rentHouseContract.setRoom(
                room.address,
                room.price.toBigInteger(),
                room.electricityPrice.toBigInteger(),
                room.waterPrice.toBigInteger(),
                room.square.roundToLong().toBigInteger(),
                room.term,
                room.prepaid.toBigInteger()
        )
    }

    private fun addUserToContract(rentHouseContract: RentHouseContract, isOwner: Boolean, user: User): RemoteCall<TransactionReceipt> {
        return rentHouseContract.setAccount(
                isOwner,
                user.userName,
                user.tsDateOfBirth.toBigInteger(),
                user.placeOfPermanent,
                user.idCard,
                user.tsCardDated.toBigInteger(),
                user.phoneNumber,
                user.placeOfIssueOfIdentityCard
        )
    }

    fun addPayment(
            rentHouseContract: RentHouseContract,
            tsStart: Long,
            tsEnd: Long,
            tsPaid: Long,
            roomBill: BigInteger,
            electricityBill: BigInteger,
            waterBill: BigInteger
    ): CompletableFuture<TransactionReceipt> {
        return rentHouseContract.addPayment(
                tsStart.toBigInteger(),
                tsEnd.toBigInteger(),
                tsPaid.toBigInteger(),
                roomBill,
                electricityBill,
                waterBill
        ).sendAsync()
    }

    fun terminateContract(rentHouseContract: RentHouseContract): CompletableFuture<TransactionReceipt> {
        return rentHouseContract.terminateContract().sendAsync()
    }

    fun getContract(address: String): RentHouseContract {
        return RentHouseContract.load(
                address,
                web3j,
                credentials,
                DefaultGasProvider()
        )
    }

}