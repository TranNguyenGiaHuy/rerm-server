package com.huytran.rerm.utilities

final class UtilityFunction {

    companion object {
        inline fun <reified T: Any> getInstance(entityClass: Class<T>): T {
            return entityClass.newInstance()
        }

    }
}