package com.dineout.code.kitchen.models;

import java.io.Serializable;

public class EmployeeDb implements Serializable
{

    private String email;
    private String id;
    private String name;
    private String password;
    private String salary;
    private String specialty;
    private String type;

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public EmployeeDb(String email, String id, String name, String password, String salary, String speciality, String type) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
        this.salary = salary;
        this.specialty = speciality;
        this.type = type;
    }

    public EmployeeDb() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setSpeciality(String speciality) {
        this.specialty = speciality;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSalary() {
        return salary;
    }

    public String getSpeciality() {
        return specialty;
    }

    public String getType() {
        return type;
    }
}
