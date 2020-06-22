package com.dkm.gameback

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GameBackApplication

fun main(args: Array<String>) {
	runApplication<GameBackApplication>(*args)
}
