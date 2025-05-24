package com.tillDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tillDawn.Controller.LoginMenuController;
import com.tillDawn.Controller.MainController;
import com.tillDawn.Controller.RegisterMenuController;
import com.tillDawn.Main;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.Result;

public class LoginMenuView implements Screen {
    private Stage stage;
    private final Label menuLabel;
    private final Table table;
    private final Label errorLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final LoginMenuController controller;
    private final TextButton backButton;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Button loginActionButton;
    private Button forgotPasswordButton;
    private boolean labelAdded = false;
    public LoginMenuView(LoginMenuController controller, Skin skin) {
        this.controller = controller;

        this.menuLabel = new Label("Login Menu", skin);
        this.menuLabel.setFontScale(1.4f);
        this.errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);

        this.usernameField = new TextField("", skin);
        this.usernameField.setScale(0.7f);
        this.usernameField.setMessageText("Username");
        this.backButton = new TextButton("Back", skin);
        this.passwordField = new TextField("", skin);
        this.passwordField.setScale(0.7f);
        this.passwordField.setMessageText("Password");
        this.passwordField.setPasswordMode(true);
        this.passwordField.setPasswordCharacter('*');
        this.loginActionButton = new TextButton("Login", skin);
        this.forgotPasswordButton = new TextButton("Forgot Password?", skin);
        this.table = new Table();
    }

    @Override
    public void show() {
        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage = new Stage(new ScreenViewport());
        stage.addActor(backgroundImage);
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        // UI layout
        table.add(menuLabel).colspan(2).padBottom(40);
        table.row();

        table.add(new Label("Username:", GameAssetManager.getInstance().getSkin())).right().pad(10);
        table.add(usernameField).width(400);
        table.row();

        table.add(new Label("Password:", GameAssetManager.getInstance().getSkin())).right().pad(10);
        table.add(passwordField).width(400);
        table.row();

        table.add(errorLabel).colspan(2).padBottom(20);
        table.row();

// Add login and register buttons side-by-side
        table.add(loginActionButton).colspan(2).center().width(500).pad(10);
        table.row();

// Add forgot password button centered
        table.add(forgotPasswordButton).colspan(2).center().padTop(20);

        table.row();
        table.add(backButton).colspan(2).center().width(500).pad(10);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new MainView(new MainController(), GameAssetManager.getInstance().getSkin()));
            }
        });
        stage.addActor(table);
        loginActionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                Result result = new Result("ad",true);
                if (!result.isSuccess()) {
                    errorLabel.setText(result.getMessage());
                } else {
                    //controller.switchToMainMenu(); // Or appropriate action
                }
            }
        });

        forgotPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showForgotPasswordDialog();
            }
        });

    }

    private void showForgotPasswordDialog() {
        Skin skin = GameAssetManager.getInstance().getSkin();
        String username = usernameField.getText();

        if (username == null || username.isEmpty()) {
            Dialog errorDialog = new Dialog("Error", skin);
            errorDialog.text("Please enter your username first.");
            errorDialog.button("OK");
            errorDialog.show(stage);
            return;
        }

        String question = controller.getSecurityQuestion(username);
        if (question == null || question.isEmpty()) {
            Dialog errorDialog = new Dialog("Error", skin);
            errorDialog.text("No security question found for this username.");
            errorDialog.button("OK");
            errorDialog.show(stage);
            return;
        }

        final TextField answerField = new TextField("", skin);
        answerField.setMessageText("Your answer");

        final Label resultLabel = new Label("", skin);
        resultLabel.setColor(Color.RED);

        final Dialog dialog = new Dialog("Forgot Password", skin);

        // Ensure content table is centered
        dialog.getContentTable().center();

        // Add question
        dialog.text(question);

        // Answer field
        dialog.getContentTable().row();
        dialog.getContentTable().add(answerField).width(400).pad(10).center().colspan(2);
        submitButtonListenerSetup(dialog, answerField, resultLabel, username);
        dialog.show(stage);
        dialog.setWidth(700);
        // Optionally center the whole dialog on screen
        dialog.setPosition(
            (stage.getWidth() - dialog.getWidth()) / 2f,
            (stage.getHeight() - dialog.getHeight()) / 2f
        );
    }



    private void submitButtonListenerSetup(
        final Dialog dialog,
        final TextField answerField,
        final Label resultLabel,
        final String username
    ) {
        TextButton submitButton = new TextButton("Submit", GameAssetManager.getInstance().getSkin());

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String answer = answerField.getText();
                Result result = controller.verifySecurityAnswer(username, answer);
                if (result.isSuccess()) {
                    resultLabel.setColor(Color.GREEN);
                    resultLabel.setText(result.getMessage());
                } else {
                    resultLabel.setColor(Color.RED);
                    resultLabel.setText(result.getMessage());
                }

                if (!labelAdded) {
                    dialog.getContentTable().row();
                    dialog.getContentTable().add(resultLabel).colspan(2).padTop(10).center();
                    dialog.pack();
                    dialog.setWidth(700);        // Force width after packing
                    dialog.setPosition(          // Re-center dialog
                        (stage.getWidth() - dialog.getWidth()) / 2f,
                        (stage.getHeight() - dialog.getHeight()) / 2f
                    );
                    labelAdded = true;
                }
            }
        });

        TextButton cancelButton = new TextButton("Cancel", GameAssetManager.getInstance().getSkin());
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        dialog.getButtonTable().center();
        dialog.getButtonTable().add(submitButton).pad(10);
        dialog.getButtonTable().add(cancelButton).pad(10);
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
