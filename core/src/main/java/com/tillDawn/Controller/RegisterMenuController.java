package com.tillDawn.Controller;

import com.tillDawn.Model.App;
import com.tillDawn.Model.Result;
import com.tillDawn.Model.User;
import com.tillDawn.View.RegisterMenuView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterMenuController extends Controller {
    private RegisterMenuView view;

    public void setView(RegisterMenuView view) {
        this.view = view;
    }


}
