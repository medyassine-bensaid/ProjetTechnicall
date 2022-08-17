package com.example.projettechnicall;

public class User {
    public  String full_name , phone , email;
    public String type ;

    public User(){

    }
    public User(String full_name , String phone , String email , String type){
        this.full_name=full_name;
        this.email=email;
        this.phone=phone;
        this.type = type;

    }

    @Override
    public String toString() {
        return "User{" +
                "full_name='" + full_name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
