package at.rspiegl.car_rental.repos

import at.rspiegl.car_rental.domain.Car
import at.rspiegl.car_rental.domain.Customer
import at.rspiegl.car_rental.domain.Rental
import org.springframework.data.jpa.repository.JpaRepository


interface RentalRepository : JpaRepository<Rental, Long> {

    fun findFirstByCar(car: Car): Rental?

    fun findFirstByCustomer(customer: Customer): Rental?

}
