package com.huytran.rerm.bean

import com.huytran.rerm.bean.core.BeanBasic

class BeanPayment : BeanBasic() {
    var amount: Float = 0f
    var currency: String = ""
    var from: Long = -1
    var to: Long = -1
    var transactionId: Long = -1
}