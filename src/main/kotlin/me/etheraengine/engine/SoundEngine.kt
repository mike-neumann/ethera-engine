package me.etheraengine.engine

import me.etheraengine.config.EtheraConfig
import me.etheraengine.service.SoundService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import javax.sound.sampled.FloatControl
import kotlin.math.log10

/**
 * Engine responsible for handling sound volume
 */
@Component
class SoundEngine(
    private val etheraConfig: EtheraConfig,
    private val soundService: SoundService
) : Thread("SoundEngine"), CommandLineRunner {
    override fun run() {
        while (true) {
            soundService.activeSounds.forEach { sound ->
                val floatControl = sound.clip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl

                // convert the set volume level to decibels
                floatControl.value = 20f * log10(soundService.volume)

                // call registered sound handlers, interesting for handling custom sound effects / modifiers
                etheraConfig.soundHandlers.forEach {
                    it.handleSound(sound)
                }
            }
        }
    }

    override fun run(vararg args: String?) {
        start()
    }
}