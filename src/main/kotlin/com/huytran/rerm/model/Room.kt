package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanRoom
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "room")
data class Room(
        @Column(name = "square")
        var square: Float = 0f,
        @Column(name = "address")
        var address: String = "",
        @Column(name = "price")
        var price: Long = 0,
        @Column(name = "type")
        var type: Int = -1,
        @Column(name = "number_of_floor")
        var numberOfFloor: Int = 0,
        @Column(name = "has_furniture")
        var hasFurniture: Boolean = false,
        @Column(name = "max_member")
        var maxMember: Int = 0,
        @Column(name = "cooking_allowance")
        var cookingAllowance: Boolean = false,
        @Column(name = "home_type")
        var homeType: Int = -1,
        @Column(name = "prepaid")
        var prepaid: Long = 0,
        @Column(name = "description")
        var description: String = "") : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanRoom()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val bean = beanBasic as BeanRoom

        bean.square = this.square
        bean.address = this.address
        bean.price = this.price
        bean.type = this.type
        bean.numberOfFloor = this.numberOfFloor
        bean.hasFurniture = this.hasFurniture
        bean.maxMember = this.maxMember
        bean.cookingAllowance = this.cookingAllowance
        bean.homeType = this.homeType
        bean.prepaid = this.prepaid
        bean.description = this.description
    }
}