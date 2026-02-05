package com.banking.transactionmanagementsystem.dto.userDTO;

import java.time.LocalDate;

/**
 * Data Transfer Object for user response data.
 * This class is used to send user details back to the client.
 */

public class UserResponseDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDate;

    //Getters & Setters
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSurname(){
        return surname;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
