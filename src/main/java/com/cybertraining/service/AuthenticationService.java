package com.cybertraining.service;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationService {

    private final DatabaseManager db;

    public AuthenticationService(DatabaseManager db) {
        this.db = db;
    }

    public User authenticate(String username, String password, String roleDisplay) {
        User u = db.getUserByUsername(username);
        if (u == null) return null;
        String stored = u.getPassword();
        if (stored == null) return null;
        if (BCrypt.checkpw(password, stored)) return u;
        return null;
    }

    public User register(String username, String password, String fullName, String roleDisplay, String department) {
        // hash password before storing
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return db.registerUser(username, hashed, fullName, roleDisplay, department);
    }
}
