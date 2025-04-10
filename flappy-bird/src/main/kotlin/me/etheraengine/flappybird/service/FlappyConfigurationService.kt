package me.etheraengine.flappybird.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class FlappyConfigurationService(@Value("\${flappy-bird.pipe-gap:500}") val pipeGap: Int)
