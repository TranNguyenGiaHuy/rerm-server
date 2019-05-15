package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanAvatar
import com.huytran.rerm.bean.BeanSavedRoom
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "saved_room")
data class SavedRoom(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "room_id")
        var room: Room? = null
) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanSavedRoom()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanSavedRoom
        trueBean.userId = this.user?.id ?: -1
        trueBean.roomId = this.room?.id ?: -1
    }
}