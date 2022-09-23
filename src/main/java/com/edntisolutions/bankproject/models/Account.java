package com.edntisolutions.bankproject.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long number;

    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "registration_dt", nullable = false)
    private LocalDateTime registration;

    @Column(name = "deactivation_dt")
    private LocalDateTime deactivation;

    @Column(nullable = false, precision = 6)
    private Long password;

}
