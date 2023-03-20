package com.software.engineering.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "clinic_bookings")
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ClinicBookingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Timestamp creationDate;

    @ManyToOne
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    private ClinicEntity clinic;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "appointment_time", nullable = false)
    private LocalDateTime appointmentTime;
}
