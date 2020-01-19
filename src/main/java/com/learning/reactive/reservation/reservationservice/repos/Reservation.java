package com.learning.reactive.reservation.reservationservice.repos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Antony Genil on 1/18/20 at 15 53 for reservation-service
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Reservation {
	@Id
	private String id;
	private String name;
}
