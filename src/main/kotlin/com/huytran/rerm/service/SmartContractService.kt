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
class SmartContractService {

    private val address = "0x642052b673215bDb181D1b29290cABC7fbB28618"
    private val privateKey = "af25d42ccd3b100fe83800ef624110fe779b805582cb8af5299fb393e9c7bb64"
    private val web3j = Web3j.build(HttpService("http://localhost:8545/"))
    private val credentials = Credentials.create(privateKey)

    @Async
    fun assignContract(
            room: Room,
            owner: User,
            renter: User,
            tsStart: Long,
            tsEnd: Long): RemoteCall<String> {
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
        return rentHouseContract.addPaymentRequest(
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