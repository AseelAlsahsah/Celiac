package com.software.engineering.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Table(name = "clinics")
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ClinicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Timestamp creationDate;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "credit_card", nullable = false)
    private String creditCard;
}
