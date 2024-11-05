package me.etheraengine.sound

import org.springframework.stereotype.Component

@Component
interface SoundHandler {
    fun handleSound(sound: Sound)
}
