package me.etheraengine.example.world

import org.springframework.util.ResourceUtils

object ExampleWorld : World(
    // Uncomment this for world "grassland"
    //ResourceUtils.getFile("classpath:assets/worlds/grassland.txt"),
    ResourceUtils.getFile("classpath:assets/worlds/island.txt"),
    106
)