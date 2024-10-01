package at.rspiegl.car_rental.rest

import at.rspiegl.car_rental.model.CustomerDTO
import at.rspiegl.car_rental.service.CustomerService
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
    value = ["/api/customers"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class CustomerResource(
    private val customerService: CustomerService
) {

    @GetMapping
    fun getAllCustomers(): ResponseEntity<List<CustomerDTO>> =
            ResponseEntity.ok(customerService.findAll())

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable(name = "id") id: Long): ResponseEntity<CustomerDTO> =
            ResponseEntity.ok(customerService.get(id))

    @PostMapping
    fun createCustomer(@RequestBody @Valid customerDTO: CustomerDTO): ResponseEntity<Long> {
        val createdId = customerService.create(customerDTO)
        return ResponseEntity(createdId, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateCustomer(@PathVariable(name = "id") id: Long, @RequestBody @Valid
            customerDTO: CustomerDTO): ResponseEntity<Long> {
        customerService.update(id, customerDTO)
        return ResponseEntity.ok(id)
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable(name = "id") id: Long): ResponseEntity<Void> {
        val referencedWarning = customerService.getReferencedWarning(id)
        if (referencedWarning != null) {
            throw ReferencedException(referencedWarning)
        }
        customerService.delete(id)
        return ResponseEntity.noContent().build()
    }

}
