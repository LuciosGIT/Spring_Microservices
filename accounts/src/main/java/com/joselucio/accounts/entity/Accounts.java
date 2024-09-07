package com.joselucio.accounts.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Accounts extends BaseEntity {

    @Column(name = "costumer_id")
    private Long customerId;

    @Column(name = "account_number")
    @Id
    private Long accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "branch_adress")
    private String branchAdress;

}
