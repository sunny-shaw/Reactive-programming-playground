package com.engineer.reactiveprogramming

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.StepVerifier
import java.time.Duration

@RunWith(SpringRunner::class)
@WebFluxTest
class DemoControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `flux API test 1`() {
        webTestClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBodyList(Int::class.java)
                .hasSize(5)

    }

    @Test
    fun `flux API test 2`() {
        val response = webTestClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().is2xxSuccessful
                .returnResult(Int::class.java)
                .responseBody


        StepVerifier.create(response)
                .expectNext(1,2,3,4,5)
                .verifyComplete()
    }

    @Test
    fun `flux API test 3`() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(6))
                .build()

        webTestClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().is2xxSuccessful

    }
}