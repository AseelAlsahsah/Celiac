package com.software.engineering.bo.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRestaurantRequest {

    private Long restaurantId;

    private Long userId;

    private LocalDateTime appointmentTime;
}
