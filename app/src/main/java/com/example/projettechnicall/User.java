package com.example.projettechnicall;

public class User {
    public  String full_name , phone , email;
    public String type ,rating ;

    public User(){

    }
    public User(String email, String full_name , String phone , String type  /* ,String rating*/){
        this.full_name=full_name;
        this.email=email;
        this.phone=phone;
        this.type = type;
    //    this.rating = rating;

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
