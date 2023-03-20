package com.software.engineering.bo.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRestaurantResponse {

    private Long id;

    private Long restaurantId;

    private Long userId;

    private String restaurantName;

    private String restaurantLocation;

    private String restaurantPhoneNumber;

    private LocalDateTime appointmentTime;
}
