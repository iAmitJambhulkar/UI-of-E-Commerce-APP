package com.example.innoval.Helper;

public class HelperClass {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HelperClass(String email, String password) {
        this.email = email;
        this.password = password;
    }

    String email , password;

    public HelperClass() {
    }
}
