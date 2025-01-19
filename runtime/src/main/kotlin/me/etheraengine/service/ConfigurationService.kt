package me.etheraengine.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ConfigurationService(
    @Value("\${settings.width: 1920}")
    var width: Int,

    @Value("\${settings.height: 1080}")
    var height: Int,

    @Value("\${settings.max-fps: 144}")
    var maxFps: Int,

    @Value("\${settings.tps: 144}")
    var tps: Int,

    @Value("\${settings.sound-volume:1f}")
    var soundVolume: Float,

    @Value("\${settings.sounds-url:}")
    var soundsUrl: String,

    @Value("\${settings.font-url:}")
    var fontUrl: String,
)