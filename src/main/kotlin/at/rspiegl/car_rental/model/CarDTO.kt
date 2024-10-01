package at.rspiegl.car_rental.model

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


class CarDTO {

    var id: Long? = null

    @NotNull
    @Size(max = 255)
    var description: String? = null

}
