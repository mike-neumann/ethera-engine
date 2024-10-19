package me.etheraengine

import java.io.File
import javax.sound.sampled.AudioSystem

class Sound(
    val file: File
) {
    val clip = AudioSystem.getClip()

    init {
        clip.open(AudioSystem.getAudioInputStream(file))
        clip.addLineListener {
            if (clip.framePosition == clip.frameLength) {
                stop()
            }
        }
        println("length: ${clip.microsecondLength}")
    }

    fun start() {
        clip.start()
    }

    fun stop() {
        clip.flush()
        clip.stop()
        clip.framePosition = 0
    }
}