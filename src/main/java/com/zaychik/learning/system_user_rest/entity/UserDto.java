package com.zaychik.learning.system_user_rest.entity;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private Role role;
}
