package at.rspiegl.car_rental.rest

import at.rspiegl.car_rental.model.CarDTO
import at.rspiegl.car_rental.service.CarService
import at.rspiegl.car_rental.util.ReferencedException
import jakarta.validation.Valid
import java.lang.Void
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
    value = ["/api/cars"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class CarResource(
    private val carService: CarService
) {

    @GetMapping
    fun getAllCars(): ResponseEntity<List<CarDTO>> = ResponseEntity.ok(carService.findAll())

    @GetMapping("/{id}")
    fun getCar(@PathVariable(name = "id") id: Long): ResponseEntity<CarDTO> =
            ResponseEntity.ok(carService.get(id))

    @PostMapping
    fun createCar(@RequestBody @Valid carDTO: CarDTO): ResponseEntity<Long> {
        val createdId = carService.create(carDTO)
        return ResponseEntity(createdId, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateCar(@PathVariable(name = "id") id: Long, @RequestBody @Valid carDTO: CarDTO):
            ResponseEntity<Long> {
        carService.update(id, carDTO)
        return ResponseEntity.ok(id)
    }

    @DeleteMapping("/{id}")
    fun deleteCar(@PathVariable(name = "id") id: Long): ResponseEntity<Void> {
        val referencedWarning = carService.getReferencedWarning(id)
        if (referencedWarning != null) {
            throw ReferencedException(referencedWarning)
        }
        carService.delete(id)
        return ResponseEntity.noContent().build()
    }

}
