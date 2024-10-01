package at.rspiegl.car_rental.service

import at.rspiegl.car_rental.domain.Customer
import at.rspiegl.car_rental.model.CustomerDTO
import at.rspiegl.car_rental.repos.CustomerRepository
import at.rspiegl.car_rental.repos.RentalRepository
import at.rspiegl.car_rental.util.NotFoundException
import at.rspiegl.car_rental.util.ReferencedWarning
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val rentalRepository: RentalRepository
) {

    fun findAll(): List<CustomerDTO> {
        val customers = customerRepository.findAll(Sort.by("id"))
        return customers.stream()
                .map { customer -> mapToDTO(customer, CustomerDTO()) }
                .toList()
    }

    fun `get`(id: Long): CustomerDTO = customerRepository.findById(id)
            .map { customer -> mapToDTO(customer, CustomerDTO()) }
            .orElseThrow { NotFoundException() }

    fun create(customerDTO: CustomerDTO): Long {
        val customer = Customer()
        mapToEntity(customerDTO, customer)
        return customerRepository.save(customer).id!!
    }

    fun update(id: Long, customerDTO: CustomerDTO) {
        val customer = customerRepository.findById(id)
                .orElseThrow { NotFoundException() }
        mapToEntity(customerDTO, customer)
        customerRepository.save(customer)
    }

    fun delete(id: Long) {
        customerRepository.deleteById(id)
    }

    private fun mapToDTO(customer: Customer, customerDTO: CustomerDTO): CustomerDTO {
        customerDTO.id = customer.id
        customerDTO.name = customer.name
        customerDTO.age = customer.age
        customerDTO.rentals = customer.rentals?.map { rental -> rental.car?.description!! }?.toSet()
        return customerDTO
    }

    private fun mapToEntity(customerDTO: CustomerDTO, customer: Customer): Customer {
        customer.name = customerDTO.name
        customer.age = customerDTO.age
        return customer
    }

    fun getReferencedWarning(id: Long): ReferencedWarning? {
        val referencedWarning = ReferencedWarning()
        val customer = customerRepository.findById(id)
                .orElseThrow { NotFoundException() }
        val customerRental = rentalRepository.findFirstByCustomer(customer)
        if (customerRental != null) {
            referencedWarning.key = "customer.rental.customer.referenced"
            referencedWarning.addParam(customerRental.id)
            return referencedWarning
        }
        return null
    }

}
