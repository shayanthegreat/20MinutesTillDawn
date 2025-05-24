package com.tillDawn.Controller;

import com.tillDawn.Model.App;
import com.tillDawn.Model.Result;

import java.util.ArrayList;

public class ProfileController extends Controller{

    public Result changeUsername(String username) {
        if(App.getInstance().getCurrentUser().getName().equals(username)) {
            return new Result("It is your current name!", false);
        }
        if(checkDuplicateUsername(username)) {
            return new Result("This username exists!", false);
        }
        App.getInstance().getCurrentUser().setName(username);
        return new Result("Successfully changed username!", true);
    }

    public Result changePassword(String oldPassword, String newPassword) {
        if(!oldPassword.equals(newPassword)) {
            return new Result("passwords do not match!", false);
        }
        if(App.getInstance().getCurrentUser().getPassword().equals(oldPassword)) {
            return new Result("your new password can't be the same!", false);
        }
        if(!checkPassword(oldPassword)) {
            return new Result("Your password is weak!", false);
        }
        App.getInstance().getCurrentUser().setPassword(newPassword);
        return new Result("Successfully changed password!", true);
    }

    public Result deleteAccount(String password){
        if(App.getInstance().getCurrentUser().getPassword().equals(password)) {
            App.getInstance().setCurrentPlayer(null);
            App.getInstance().setCurrentUser(null);
            return new Result("Successfully deleted account!", true);
        }
        return new Result("wrong password!", false);
    }

    public void setAvatar(String pathOrFile) {
        App.getInstance().getCurrentUser().setAvatarPath(pathOrFile); // Store it wherever needed
    }

    public ArrayList<String> getAvailableAvatars(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 1; i < 13; i++)
            list.add("Avatar" + i + ".png");
        return list;
    }
}
