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
        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var avatarList: List<Avatar> = emptyList(),
        @Column(name = "phone_number")
        var phoneNumber: String = "",
        @Column(name = "id_card")
        var idCard: String = "",
        @Column(name = "ts_card_dated")
        var tsCardDated: Long = 0,
        @Column(name = "ts_date_of_birth")
        var tsDateOfBirth: Long = 0,
        @Column(name = "place_of_permanent")
        var placeOfPermanent: String = "",
        @Column(name = "place_of_issue_of_identity_card")
        var placeOfIssueOfIdentityCard: String = "",
        @Column(name = "admin")
        var admin: Boolean = false,
        @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var roomList: List<Room> = emptyList(),
        @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var ownedContractList: List<Contract> = emptyList(),
        @OneToMany(mappedBy = "renter", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var rentedContractList: List<Contract> = emptyList(),
        @OneToMany(mappedBy = "payer", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var paymentList: List<Payment> = emptyList(),
        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var grpcSessionList: List<GrpcSession> = emptyList(),

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        var savedRoomList: List<SavedRoom> = emptyList()
) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanUser()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanUser
        trueBean.name = this.name
        trueBean.userName = this.userName
        trueBean.phoneNumber = this.phoneNumber
        trueBean.idCard = this.idCard
        trueBean.tsCardDated = this.tsCardDated
        trueBean.tsDateOfBirth = this.tsDateOfBirth
        trueBean.placeOfPermanent = this.placeOfPermanent
        trueBean.placeOfIssueOfIdentityCard = this.placeOfIssueOfIdentityCard
        trueBean.admin = this.admin
    }
}