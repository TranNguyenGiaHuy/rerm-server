package com.huytran.rerm.bean

import com.huytran.rerm.bean.core.BeanBasic

class BeanTransaction : BeanBasic() {
    var blockchainHash: String = ""
    var from: String = ""
    var to: String = ""
    var blockchainFee: Double = 0.0
    var status: Long = -1
    var type: Long = -1
}