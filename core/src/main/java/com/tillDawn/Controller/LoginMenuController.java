package com.tillDawn.Controller;

import com.tillDawn.Model.Result;
import com.tillDawn.Model.User;

public class LoginMenuController extends Controller {
    public Result verifySecurityAnswer(String username, String answer) {
        User user = getUser(username);
        if (user == null) {
            return new Result("No user found with this name!", false);
        }
        if(user.getAnswer().equals(answer)) {
            return new Result("Success! your password is " + user.getPassword() + " !", true);
        }
        return new Result("Your answer is incorrect!", false);
    }
    public String getSecurityQuestion(String username) {
        User user = getUser(username);
        return user != null ? user.getQuestion() : null;
    }
}
