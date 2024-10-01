package at.rspiegl.car_rental.repos

import at.rspiegl.car_rental.domain.Car
import org.springframework.data.jpa.repository.JpaRepository


interface CarRepository : JpaRepository<Car, Long>
