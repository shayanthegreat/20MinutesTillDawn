package com.tillDawn.Controller;

import com.tillDawn.Model.App;
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

    public Result loginUser(String username, String password) {
        User user = getUser(username);
        if (user == null) {
            return new Result("No user found with this name!", false);
        }
        if(!user.getPassword().equals(password)) {
            return new Result("Your password is incorrect!", false);
        }
        App.getInstance().setCurrentUser(user);
        return new Result("Success! ", true);
    }

}
