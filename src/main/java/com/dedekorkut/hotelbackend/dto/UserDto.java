package com.dedekorkut.hotelbackend.dto;

import com.dedekorkut.hotelbackend.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

}
