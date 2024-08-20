package io.bootify.my_app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDto {
    private String email;
    private String mobileNo;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String shopName;
    private String area;
    private String status;
    private String roles;
    private String moNumber;
    private String rentPersoncol;
}
