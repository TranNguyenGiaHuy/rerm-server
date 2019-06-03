package com.huytran.rerm.bean

import com.huytran.rerm.bean.core.BeanBasic

class BeanRentRequest : BeanBasic() {
    var renter: Long = -1
    var tsStart: Long = -1
    var tsEnd: Long = -1
    var roomId: Long = -1
}