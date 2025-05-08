package com.example.backend.dto;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthTDO {
    private Long id_user;
    private String firstname;
    private String lastname;
    private String email;
    private Long role_id;
    private String Token;
}
