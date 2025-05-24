package com.tillDawn.Controller;

import com.tillDawn.Model.App;
import com.tillDawn.Model.Result;
import com.tillDawn.Model.User;

import java.util.ArrayList;

public class Controller {
    public boolean checkDuplicateUsername(String username) {
        ArrayList<User> users = App.getInstance().getUsers();
        for (User user : users) {
            if(user.getName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@%$#&*()_]).{8,}$";
        return password.matches(regex);
    }

    public Result registerUser(String username, String password, String question, String answer) {
        if(username.equals("") || password.equals("") || question.equals("") || answer.equals("")) {
            return new Result("Fill all the fields", false);
        }
        ArrayList<User> users = App.getInstance().getUsers();
        if(checkDuplicateUsername(username)) {
            return new Result("This user name is already taken!", false);
        }
        if(!checkPassword(password)) {
            return new Result("Please enter a valid password!", false);
        }
        users.add(new User(username, password, question, answer));
        return new Result("Successfully registered user!", true);
    }

    public User getUser(String username) {
        for (User user : App.getInstance().getUsers()) {
            if(user.getName().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
