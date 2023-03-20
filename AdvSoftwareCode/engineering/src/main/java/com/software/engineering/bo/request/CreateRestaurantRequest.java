package com.software.engineering.bo.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantRequest {

    private Long userId;

    private String name;

    private String location;

    private String phoneNumber;

    private String creditCard;
}
