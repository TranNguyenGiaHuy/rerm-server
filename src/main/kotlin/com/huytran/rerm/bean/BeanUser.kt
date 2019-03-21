package com.huytran.rerm.bean

import com.huytran.rerm.bean.core.BeanBasic

class BeanUser : BeanBasic() {
    var name: String = ""
    var userName: String = ""
    var avatarId: Long = -1
    var phoneNumber: String = ""
    var idCard: String = ""
    var tsCardDated: Long = 0
    var tsDateOfBirth: Long = 0
}