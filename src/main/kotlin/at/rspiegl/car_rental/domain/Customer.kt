package at.rspiegl.car_rental.domain

import jakarta.persistence.*


@Entity
@Table(name = "Customers")
class Customer {

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

    @Column
    var name: String? = null

    @Column
    var age: Int? = null

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    var rentals: MutableSet<Rental>? = null

}
