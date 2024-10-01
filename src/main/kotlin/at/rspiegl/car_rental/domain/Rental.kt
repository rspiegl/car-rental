package at.rspiegl.car_rental.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table


@Entity
@Table(name = "Rentals")
class Rental {

    @Id
    @Column(
        nullable = false,
        updatable = false
    )
    @SequenceGenerator(
        name = "primary_sequence",
        sequenceName = "primary_sequence",
        allocationSize = 1,
        initialValue = 10000
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "primary_sequence"
    )
    var id: Long? = null

    @Column(nullable = false)
    var mileage: Int? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "car_id",
        nullable = false
    )
    var car: Car? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "customer_id",
        nullable = false
    )
    var customer: Customer? = null

}
