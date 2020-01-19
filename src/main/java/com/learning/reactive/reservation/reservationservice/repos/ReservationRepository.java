package com.learning.reactive.reservation.reservationservice.repos;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Created by Antony Genil on 1/18/20 at 15 53 for reservation-service
 **/
public interface ReservationRepository extends ReactiveCrudRepository<Reservation, Long> {

}
