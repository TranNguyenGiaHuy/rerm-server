package com.huytran.rerm.bean

import com.huytran.rerm.bean.core.BeanBasic

@Deprecated("Not Use")
class BeanTransaction : BeanBasic() {
    var blockchainHash: String = ""
    var src: String = ""
    var des: String = ""
    var blockchainFee: Double = 0.0
    var status: Long = -1
    var type: Long = -1
}