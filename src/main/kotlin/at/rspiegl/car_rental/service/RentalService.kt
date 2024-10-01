package at.rspiegl.car_rental.service

import at.rspiegl.car_rental.domain.Rental
import at.rspiegl.car_rental.model.RentalDTO
import at.rspiegl.car_rental.repos.CarRepository
import at.rspiegl.car_rental.repos.CustomerRepository
import at.rspiegl.car_rental.repos.RentalRepository
import at.rspiegl.car_rental.util.CarAlreadyRentedException
import at.rspiegl.car_rental.util.NotFoundException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class RentalService(
    private val rentalRepository: RentalRepository,
    private val carRepository: CarRepository,
    private val customerRepository: CustomerRepository
) {

    fun findAll(): List<RentalDTO> {
        val rentals = rentalRepository.findAll(Sort.by("id"))
        return rentals.stream()
                .map { rental -> mapToDTO(rental, RentalDTO()) }
                .toList()
    }

    fun `get`(id: Long): RentalDTO = rentalRepository.findById(id)
            .map { rental -> mapToDTO(rental, RentalDTO()) }
            .orElseThrow { NotFoundException() }

    fun create(rentalDTO: RentalDTO): Long {
        if (isCarRented(rentalDTO.car)) {
            throw CarAlreadyRentedException("car is already rented")
        }
        val rental = Rental()
        mapToEntity(rentalDTO, rental)
        return rentalRepository.save(rental).id!!
    }

    fun update(id: Long, rentalDTO: RentalDTO) {
        val rental = rentalRepository.findById(id)
                .orElseThrow { NotFoundException() }
        mapToEntity(rentalDTO, rental)
        rentalRepository.save(rental)
    }

    fun delete(id: Long) {
        rentalRepository.deleteById(id)
    }

    private fun isCarRented(carId: Long?): Boolean {
        return carId != null && rentalRepository.findAll().stream()
                .anyMatch { rental -> rental.car?.id == carId }
    }

    private fun mapToDTO(rental: Rental, rentalDTO: RentalDTO): RentalDTO {
        rentalDTO.id = rental.id
        rentalDTO.mileage = rental.mileage
        rentalDTO.car = rental.car?.id
        rentalDTO.customer = rental.customer?.id
        return rentalDTO
    }

    private fun mapToEntity(rentalDTO: RentalDTO, rental: Rental): Rental {
        rental.mileage = rentalDTO.mileage
        val car = if (rentalDTO.car == null) null else carRepository.findById(rentalDTO.car!!)
                .orElseThrow { NotFoundException("car not found") }
        rental.car = car
        val customer = if (rentalDTO.customer == null) null else
                customerRepository.findById(rentalDTO.customer!!)
                .orElseThrow { NotFoundException("customer not found") }
        rental.customer = customer
        return rental
    }

}
