package com.huytran.rerm.utilities

import com.huytran.rerm.constant.ResultCode
import org.springframework.core.io.ByteArrayResource
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
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

        @Throws(IOException::class)
        fun writeFileToPath(pathString: String, image: ByteArray) {
            val path = Paths.get(
                    pathString
            )

            try {
                Files.write(path, image)
            } catch (e: IOException) {
                e.printStackTrace()
                throw e
            }

        }

        fun downloadFileFromPath(pathString: String): ByteArray {
            val path = Paths.get(pathString)
            return try {
                val byteArrayResource = ByteArrayResource(Files.readAllBytes(path))
                byteArrayResource.byteArray
            } catch (e: Exception) {
                e.printStackTrace()
                ByteArray(0)
            }

        }
    }

}