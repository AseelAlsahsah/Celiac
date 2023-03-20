package com.software.engineering.bo.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookClinicRequest {

    private Long clinicId;

    private Long userId;

    private LocalDateTime appointmentTime;
}
