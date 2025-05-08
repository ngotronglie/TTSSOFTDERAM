package com.example.backend.dto;


import java.util.List;

public class UserTDO {

    private Long id_user;
    private String firstname;
    private String lastname;
    private String email;
    private String avatar;
    private Long role_id;

//    private List<OrderRequest> products;



    public UserTDO() {
    }

    public UserTDO(Long id_user, String firstname, String lastname, String email, String avatar, Long role_id) {
        this.id_user = id_user;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.avatar = avatar;
        this.role_id = role_id;
    }
    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }


}
