package com.learning.reactive.reservation.reservationservice.controller;

import com.learning.reactive.reservation.reservationservice.repos.Reservation;
import com.learning.reactive.reservation.reservationservice.repos.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Flow;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by Antony Genil on 1/18/20 at 15 52 for reservation-service
 **/
@RestController
@RequiredArgsConstructor
public class ReservationRestController {
    private final ReservationRepository reservationRepository;
    private final IntervalMessageProducer intervalMessageProducer;

    @GetMapping("/reservations")
    Publisher<Reservation> reservationPublisher() {
        return reservationRepository.findAll();
    }


    /**
     * When client sees EVENT_STREAM_VALUE, it does not disconnect
     * @return
     */
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/sse/{n}")
    Publisher<GreetingsResponse> stringPublisher(@PathVariable(name = "n") String name) {
        return this.intervalMessageProducer.produceGreeting(new GreetingsRequest(name));
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class GreetingsRequest {
    private String name;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class GreetingsResponse {
    private String greeting;
}

@Component
class IntervalMessageProducer {
    Flux <GreetingsResponse > produceGreeting(GreetingsRequest name) {
        return Flux.fromStream(Stream.generate(() ->   "Hello "+ name.getName() + " @ " + Instant.now()))
                .map(GreetingsResponse::new)
                .delayElements(Duration.ofSeconds(2));
    }
}
