package com.huytran.rerm.bean

import com.huytran.rerm.bean.core.BeanBasic

class BeanGrpcSession : BeanBasic() {
    var userId: Long = 0
    var token: String = ""
}