package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanImage
import com.huytran.rerm.bean.BeanUser
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "image")
data class Image(
        @Column(name = "path")
        var path: String = "",
        @Column(name = "room_id")
        var roomId: Long = 0,
        @Column(name = "name")
        var name: String = ""
) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanImage()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanImage
        trueBean.roomId = this.roomId
    }
}