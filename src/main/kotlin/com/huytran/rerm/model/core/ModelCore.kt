package com.huytran.rerm.model.core

import com.huytran.rerm.bean.core.BeanBasic
import java.io.Serializable
import javax.persistence.*

@MappedSuperclass
open class ModelCore: Serializable, AbstractModel() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0

    @Column(name = "ts_created")
    var tsCreated: Long = System.currentTimeMillis()

    @Column(name = "ts_last_modified")
    var tsLastModified: Long = System.currentTimeMillis()

    @Column(name = "available")
    var available: Boolean = true

    override fun createEmptyBean(): BeanBasic {
        return BeanBasic()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        beanBasic.id = id
        beanBasic.tsCreated = tsCreated
        beanBasic.tsLastModified = tsLastModified
    }

}