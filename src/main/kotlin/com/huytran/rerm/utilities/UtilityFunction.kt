package com.huytran.rerm.utilities

import org.springframework.security.crypto.factory.PasswordEncoderFactories
import java.security.SecureRandom
import java.util.*


class UtilityFunction {

    companion object {
        fun encode(password: String): String {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password)
        }

        fun comparePassword(password: String, encryptedPassword: String): Boolean {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(password, encryptedPassword)
        }

        fun getUUID(): String {
            return UUID.randomUUID().toString()
        }

        fun generateToken(): String{
            val random = SecureRandom()
            val bytes = ByteArray(20)
            random.nextBytes(bytes)
            val encoder = Base64.getUrlEncoder().withoutPadding()
            return encoder.encodeToString(bytes)
        }
    }

}