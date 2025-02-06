package me.etheraengine.runtime.sound

import org.springframework.stereotype.Component

@Component
interface SoundHandler {
    fun handleSound(sound: Sound)
}
