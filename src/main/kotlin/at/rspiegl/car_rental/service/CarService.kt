package at.rspiegl.car_rental.service

import at.rspiegl.car_rental.domain.Car
import at.rspiegl.car_rental.model.CarDTO
import at.rspiegl.car_rental.repos.CarRepository
import at.rspiegl.car_rental.repos.RentalRepository
import at.rspiegl.car_rental.util.NotFoundException
import at.rspiegl.car_rental.util.ReferencedWarning
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class CarService(
    private val carRepository: CarRepository,
    private val rentalRepository: RentalRepository
) {

    fun findAll(): List<CarDTO> {
        val cars = carRepository.findAll(Sort.by("id"))
        return cars.stream()
                .map { car -> mapToDTO(car, CarDTO()) }
                .toList()
    }

    fun `get`(id: Long): CarDTO = carRepository.findById(id)
            .map { car -> mapToDTO(car, CarDTO()) }
            .orElseThrow { NotFoundException() }

    fun create(carDTO: CarDTO): Long {
        val car = Car()
        mapToEntity(carDTO, car)
        return carRepository.save(car).id!!
    }

    fun update(id: Long, carDTO: CarDTO) {
        val car = carRepository.findById(id)
                .orElseThrow { NotFoundException() }
        mapToEntity(carDTO, car)
        carRepository.save(car)
    }

    fun delete(id: Long) {
        carRepository.deleteById(id)
    }

    private fun mapToDTO(car: Car, carDTO: CarDTO): CarDTO {
        carDTO.id = car.id
        carDTO.description = car.description
        return carDTO
    }

    private fun mapToEntity(carDTO: CarDTO, car: Car): Car {
        car.description = carDTO.description
        return car
    }

    fun getReferencedWarning(id: Long): ReferencedWarning? {
        val referencedWarning = ReferencedWarning()
        val car = carRepository.findById(id)
                .orElseThrow { NotFoundException() }
        val carRental = rentalRepository.findFirstByCar(car)
        if (carRental != null) {
            referencedWarning.key = "car.rental.car.referenced"
            referencedWarning.addParam(carRental.id)
            return referencedWarning
        }
        return null
    }

}
