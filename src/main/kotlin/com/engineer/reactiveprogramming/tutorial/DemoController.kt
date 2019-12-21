package com.engineer.reactiveprogramming.tutorial

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration

@RestController
class DemoController {

    @GetMapping("/flux")
    fun getNumbers() : Flux<Int> {
        return Flux.just(1,2,3,4,5)
                .delayElements(Duration.ofSeconds(1))
                .log()
    }

    @GetMapping("/fluxStream", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getNumberStream() : Flux<Int> {
        return Flux.just(1,2,3,4,5)
                .delayElements(Duration.ofSeconds(1))
                .log()
    }
}