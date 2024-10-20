package me.etheraengine.service

import jakarta.annotation.PostConstruct
import me.etheraengine.Sound
import me.etheraengine.config.EtheraConfig
import me.etheraengine.logger
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import java.io.File
import java.io.FileFilter

@Service
class SoundService(
    private val etheraConfig: EtheraConfig
) {
    private val log = logger<SoundService>()
    private val sounds = mutableMapOf<String, Sound>()

    @PostConstruct
    fun init() {
        val soundsFile = File(etheraConfig.soundUrl.toURI())
        val soundFiles = soundsFile.listFiles(FileFilter {
            !it.isDirectory && it.extension == "wav"
        })

        soundFiles?.forEach {
            getSound(it.name)
        }
    }

    fun getSound(key: String) =
        sounds.computeIfAbsent(key) {
            log.info("Loading sound {}", key)
            Sound(ResourceUtils.getFile("${etheraConfig.soundUrl}/$key"))
        }

    fun stopSound(key: String) {
        getSound(key).stop()
    }

    fun playSound(key: String) {
        getSound(key).start()
    }
}