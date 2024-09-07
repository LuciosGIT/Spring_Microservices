package com.joselucio.accounts.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "costumer_id")
    private Long customerId;

    private String name;

    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

}
