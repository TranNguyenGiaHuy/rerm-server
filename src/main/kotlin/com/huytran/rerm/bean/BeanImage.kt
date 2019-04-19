package com.huytran.rerm.bean

import com.huytran.rerm.bean.core.BeanBasic

class BeanImage : BeanBasic() {
    var roomId: Long = 0
    var name: String = ""
    var fileName: String = ""
    var content: ByteArray = byteArrayOf()
}