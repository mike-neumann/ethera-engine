package me.animaengine.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * Defines internal and external configurations for anima
 */
@Configuration
open class AnimaConfig {
    /**
     * Defines the width of the rendered screen
     *
     * NOTE: May vary by user settings
     */
    @Value("\${settings.width}")
    var width: Int = 0

    /**
     * Defines the height of the rendered screen
     *
     * NOTE: May vary by user settings
     */
    @Value("\${settings.height}")
    var height: Int = 0

    /**
     * Defines the max available rate at which the rendering engine requests screen rendering (not tied to game logic)
     *
     * NOTE: May vary by user settings
     */
    @Value("\${settings.max-fps}")
    var maxFps: Int = 0

    /**
     * Defines the rate at which the logic engine requests logic updates from scenes and entities (not tied to rendering engine)
     *
     * NOTE: Programmatically set, users may not change this value, as this changes game logic speed
     */
    var tps: Int = 144

    /**
     * Defines the current sound volume level where 1.0f is full volume and 0.0f is no volume
     *
     * NOTE: May vary by user settings
     */
    @Value("\${settings.sound-volume}")
    var soundVolume = 1f
}