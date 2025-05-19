package com.tillDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tillDawn.Controller.LoginMenuController;
import com.tillDawn.Controller.RegisterMenuController;
import com.tillDawn.Main;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.Result;

public class LoginMenuView implements Screen {
    private Stage stage;
    private final Label menuLabel;
    private final TextButton loginButton;
    private final Table table;
    private final Label errorLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final LoginMenuController controller;

    public LoginMenuView(LoginMenuController controller, Skin skin) {
        this.controller = controller;

        this.menuLabel = new Label("Register Menu", skin);
        this.menuLabel.setFontScale(1.4f);
        this.loginButton = new TextButton("Register", skin);
        this.errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);

        this.usernameField = new TextField("", skin);
        this.usernameField.setScale(0.7f);
        this.usernameField.setMessageText("Username");

        this.passwordField = new TextField("", skin);
        this.passwordField.setScale(0.7f);
        this.passwordField.setMessageText("Password");
        this.passwordField.setPasswordMode(true);
        this.passwordField.setPasswordCharacter('*');


        this.table = new Table();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        // UI layout
        table.add(menuLabel).colspan(2).padBottom(40);
        table.row();

        table.add(new Label("Username:", GameAssetManager.getInstance().getSkin())).right().pad(10);
        table.add(usernameField).width(200);
        table.row();

        table.add(new Label("Password:", GameAssetManager.getInstance().getSkin())).right().pad(10);
        table.add(passwordField).width(200);
        table.row();


        table.add(errorLabel).colspan(2).padBottom(40);

        table.row();


        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }
}
