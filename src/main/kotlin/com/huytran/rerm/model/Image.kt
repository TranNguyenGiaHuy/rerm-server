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
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "room_id")
        var room: Room? = null,
        @Column(name = "name")
        var name: String = "",
        @Column(name = "file_name")
        var fileName: String = ""
) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanImage()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanImage
        trueBean.roomId = this.room?.id ?: -1
        trueBean.name = this.name
        trueBean.fileName = this.fileName
    }
}