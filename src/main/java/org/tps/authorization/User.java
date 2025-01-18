package org.tps.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}
