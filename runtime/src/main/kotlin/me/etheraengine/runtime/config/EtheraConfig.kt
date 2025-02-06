package me.etheraengine.runtime.config

import me.etheraengine.runtime.sound.SoundHandler
import org.springframework.context.annotation.Configuration

@Configuration
open class EtheraConfig(val soundHandlers: List<SoundHandler>)