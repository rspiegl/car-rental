package at.rspiegl.car_rental.model

import jakarta.validation.constraints.NotNull


class RentalDTO {

    var id: Long? = null

    @NotNull
    var mileage: Int? = null

    @NotNull
    var car: Long? = null

    @NotNull
    var customer: Long? = null

}
