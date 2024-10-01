package at.rspiegl.car_rental.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement


@Configuration
@EntityScan("at.rspiegl.car_rental.domain")
@EnableJpaRepositories("at.rspiegl.car_rental.repos")
@EnableTransactionManagement
class DomainConfig
