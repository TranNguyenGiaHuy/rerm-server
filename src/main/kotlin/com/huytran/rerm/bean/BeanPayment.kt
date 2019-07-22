package com.huytran.rerm.bean

import com.huytran.rerm.bean.core.BeanBasic

class BeanPayment : BeanBasic() {
    var amount: Long = 0L
    var currency: String = ""
//    var src: Long = -1
//    var des: Long = -1
//    var transactionId: Long = -1
    var status: Int = 0
    var payerId: Long = -1
    var contractId: Long = -1
    var tsStart: Long = -1
    var tsEnd: Long = -1
    var electricityBill: Long = 0
    var waterBill: Long = 0
}