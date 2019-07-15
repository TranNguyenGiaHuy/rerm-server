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
import kotlin.math.roundToLong

@Service
class SmartContractService {

    private val address = "0xcA68ac4573C22bFFD1dd85275f543c0f2C16658e"
    private val privateKey = "f4f409e4a842bab1a21cc6e61408197703b122efa9e205b6da9b5c0c43c1f19a"
    private val web3j = Web3j.build(HttpService("127.0.0.1:7545"))
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

}