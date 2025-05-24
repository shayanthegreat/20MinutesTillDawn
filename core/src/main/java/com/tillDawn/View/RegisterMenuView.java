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
import com.tillDawn.Controller.MainController;
import com.tillDawn.Controller.RegisterMenuController;
import com.tillDawn.Main;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.Result;

public class RegisterMenuView implements Screen {
    private Stage stage;
    private final Label menuLabel;
    private final TextButton registerButton;
    private final Table table;
    private final Label errorLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final SelectBox<String> securityQuestionBox;
    private final TextField securityAnswerField;
    private Skin skin;
    private final TextButton backButton;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private final RegisterMenuController controller;

    public RegisterMenuView(RegisterMenuController controller, Skin skin) {
        this.controller = controller;

        this.menuLabel = new Label("Register Menu", skin);
        this.menuLabel.setFontScale(1.4f);
        this.registerButton = new TextButton("Register", skin);
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

        this.securityQuestionBox = new SelectBox<>(skin);
        this.securityQuestionBox.setItems(
            "What is your pet’s name?",
            "What is your favorite job",
            "What is your favorite color?",
            "What is your birth city?"
        );

        this.securityAnswerField = new TextField("", skin);
        this.securityAnswerField.setMessageText("Answer");

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
        table.add(usernameField).width(400).pad(10, 0, 10, 0);
        table.row();

        table.add(new Label("Password:", GameAssetManager.getInstance().getSkin())).right().pad(10);
        table.add(passwordField).width(400).pad(10, 0, 10, 0);
        table.row();

        table.add(new Label("Security Question:", GameAssetManager.getInstance().getSkin())).right().pad(10);
        table.add(securityQuestionBox).width(400).pad(10, 0, 10, 0);
        table.row();

        table.add(new Label("Answer:", GameAssetManager.getInstance().getSkin())).right().pad(10);
        table.add(securityAnswerField).width(400).pad(10, 0, 10, 0);

        table.row();

        table.add(errorLabel).colspan(2).padBottom(40);


        table.row();

        table.add(registerButton).colspan(2).padTop(20).width(500).center();

        table.row();

        table.add(backButton).colspan(2).center().width(500).pad(10);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new MainView(new MainController(), GameAssetManager.getInstance().getSkin()));
            }
        });
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String question = securityQuestionBox.getSelected();
                String answer = securityAnswerField.getText();
                Result result = controller.registerUser(username, password, question, answer);
                if(result.isSuccess()){
                    errorLabel.setColor(Color.GREEN);
                    errorLabel.setText("Success");
                    Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                        @Override
                        public void run() {
                            Main.getInstance().setScreen(new MainView(new MainController(), GameAssetManager.getInstance().getSkin()));
                        }
                    }, 1);
                }
                else{
                    errorLabel.setText(result.getMessage());
                }
            }
        });
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
