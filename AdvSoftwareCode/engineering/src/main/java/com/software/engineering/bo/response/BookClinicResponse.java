package com.software.engineering.bo.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookClinicResponse {

    private Long id;

    private Long clinicId;

    private Long userId;

    private String clinicName;

    private String clinicLocation;

    private String clinicPhoneNumber;

    private LocalDateTime appointmentTime;
}
