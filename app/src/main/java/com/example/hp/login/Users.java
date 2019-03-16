package com.example.hp.login;

import android.location.Address;

public class Users {
    private String Email,Username,Address,Password;

    public Users(String name,String email,String password,String address) {
        Email = email;
        Username = name;
        Password = password;
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getMobile() {
        return Address;
    }

}
