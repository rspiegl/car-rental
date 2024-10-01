package at.rspiegl.car_rental.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


/**
 * Serve Angulars index.html for all requests that are not relevant for the server.
 */
@Controller
class AngularForwardController {

    @GetMapping("{path:^(?!api|public)[^\\.]*}/**")
    fun handleForward(): String = "forward:/"

}
