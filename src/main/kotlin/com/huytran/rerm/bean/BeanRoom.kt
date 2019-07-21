package com.huytran.rerm.bean

import com.huytran.rerm.bean.core.BeanBasic

class BeanRoom : BeanBasic() {
    var title: String = ""
    var square: Float = 0f
    var address: String = ""
    var price: Long = 0
    var type: Int = -1
    var numberOfFloor: Int = 0
    var hasFurniture: Boolean = false
    var maxMember: Int = 0
    var cookingAllowance: Boolean = false
    var homeType: Int = -1
    var prepaid: Long = 0
    var description: String = ""
    var owner: Long = 0
    var term: String = ""
    var electricityPrice: Long = 0
    var waterPrice: Long = 0
    var isRenting: Boolean = false

    var ownerName: String = ""
}