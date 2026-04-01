package com.micnusz.userService.Model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String email;
}
