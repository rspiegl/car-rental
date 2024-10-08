package at.rspiegl.car_rental.util

import java.lang.RuntimeException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.CONFLICT)
class ReferencedException : RuntimeException {

    constructor() : super()

    constructor(referencedWarning: ReferencedWarning) : super(referencedWarning.toMessage())

}
