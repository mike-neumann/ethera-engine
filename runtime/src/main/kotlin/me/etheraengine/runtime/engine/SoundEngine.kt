package me.etheraengine.runtime.engine

import me.etheraengine.runtime.config.EtheraConfig
import me.etheraengine.runtime.service.SoundService
import org.springframework.stereotype.Component
import javax.sound.sampled.FloatControl
import kotlin.math.log10

/**
 * Engine responsible for handling sound related tasks
 */
@Component
open class SoundEngine(val etheraConfig: EtheraConfig, val soundService: SoundService) :
    Engine("SoundEngine", 0) {
    override fun onTick(now: Long, deltaTime: Long) {
        for (activeSound in soundService.activeSounds) {
            val floatControl = activeSound.clip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
            // convert the set volume level to decibels
            floatControl.value = 20f * log10(soundService.volume)
            // call registered sound handlers, interesting for handling custom sound effects / modifiers
            for (soundHandler in etheraConfig.soundHandlers) {
                soundHandler.handleSound(activeSound)
            }
        }
    }
}