package me.etheraengine.service

import me.etheraengine.Sound
import me.etheraengine.config.EtheraConfig
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils

@Service
class SoundService(
    private val etheraConfig: EtheraConfig
) {
    private val sounds = mutableMapOf<String, Sound>()

    fun getSound(key: String) =
        sounds.computeIfAbsent(key) {
            Sound(ResourceUtils.getFile("${etheraConfig.soundUrl}/$key"))
        }

    fun stopSound(key: String) {
        getSound(key).stop()
    }

    fun playSound(key: String) {
        getSound(key).start()
    }
}