package com.dineout.code.hall.DB;

/**
 * Created by Khalid on 12/2/2018.
 */

public class Employee {
    String email;
    String id;
    String name;
    String password;
    String salary;
    String speciality;
    String type;

    public Employee() {
    }

    public Employee(String email, String id, String name, String password, String salary, String speciality, String type) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
        this.salary = salary;
        this.speciality = speciality;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

