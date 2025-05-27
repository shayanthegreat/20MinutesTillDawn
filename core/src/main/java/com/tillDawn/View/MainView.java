package com.tillDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tillDawn.Controller.*;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.GameAssetManager;

public class MainView implements Screen {
    private Stage stage;
    private final Label menuLabel;
    private final TextButton registerButton;
    private final TextButton loginButton;
    private final TextButton settingButton;
    private final TextButton profileButton;
    private final TextButton preGameButton;
    private final TextButton scoreBoard;
    private final Table table;
    private final TextButton exitButton;

    private final MainController controller;

    private Texture backgroundTexture;
    private Image backgroundImage;

    public MainView(MainController controller, Skin skin) {
        this.controller = controller;

        this.menuLabel = new Label("Main Menu", skin);
        this.menuLabel.setFontScale(2f); // Makes title text 2x larger

        this.registerButton = new TextButton("Register Menu", skin);
        this.loginButton = new TextButton("Login Menu", skin);
        this.settingButton = new TextButton("Settings", skin);
        this.profileButton = new TextButton("Profile Menu", skin);
        this.preGameButton = new TextButton("Pre Game", skin);
        this.scoreBoard = new TextButton("ScoreBoard Menu", skin);
        this.exitButton = new TextButton("Exit", skin);

        this.table = new Table();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        table.setFillParent(true);
        table.center();

        // Increase label font size
        menuLabel.setFontScale(2f);

        table.add(menuLabel).colspan(3).padBottom(40);
        table.row();

        // Apply hover effect and add buttons
        applyAnimatedHoverEffect(registerButton);
        applyAnimatedHoverEffect(loginButton);
        applyAnimatedHoverEffect(settingButton);
        applyAnimatedHoverEffect(profileButton);
        applyAnimatedHoverEffect(preGameButton);
        applyAnimatedHoverEffect(scoreBoard);

        table.add(registerButton).pad(10).expandX().fillX();
        table.add(loginButton).pad(10).expandX().fillX();
        table.add(settingButton).pad(10).expandX().fillX();
        table.row();

        table.add(profileButton).pad(10).expandX().fillX();
        table.add(preGameButton).pad(10).expandX().fillX();
        table.add(scoreBoard).pad(10).expandX().fillX();
        table.row();
        applyAnimatedHoverEffect(exitButton);
        table.add(exitButton).pad(10).expandX().fillX().colspan(3);
        stage.addActor(table);

        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new ProfileView(new ProfileController(), GameAssetManager.getInstance().getSkin()));
            }
        });
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new SettingView(new SettingController(), GameAssetManager.getInstance().getSkin()));
            }
        });
        preGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new PreGameView(new PreGameController(), GameAssetManager.getInstance().getSkin()));
            }
        });
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new RegisterMenuView(new RegisterMenuController(), GameAssetManager.getInstance().getSkin()));
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.getInstance().AppCloser();
            }
        });
    }




    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    private void applyAnimatedHoverEffect(final TextButton button) {
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.addAction(Actions.parallel(
                    Actions.scaleTo(1.05f, 1.05f, 0.1f), // Smooth zoom-in
                    Actions.color(new Color(1f, 1f, 0.7f, 1f), 0.1f) // Light yellow tint
                ));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.addAction(Actions.parallel(
                    Actions.scaleTo(1f, 1f, 0.1f), // Reset size
                    Actions.color(Color.WHITE, 0.1f) // Reset color
                ));
            }
        });
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
