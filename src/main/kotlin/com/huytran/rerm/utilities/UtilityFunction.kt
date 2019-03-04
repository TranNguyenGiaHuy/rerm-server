package com.huytran.rerm.utilities

import org.springframework.security.crypto.factory.PasswordEncoderFactories
import java.util.*
import javax.servlet.http.HttpSession



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
    }

}