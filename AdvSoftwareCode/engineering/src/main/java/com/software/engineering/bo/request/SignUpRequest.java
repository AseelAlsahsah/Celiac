package com.software.engineering.bo.request;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String username;

    @Email(message = "enter a valid email")
    private String email;

    private String password;
}
