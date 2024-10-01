package at.rspiegl.car_rental.model

import jakarta.validation.constraints.Size


class CustomerDTO {

    var id: Long? = null

    @Size(max = 255)
    var name: String? = null

    var age: Int? = null

    var rentals: Set<String>? = null

}
