package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanUser
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "user")
data class User(
        @Column(name = "name")
        var name: String = "",
        @Column(name = "password")
        var password: String = "",
        @Column(name = "user_name")
        var userName: String = "",
        @Column(name = "avatar_id")
        var avatarId: Long = -1,
        @Column(name = "phone_number")
        var phoneNumber: String = "",
        @Column(name = "id_card")
        var idCard: String = "",
        @Column(name = "ts_card_dated")
        var tsCardDated: Long = 0,
        @Column(name = "ts_date_of_birth")
        var tsDateOfBirth: Long = 0,
        @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var roomList: List<Room> = emptyList(),
        @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var ownedContractList: List<Contract> = emptyList(),
        @OneToMany(mappedBy = "renter", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var rentedContractList: List<Contract> = emptyList(),
        @OneToMany(mappedBy = "payer", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var paymentList: List<Payment> = emptyList(),
        @OneToOne(mappedBy = "user")
        var grpcSession: GrpcSession? = null
) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanUser()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanUser
        trueBean.name = this.name
    }
}