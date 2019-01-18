package com.sb.blumek.daos;

import com.sb.blumek.models.User;
import com.sb.blumek.utils.DBManager;

import java.util.List;

public class UserDAO {
    public List<User> getUsers() {
        return DBManager.getUsers();
    }

    public User getUser(Integer userId) {
        return DBManager.getUser(userId);
    }

    public User loginUser(User user) {
        return DBManager.loginUser(user);
    }

    public void createUser(User user) {
        DBManager.createUser(user);
    }
}
