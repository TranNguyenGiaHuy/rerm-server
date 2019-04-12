package com.huytran.rerm.bean

import com.huytran.rerm.bean.core.BeanBasic

class BeanAvatar : BeanBasic() {
    var userId: Long = 0
    var name: String = ""
    var fileName: String = ""
    var content: ByteArray = byteArrayOf()
}