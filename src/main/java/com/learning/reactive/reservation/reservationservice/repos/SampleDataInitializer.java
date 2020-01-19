package com.learning.reactive.reservation.reservationservice.repos;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

/**
 * Created by Antony Genil on 1/18/20 at 15 53 for reservation-service
 **/
@Component
@RequiredArgsConstructor
@Log4j2
class SampleDataInitializer{
	private final ReservationRepository reservationRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void initialize() {
		Flux<String> names = Flux.just("Josh", "Antony", "Dr. Sever", "Kumaran", "Partha", "Sriram", "Violotta", "Stephane"
				, "Madhura", "Sebastin");

		Flux<Reservation> reservationFluxMap = names.map(name -> new Reservation(null,name));

		Publisher<Reservation> saved = reservationFluxMap.flatMap(this.reservationRepository::save);
		Disposable subscribe = reservationRepository.deleteAll()
				.thenMany(saved)
				.thenMany(this.reservationRepository.findAll())
				.subscribe(log::info);


	}

}
