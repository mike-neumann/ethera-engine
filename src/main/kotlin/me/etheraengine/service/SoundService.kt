package me.etheraengine.service

import jakarta.annotation.PostConstruct
import me.etheraengine.config.EtheraConfig
import me.etheraengine.logger
import me.etheraengine.sound.Sound.Sound
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import java.io.File
import java.io.FileFilter
import java.util.concurrent.LinkedBlockingQueue
import javax.sound.sampled.AudioSystem

/**
 * Service to manage audio playback via the systems sound engine
 */
@Service
class SoundService(
    private val etheraConfig: EtheraConfig
) {
    private val log = logger<SoundService>()
    private val sounds = mutableMapOf<String, File>()
    private val activeSounds = mutableListOf<Sound>()
    private val taskQueue = LinkedBlockingQueue<() -> Unit>()
    private val workerThread = Thread {
        while (true) {
            taskQueue.take()()
        }
    }

    @PostConstruct
    fun init() {
        val soundsFile = File(etheraConfig.soundsUrl.toURI())
        val soundFiles = soundsFile.listFiles(FileFilter {
            !it.isDirectory && it.extension == "wav"
        })

        soundFiles?.forEach {
            getSound(it.name)
        }

        workerThread.start()
    }

    fun getSound(key: String) =
        sounds.computeIfAbsent(key) {
            log.info("Loading sound {}", key)

            val file = ResourceUtils.getFile("${etheraConfig.soundsUrl}/$key")

            // First call to cache the next calls
            AudioSystem.getAudioInputStream(file)
            file
        }

    fun isPlaying(key: String) = activeSounds.any { it.name == key }

    /**
     * Play a sound either cached or newly loaded into the sound system, sounds may loop and or be blocking.
     * Blocking sounds run on the caller's thread, non-blocking ones use the sound engines worker thread
     */
    fun playSound(key: String, loop: Boolean = false, isBlocking: Boolean = false) {
        val offer = {
            val clip = AudioSystem.getClip()

            clip.open(AudioSystem.getAudioInputStream(getSound(key)))
            clip.addLineListener {
                if (clip.framePosition == clip.frameLength) {
                    clip.drain()
                    clip.stop()
                }

                if (loop) {
                    clip.framePosition = 0
                    clip.start()
                }
            }
            activeSounds.add(Sound(key, clip))
            clip.start()
        }

        if (isBlocking) {
            offer()
        } else {
            taskQueue.offer(offer)
        }
    }

    fun stopSound(key: String) {
        val sounds = activeSounds
            .filter { it.name == key }

        sounds.forEach {
            it.clip.stop()
            it.clip.close()
        }
        activeSounds.removeAll(sounds)
    }
}