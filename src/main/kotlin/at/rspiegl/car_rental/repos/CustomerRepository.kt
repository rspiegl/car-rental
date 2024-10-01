package at.rspiegl.car_rental.repos

import at.rspiegl.car_rental.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository


interface CustomerRepository : JpaRepository<Customer, Long>
