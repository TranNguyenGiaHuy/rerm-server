package com.huytran.rerm.bean

import com.huytran.rerm.bean.core.BeanBasic

class BeanContract : BeanBasic() {
    var owner: Long = -1
    var renter: Long = -1
    var tsStart: Long = -1
    var tsEnd: Long = -1
//    var prepaid: Long = -1
    var modeOPayment: Int = -1
//    var numberOfRoom: Long = -1
    var transactionId: String = ""
    var roomId: Long = -1
//    var term: String = ""

    var ownerName: String = ""
    var renterName: String = ""
    var roomName: String = ""
}