package com.software.engineering.bo.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponse {

    private Long id;

    private String name;

    private String location;

    private String phoneNumber;

    private String creditCard;
}
