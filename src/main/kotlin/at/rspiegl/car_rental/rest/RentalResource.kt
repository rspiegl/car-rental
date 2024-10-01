package at.rspiegl.car_rental.rest

import at.rspiegl.car_rental.domain.Car
import at.rspiegl.car_rental.domain.Customer
import at.rspiegl.car_rental.model.RentalDTO
import at.rspiegl.car_rental.repos.CarRepository
import at.rspiegl.car_rental.repos.CustomerRepository
import at.rspiegl.car_rental.service.RentalService
import at.rspiegl.car_rental.util.CustomCollectors
import jakarta.validation.Valid
import java.lang.Void
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(
    value = ["/api/rentals"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class RentalResource(
    private val rentalService: RentalService,
    private val carRepository: CarRepository,
    private val customerRepository: CustomerRepository
) {

    @GetMapping
    fun getAllRentals(): ResponseEntity<List<RentalDTO>> =
            ResponseEntity.ok(rentalService.findAll())

    @GetMapping("/{id}")
    fun getRental(@PathVariable(name = "id") id: Long): ResponseEntity<RentalDTO> =
            ResponseEntity.ok(rentalService.get(id))

    @PostMapping
    fun createRental(@RequestBody @Valid rentalDTO: RentalDTO): ResponseEntity<Long> {
        val createdId = rentalService.create(rentalDTO)
        return ResponseEntity(createdId, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateRental(@PathVariable(name = "id") id: Long, @RequestBody @Valid rentalDTO: RentalDTO):
            ResponseEntity<Long> {
        rentalService.update(id, rentalDTO)
        return ResponseEntity.ok(id)
    }

    @DeleteMapping("/{id}")
    fun deleteRental(@PathVariable(name = "id") id: Long): ResponseEntity<Void> {
        rentalService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/carValues")
    fun getCarValues(): ResponseEntity<Map<Long, String>> =
            ResponseEntity.ok(carRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Car::id, Car::description)))

    @GetMapping("/customerValues")
    fun getCustomerValues(): ResponseEntity<Map<Long, Long>> =
            ResponseEntity.ok(customerRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Customer::id, Customer::id)))

}
