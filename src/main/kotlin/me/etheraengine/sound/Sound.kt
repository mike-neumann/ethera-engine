package me.etheraengine.sound

import javax.sound.sampled.Clip

data class Sound(
    val name: String,
    val clip: Clip
)