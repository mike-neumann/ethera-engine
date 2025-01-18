package me.etheraengine.config

import me.etheraengine.sound.SoundHandler
import org.springframework.context.annotation.Configuration

@Configuration
open class EtheraConfig(
    val soundHandlers: List<SoundHandler>,
)