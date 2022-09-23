package com.edntisolutions.bankproject.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

// lombok
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

// hibernate
@Entity
@Table(name = "address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private String state;

    private String address;

    @Column(name = "house_number")
    private String number;

    private String cep;

    private String secondAddress;

}
