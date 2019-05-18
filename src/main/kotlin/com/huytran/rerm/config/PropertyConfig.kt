package com.huytran.rerm.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class PropertyConfig {

    @Value("\${image.path}")
    lateinit var imageFolder: String

    @Value("\${firebase.key}")
    lateinit var firebaseKey: String

}