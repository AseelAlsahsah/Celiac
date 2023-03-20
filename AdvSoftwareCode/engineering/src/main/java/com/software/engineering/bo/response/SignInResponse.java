package com.software.engineering.bo.response;

import com.software.engineering.entities.UserRoles;
import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {

    private Long id;

    private String username;

    private String email;

    private UserRoles role;
}
