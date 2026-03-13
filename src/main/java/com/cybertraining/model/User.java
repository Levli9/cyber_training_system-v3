package com.cybertraining.model;

public class User {

    private int id;
    private String username;
    private String password;
    private String name;
    private String role;
    private String department;

    public User(int id, String username, String password, String name, String role, String department) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getDepartment() {
        return department;
    }

    public boolean isManager() {
        return "manager".equals(role);
    }

    public boolean isEmployee() {
        return "employee".equals(role);
    }
}