package com.huytran.rerm.bean

import com.huytran.rerm.bean.core.BeanBasic

class BeanUserDetail() : BeanBasic() {
    var name: String = ""
    var address: String = ""
    var phoneNumber: String = ""

    constructor(id: Long, name: String, address: String, phoneNumber: String) : this()

}