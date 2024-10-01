package at.rspiegl.car_rental.util

import java.lang.RuntimeException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.BAD_REQUEST)
class CarAlreadyRentedException : RuntimeException {

    constructor() : super()

    constructor(message: String) : super(message)

}
